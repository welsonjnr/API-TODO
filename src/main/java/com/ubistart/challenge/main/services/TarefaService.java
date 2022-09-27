package com.ubistart.challenge.main.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ubistart.challenge.main.entities.Role;
import com.ubistart.challenge.main.entities.Tarefa;
import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.dto.NewTarefaDTO;
import com.ubistart.challenge.main.entities.dto.StatusTarefaUpdateDTO;
import com.ubistart.challenge.main.entities.dto.TarefaDTO;
import com.ubistart.challenge.main.entities.dto.TarefaUpdateDTO;
import com.ubistart.challenge.main.entities.enums.StatusTarefa;
import com.ubistart.challenge.main.repositories.TarefaRepository;
import com.ubistart.challenge.main.repositories.UsuarioRepository;
import com.ubistart.challenge.main.repositories.specs.TarefaSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service @Transactional
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String chaveAssinatura = "dWJpc3RhcnQgZGVzYWZpbw==";

    public List<Tarefa> findTarefasAtrasadas(){
        Specification<Tarefa> spec = TarefaSpecs.findTarefasAtrasadas();
        tarefaRepository.findAll(spec).stream().map(tarefa -> {
            tarefa.setTarefaAtrasada(true);
            tarefaRepository.save(tarefa);
            return null;
        });
        return tarefaRepository.findAll(spec);
    }

    @Transactional
    public List<Tarefa> findAllTarefasByUsuario(Usuario usuario){
        return tarefaRepository.findAllByUserTask(usuario);
    }

    public List<Tarefa> findTarefasCanceladas(){
        Specification<Tarefa> spec = TarefaSpecs.statusIsCanceled(StatusTarefa.CANCELED);
        return tarefaRepository.findAll(spec);
    }

    public List<Tarefa> findTarefasNaoCanceladas(){
        Specification<Tarefa> spec = TarefaSpecs.statusIsNotCanceled(StatusTarefa.CANCELED);
        return tarefaRepository.findAll(spec);
    }

    public Optional<Tarefa> findById(Long id){
        return tarefaRepository.findById(id);
    }

    public Page<Tarefa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return tarefaRepository.findAll(pageRequest);
    }

    public Tarefa save(NewTarefaDTO tarefaDTO) throws AccountNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(tarefaDTO.getEmailUser());
        if(!usuarioOptional.isPresent()){
            throw new AccountNotFoundException("O usuario não foi encontrado com esse email");
        }

        Tarefa newTarefa = new Tarefa();
        newTarefa.setTitulo(tarefaDTO.getTitulo());
        newTarefa.setDescricao(tarefaDTO.getDescricao());
        newTarefa.setDataCriacao(LocalDateTime.now());
        newTarefa.setDataPrazoParaTarefa(tarefaDTO.getDataPrazoParaTarefa() != null ? tarefaDTO.getDataPrazoParaTarefa() : LocalDate.now().plusDays(7));
        newTarefa.setDatafinalizacaoTarefa(null);
        newTarefa.setDataUltimoUpdate(LocalDateTime.now());
        newTarefa.setStatusTarefa(StatusTarefa.CREATED);
        newTarefa.setTarefaAtrasada(newTarefa.getDataPrazoParaTarefa().isAfter(LocalDate.now()) ? false : true);
        newTarefa.setUserTask(usuarioOptional.get());

        tarefaRepository.save(newTarefa);

        return newTarefa;
    }

    public Tarefa delete(Long id, Usuario usuario) throws InvalidObjectException {
        if(isTarefaDoUsuario(id, usuario)) {
            return tarefaRepository.findById(id)
                    .map(tarefa -> {
                        tarefa.setStatusTarefa(StatusTarefa.CANCELED);
                        tarefa.setDataUltimoUpdate(LocalDateTime.now());
                        tarefaRepository.save(tarefa);
                        return tarefa;
                    }).orElse(null);
        }
        return null;
    }

    public TarefaDTO update(Long id, TarefaUpdateDTO tarefaUpdateDTO, Usuario usuario) throws InvalidObjectException {
        if(isTarefaDoUsuario(id, usuario)){
            tarefaRepository.findById(id)
                    .map(tarefa -> {
                        tarefa.setDescricao(tarefaUpdateDTO.getTarefaDescricao());
                        tarefa.setStatusTarefa(tarefaUpdateDTO.getStatusTarefa());
                        tarefa.setDataUltimoUpdate(LocalDateTime.now());
                        tarefa.setTarefaAtrasada(tarefa.getDataPrazoParaTarefa().isAfter(LocalDate.now()) ? false : true);
                        tarefa.setDataPrazoParaTarefa(tarefaUpdateDTO.getDataPrazoParaTarefa());
                        Tarefa updated = tarefaRepository.save(tarefa);
                        return new TarefaDTO(updated);
                    });
        }
        return new TarefaDTO(tarefaRepository.findById(id).get());
    }



    public TarefaDTO updateStatus(Long id, StatusTarefaUpdateDTO statusTarefaUpdateDTO, Usuario usuario) throws InvalidObjectException {
        if(isTarefaDoUsuario(id, usuario)) {
            tarefaRepository.findById(id)
                    .map(tarefa -> {
                        try {
                            tarefa.setStatusTarefa(verificaUpdateStatus(tarefa.getStatusTarefa(), statusTarefaUpdateDTO.getStatusTarefa()));
                        } catch (InvalidObjectException e) {
                            e.printStackTrace();
                        }
                        tarefa.setDataUltimoUpdate(LocalDateTime.now());
                        Tarefa updated = tarefaRepository.save(tarefa);
                        return new TarefaDTO(updated);
                    });
        }
        return new TarefaDTO(tarefaRepository.findById(id).get());
    }

    public TarefaDTO  updateStatusFinish(Long id, StatusTarefaUpdateDTO statusTarefaUpdateDTO, Usuario usuario) throws InvalidObjectException {
        if(isTarefaDoUsuario(id, usuario)) {
            tarefaRepository.findById(id)
                    .map(tarefa -> {
                        try {
                            tarefa.setStatusTarefa(verificaUpdateStatusToFinish(tarefa.getStatusTarefa(), statusTarefaUpdateDTO.getStatusTarefa()));
                        } catch (InvalidObjectException e) {
                            e.printStackTrace();
                        }

                        tarefa.setDataUltimoUpdate(LocalDateTime.now());
                        tarefa.setDatafinalizacaoTarefa(LocalDateTime.now());
                        Tarefa updated = tarefaRepository.save(tarefa);
                        return new TarefaDTO(updated);
                    });
        }
        return new TarefaDTO(tarefaRepository.findById(id).get());
    }

    private StatusTarefa verificaUpdateStatus(StatusTarefa statusAtual, StatusTarefa statusUpdate) throws InvalidObjectException {
        StatusTarefa statusTarefa = statusAtual;
        if(statusUpdate != null && !statusAtual.equals(StatusTarefa.CANCELED) && !statusUpdate.equals(StatusTarefa.CREATED) && !statusUpdate.equals(StatusTarefa.COMPLETED)){
            statusTarefa = statusUpdate;
            return statusTarefa;
        } else {
            throw new InvalidObjectException("O Status enviado nao e valido " + statusUpdate.toString()
                    + " verifique o status atual da tarefa! ");
        }
    }

    private StatusTarefa verificaUpdateStatusToFinish(StatusTarefa statusAtual, StatusTarefa statusFinish) throws InvalidObjectException {
        StatusTarefa statusTarefa = statusAtual;
        if(statusFinish != null && !statusAtual.equals(StatusTarefa.CANCELED) && statusFinish.equals(StatusTarefa.COMPLETED)){
            statusTarefa = statusFinish;
            return statusTarefa;
        } else {
            throw new InvalidObjectException("O Status enviado nao e valido " + statusFinish.toString()
                    + " verifique o status atual da tarefa! ");
        }
    }

    public boolean isTarefaDoUsuario(Long id, Usuario usuario) throws InvalidObjectException {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        Tarefa tarefaAux  = new Tarefa();
        if(tarefaOptional.isPresent()){
            tarefaAux = tarefaOptional.get();
            if(!tarefaAux.getUserTask().equals(usuario)){
                throw new InvalidObjectException("Essa tarefa nao pertence a esse usuario");
            } else if(usuario.getRoles().contains(new Role("ADMIN"))){
                return true;
            }
            return true;
        }
        return false;
    }

    public Usuario getUsuarioByRequisicao(HttpServletRequest request){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refresh_token = authorizationHeader.substring("Bearer " .length());
            Algorithm algorithm = Algorithm.HMAC256(chaveAssinatura.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String emailUsuario = decodedJWT.getSubject();
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(emailUsuario);
            Usuario usuario = new Usuario();
            if (!usuarioOptional.isPresent()) {
                throw new UsernameNotFoundException("Usuario nao encontrado");
            } else {
                return usuario = usuarioOptional.get();
            }
        } else {
            throw new UsernameNotFoundException("Usuario nao encontrado");
        }
    }
}
