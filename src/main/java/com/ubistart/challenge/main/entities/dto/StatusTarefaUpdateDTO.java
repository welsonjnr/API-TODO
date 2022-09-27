package com.ubistart.challenge.main.entities.dto;

import com.ubistart.challenge.main.entities.enums.StatusTarefa;

public class StatusTarefaUpdateDTO {

    private StatusTarefa statusTarefa;

    public StatusTarefaUpdateDTO() {}

    public StatusTarefaUpdateDTO(StatusTarefa statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    public StatusTarefa getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(StatusTarefa statusTarefa) {
        this.statusTarefa = statusTarefa;
    }
}
