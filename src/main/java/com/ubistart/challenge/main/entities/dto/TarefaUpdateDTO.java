package com.ubistart.challenge.main.entities.dto;

import com.ubistart.challenge.main.entities.enums.StatusTarefa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TarefaUpdateDTO {

    @NotBlank(message = "O titulo da tarefa não pode ser vazia")
    @Size(min = 4, message = "O titulo precisa ter no mínimo 5 caracteres")
    private String tarefaTitulo;
    @NotBlank(message = "A descrição da tarefa não pode ser vazia")
    @Size(min = 4, message = "A descrição precisa ter no mínimo 5 caracteres")
    private String tarefaDescricao;
    @NotEmpty(message = "O status da tarefa não pode ser vazio")
    private StatusTarefa statusTarefa;
    private LocalDate dataPrazoParaTarefa;

    public TarefaUpdateDTO() {
    }

    public TarefaUpdateDTO(String tarefaTitulo, String tarefaDescricao, StatusTarefa statusTarefa, LocalDate dataPrazoParaTarefa) {
        this.tarefaTitulo = tarefaTitulo;
        this.tarefaDescricao = tarefaDescricao;
        this.statusTarefa = statusTarefa;
        this.dataPrazoParaTarefa = dataPrazoParaTarefa;
    }

    public String getTarefaTitulo() {
        return tarefaTitulo;
    }

    public void setTarefaTitulo(String tarefaTitulo) {
        this.tarefaTitulo = tarefaTitulo;
    }

    public String getTarefaDescricao() {
        return tarefaDescricao;
    }

    public void setTarefaDescricao(String tarefaDescricao) {
        this.tarefaDescricao = tarefaDescricao;
    }

    public StatusTarefa getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(StatusTarefa statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    public LocalDate getDataPrazoParaTarefa() {
        return dataPrazoParaTarefa;
    }

    public void setDataPrazoParaTarefa(LocalDate dataPrazoParaTarefa) {
        this.dataPrazoParaTarefa = dataPrazoParaTarefa;
    }
}
