package com.ubistart.challenge.main.entities;

import com.ubistart.challenge.main.entities.enums.StatusTarefa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Tarefa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDate dataPrazoParaTarefa;
    private LocalDateTime datafinalizacaoTarefa;
    private LocalDateTime dataUltimoUpdate;
    private StatusTarefa statusTarefa;
    private boolean isTarefaAtrasada;

    @ManyToOne
    @JoinColumn(name="task_user_id")
    private Usuario userTask;

    public Tarefa() {}

    public Tarefa(Long id, String titulo, String descricao, LocalDateTime dataCriacao, LocalDate dataPrazoParaTarefa, LocalDateTime datafinalizacaoTarefa, LocalDateTime dataUltimoUpdate, StatusTarefa statusTarefa, boolean isTarefaAtrasada, Usuario userTask) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataPrazoParaTarefa = dataPrazoParaTarefa;
        this.datafinalizacaoTarefa = datafinalizacaoTarefa;
        this.dataUltimoUpdate = dataUltimoUpdate;
        this.statusTarefa = statusTarefa;
        this.isTarefaAtrasada = isTarefaAtrasada;
        this.userTask = userTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataPrazoParaTarefa() {
        return dataPrazoParaTarefa;
    }

    public void setDataPrazoParaTarefa(LocalDate dataPrazoParaTarefa) {
        this.dataPrazoParaTarefa = dataPrazoParaTarefa;
    }

    public LocalDateTime getDatafinalizacaoTarefa() {
        return datafinalizacaoTarefa;
    }

    public void setDatafinalizacaoTarefa(LocalDateTime datafinalizacaoTarefa) {
        this.datafinalizacaoTarefa = datafinalizacaoTarefa;
    }

    public LocalDateTime getDataUltimoUpdate() {
        return dataUltimoUpdate;
    }

    public void setDataUltimoUpdate(LocalDateTime dataUltimoUpdate) {
        this.dataUltimoUpdate = dataUltimoUpdate;
    }

    public StatusTarefa getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(StatusTarefa statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    public boolean isTarefaAtrasada() {
        return isTarefaAtrasada;
    }

    public void setTarefaAtrasada(boolean tarefaAtrasada) {
        isTarefaAtrasada = tarefaAtrasada;
    }

    public Usuario getUserTask() {
        return userTask;
    }

    public void setUserTask(Usuario userTask) {
        this.userTask = userTask;
    }
}
