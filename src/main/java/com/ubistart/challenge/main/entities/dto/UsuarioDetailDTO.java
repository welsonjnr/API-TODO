package com.ubistart.challenge.main.entities.dto;

import com.ubistart.challenge.main.entities.Role;
import com.ubistart.challenge.main.entities.Tarefa;
import com.ubistart.challenge.main.entities.Usuario;

import java.util.Collection;
import java.util.List;

public class UsuarioDetailDTO {

    private String name;
    private String email;
    private String passowrd;

    private Collection<Role> roles;

    private List<Tarefa> tarefas;

    public UsuarioDetailDTO() {
    }

    public UsuarioDetailDTO(String name, String email, String passowrd, List<Role> roles, List<Tarefa> tarefas) {
        this.name = name;
        this.email = email;
        this.passowrd = passowrd;
        this.roles = roles;
        this.tarefas = tarefas;
    }

    public UsuarioDetailDTO(Usuario usuario) {
        this.name = usuario.getName();
        this.email = usuario.getEmail();
        this.passowrd = usuario.getPassword();
        this.roles = usuario.getRoles();
        this.tarefas = usuario.getTasks();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
