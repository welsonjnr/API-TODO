package com.ubistart.challenge.main.entities.dto;

import com.ubistart.challenge.main.entities.Usuario;

public class UsuarioDTO {
    private String name;
    private String email;
    private String password;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UsuarioDTO(Usuario usuario){
        this.name = usuario.getName();
        this.email = usuario.getEmail();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
