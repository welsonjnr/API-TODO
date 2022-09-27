package com.ubistart.challenge.main.repositories;

import com.ubistart.challenge.main.entities.Tarefa;
import com.ubistart.challenge.main.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> , JpaSpecificationExecutor<Tarefa> {

    List<Tarefa> findAllByUserTask(Usuario usuario);

}
