package com.ubistart.challenge.main.services;

import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.dto.*;
import com.ubistart.challenge.main.repositories.RoleRepository;
import com.ubistart.challenge.main.repositories.TarefaRepository;
import com.ubistart.challenge.main.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InvalidAttributeValueException;
import java.io.InvalidObjectException;
import java.util.Optional;

@Service @Transactional
public class UsuarioService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return usuarioRepository.findAll(pageRequest);
    }

    public Usuario save(NewUsuarioDTO usuarioDTO) throws InvalidAttributeValueException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuarioDTO.getEmail());
        if(usuarioOptional.isPresent()){
            throw new InvalidAttributeValueException("Ja existe um usuario com esse email!");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setName(usuarioDTO.getName());
        newUsuario.setEmail(usuarioDTO.getEmail());
        newUsuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuarioDTO.getRoles().forEach(roleDTO -> newUsuario.getRoles().add(roleRepository.findByName(roleDTO.getName())));

        usuarioRepository.save(newUsuario);

        return newUsuario;
    }

    public Usuario delete(Long id){
        return usuarioRepository.findById(id)
                .map( usuario -> {
                    usuarioRepository.delete(usuario);
                    return usuario;
                }).orElse(null);
    }

    public UsuarioDTO update(Long id, UsuarioUpdateDTO usuarioUpdateDTO, Usuario usuarioAux) throws InvalidObjectException {
        if (tarefaService.isTarefaDoUsuario(id, usuarioAux)) {
            usuarioRepository.findById(id)
                    .map(usuario -> {
                        usuario.setName(usuarioUpdateDTO.getName());
                        usuario.setEmail(usuarioUpdateDTO.getEmail());
                        usuario.setPassword(usuarioUpdateDTO.getPassword());

                        Usuario updated = usuarioRepository.save(usuario);
                        return new UsuarioDTO(updated);
                    });
        }
        return new UsuarioDTO(usuarioRepository.findById(id).get());
    }
}
