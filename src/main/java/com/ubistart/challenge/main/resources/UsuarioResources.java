package com.ubistart.challenge.main.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubistart.challenge.main.entities.Role;
import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.dto.*;
import com.ubistart.challenge.main.exceptions.SenhaInvalidaException;
import com.ubistart.challenge.main.services.TarefaService;
import com.ubistart.challenge.main.services.UsuarioService;
import com.ubistart.challenge.main.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.management.InvalidAttributeValueException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioResources {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private UsuarioService usuarioService;

   @Autowired
    private UsuarioServiceImpl usuarioServiceImp;

    private String chaveAssinatura = "dWJpc3RhcnQgZGVzYWZpbw==";

    private Long expiracao = 30L;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);

        return (!usuario.isEmpty()) ?
                ResponseEntity.ok().body(new UsuarioDTO(usuario.get())) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<UsuarioDetailDTO> findUsuario(HttpServletRequest request){
        Usuario usuario = tarefaService.getUsuarioByRequisicao(request);

        return (usuario != null) ?
                ResponseEntity.ok().body(new UsuarioDetailDTO(usuario)) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<UsuarioDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC" ) String direction
    ){

        Page<Usuario> list = usuarioService.findPage(page, linesPerPage, orderBy, direction);
        Page<UsuarioDTO> listDTO = list.map(usuario -> new UsuarioDTO(usuario));

        return ResponseEntity.ok().body(listDTO);

    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> insert(@Valid @RequestBody NewUsuarioDTO newUsuarioDTO) throws InvalidAttributeValueException {
        Usuario usuarioCriado = usuarioService.save(newUsuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(usuarioCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDTO(usuarioCriado));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Usuario usuario = usuarioService.delete(id);
        if(usuario != null){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable ("id") Long id, @RequestBody UsuarioUpdateDTO usuarioUpdateDTO, HttpServletRequest request) throws InvalidObjectException {
        Usuario usuario = tarefaService.getUsuarioByRequisicao(request);
        UsuarioDTO usuarioDTO = usuarioService.update(id, usuarioUpdateDTO, usuario);
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @PostMapping(value = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(chaveAssinatura.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String emailUsuario = decodedJWT.getSubject();
                Optional<Usuario> usuarioOptional = usuarioService.findByEmail(emailUsuario);
                Usuario usuario = new Usuario();
                if(!usuarioOptional.isPresent()){
                    throw new UsernameNotFoundException("Usuario nao encontrado");
                } else{
                    usuario = usuarioOptional.get();
                }

                LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracao);
                Date dateToken = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

                String acess_token = JWT.create()
                        .withSubject(usuario.getEmail())
                        .withExpiresAt(dateToken)
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", usuario.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("acess_token", acess_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e){
                response.setHeader("error ", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token nao foi enviado");
        }
    }

}
