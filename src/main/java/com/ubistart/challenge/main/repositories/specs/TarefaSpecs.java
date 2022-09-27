package com.ubistart.challenge.main.repositories.specs;

import com.ubistart.challenge.main.entities.Tarefa;
import com.ubistart.challenge.main.entities.Usuario;
import com.ubistart.challenge.main.entities.enums.StatusTarefa;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public abstract class TarefaSpecs {
    public static Specification<Tarefa> statusIsCanceled(StatusTarefa status){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("statusTarefa"), status));
    }

    public static Specification<Tarefa> statusIsNotCanceled(StatusTarefa status){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("statusTarefa"), status));
    }

    public static Specification<Tarefa> findTarefasAtrasadas(){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("dataPrazoParaTarefa"), LocalDate.now()));
    }

    public static Specification<Tarefa> findTarefasByUsuario(Usuario usuario){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userTask"), usuario));
    }
}
