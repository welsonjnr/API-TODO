package com.ubistart.challenge.main.entities.dto;

import com.ubistart.challenge.main.entities.Tarefa;
import com.ubistart.challenge.main.entities.enums.StatusTarefa;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TarefaDTO{

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDate dataPrazoParaTarefa;
    private LocalDateTime datafinalizacaoTarefa;
    private LocalDateTime dataUltimoUpdate;
    private StatusTarefa statusTarefa;
    private boolean isTarefaAtrasada;
    private String nomeUsuario;

    public TarefaDTO() {
    }

    public TarefaDTO(Long id, String titulo, String descricao, LocalDateTime dataCriacao, LocalDate dataPrazoParaTarefa, LocalDateTime datafinalizacaoTarefa, LocalDateTime dataUltimoUpdate, StatusTarefa statusTarefa, boolean isTarefaAtrasada, String nomeUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataPrazoParaTarefa = dataPrazoParaTarefa;
        this.datafinalizacaoTarefa = datafinalizacaoTarefa;
        this.dataUltimoUpdate = dataUltimoUpdate;
        this.statusTarefa = statusTarefa;
        this.isTarefaAtrasada = isTarefaAtrasada;
        this.nomeUsuario = nomeUsuario;
    }

    public TarefaDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.titulo = tarefa.getTitulo();
        this.descricao = tarefa.getDescricao();
        this.dataCriacao = tarefa.getDataCriacao();
        this.dataPrazoParaTarefa = tarefa.getDataPrazoParaTarefa();
        this.datafinalizacaoTarefa = tarefa.getDatafinalizacaoTarefa();
        this.dataUltimoUpdate = tarefa.getDataUltimoUpdate();
        this.statusTarefa = tarefa.getStatusTarefa();
        this.isTarefaAtrasada = tarefa.isTarefaAtrasada();
        this.nomeUsuario = tarefa.getUserTask().getName();
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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
