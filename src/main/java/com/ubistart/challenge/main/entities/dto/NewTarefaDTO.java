package com.ubistart.challenge.main.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class NewTarefaDTO {
    @NotBlank(message = "O titulo da tarefa não pode ser vazia")
    @Size(min = 4, message = "O titulo precisa conter no mínimo 5 caracteres")
    private String titulo;
    @NotBlank(message = "A descrição da tarefa não pode ser vazia")
    @Size(min = 4, message = "A descrição precisa ter no mínimo 5 caracteres")
    private String descricao;
    @NotNull(message = "A data para tarefa nao pode ser vazia")
    private LocalDate dataPrazoParaTarefa;
    @NotBlank(message = "O email do usuario nao pode ser vazio")
    @Email(message = "O email nao e valido")
    private String emailUser;

    public NewTarefaDTO() {
    }

    public NewTarefaDTO(String titulo, String descricao, LocalDate dataPrazoParaTarefa, String emailUser) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataPrazoParaTarefa = dataPrazoParaTarefa;
        this.emailUser = emailUser;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataPrazoParaTarefa() {
        return dataPrazoParaTarefa;
    }

    public void setDataPrazoParaTarefa(LocalDate dataPrazoParaTarefa) {
        this.dataPrazoParaTarefa = dataPrazoParaTarefa;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
