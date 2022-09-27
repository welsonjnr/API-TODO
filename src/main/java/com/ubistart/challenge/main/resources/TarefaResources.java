package com.ubistart.challenge.main.resources;

import com.ubistart.challenge.main.entities.Tarefa;
import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.dto.*;
import com.ubistart.challenge.main.services.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InvalidObjectException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/tarefa")
public class TarefaResources {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TarefaDTO> findById(@PathVariable Long id){
        Optional<Tarefa> tarefa = tarefaService.findById(id);

        return (!tarefa.isEmpty()) ?
                ResponseEntity.ok().body(new TarefaDTO(tarefa.get())) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/canceled")
    public ResponseEntity<List<TarefaDTO>> findTarefasCanceled(){
        List<Tarefa> tarefas = tarefaService.findTarefasCanceladas();
        List<TarefaDTO> listDTO = tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).collect(Collectors.toList());

        return (!tarefas.isEmpty()) ?
                ResponseEntity.ok().body(listDTO) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/ok")
    public ResponseEntity<List<TarefaDTO>> findTarefasNotCanceled(){
        List<Tarefa> tarefas = tarefaService.findTarefasNaoCanceladas();
        List<TarefaDTO> listDTO = tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).collect(Collectors.toList());

        return (!tarefas.isEmpty()) ?
                ResponseEntity.ok().body(listDTO) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/late")
    public ResponseEntity<List<TarefaDTO>> findTarefasLate(){
        List<Tarefa> tarefas = tarefaService.findTarefasAtrasadas();
        List<TarefaDTO> listDTO = tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).collect(Collectors.toList());

        return (!tarefas.isEmpty()) ?
                ResponseEntity.ok().body(listDTO) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<TarefaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC" ) String direction
    ){

       Page<Tarefa> list = tarefaService.findPage(page, linesPerPage, orderBy, direction);
       Page<TarefaDTO> listDTO = list.map(tarefa -> new TarefaDTO(tarefa));
       System.out.println(LocalDate.now());

        return ResponseEntity.ok().body(listDTO);

    }

    @GetMapping(value = "/usuario")
    public ResponseEntity <List<TarefaDTO>> findAllByUsuario(HttpServletRequest request){
        Usuario usuario = tarefaService.getUsuarioByRequisicao(request);
        List<Tarefa> tarefas = tarefaService.findAllTarefasByUsuario(usuario);

        List<TarefaDTO> listDTO = tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).collect(Collectors.toList());
        return (!tarefas.isEmpty()) ?
                ResponseEntity.ok().body(listDTO):
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> insert(@Valid @RequestBody NewTarefaDTO newTarefaDTO) throws AccountNotFoundException {
        Tarefa tarefaCriada = tarefaService.save(newTarefaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(tarefaCriada.getId()).toUri();
        return ResponseEntity.created(uri).body(new TarefaDTO(tarefaCriada));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) throws InvalidObjectException {
        Usuario usuario = tarefaService.getUsuarioByRequisicao(request);
        Tarefa tarefa = tarefaService.delete(id, usuario);
        if(tarefa != null){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TarefaDTO> update(@PathVariable ("id") Long id, @RequestBody TarefaUpdateDTO tarefaUpdateDTO, HttpServletRequest request) throws InvalidObjectException {
        Usuario usuario = tarefaService.getUsuarioByRequisicao(request);
        TarefaDTO tarefaDTO = tarefaService.update(id, tarefaUpdateDTO, usuario);
        return ResponseEntity.ok().body(tarefaDTO);
    }

    @PutMapping(value = "/status/{id}")
    public ResponseEntity<TarefaDTO> updateTarefaStatus(@PathVariable ("id") Long id, @RequestBody StatusTarefaUpdateDTO statusTarefaUpdateDTO, HttpServletRequest request) throws InvalidObjectException {
        Usuario usuario = tarefaService.getUsuarioByRequisicao(request);
        TarefaDTO tarefaDTO = tarefaService.updateStatus(id, statusTarefaUpdateDTO, usuario);
        return ResponseEntity.ok().body(tarefaDTO);
    }

    @PutMapping(value = "/finish/{id}")
    public ResponseEntity<TarefaDTO> finishTarefa(@PathVariable ("id") Long id, @RequestBody StatusTarefaUpdateDTO statusTarefaUpdateDTO, HttpServletRequest request) throws InvalidObjectException {
        Usuario usuario = tarefaService.getUsuarioByRequisicao(request);
        TarefaDTO tarefaDTO = tarefaService.updateStatusFinish(id, statusTarefaUpdateDTO, usuario);
        return ResponseEntity.ok().body(tarefaDTO);
    }


}
