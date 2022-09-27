package com.ubistart.challenge.main.entities.dto;

import com.ubistart.challenge.main.entities.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoleDTO {

    @NotBlank(message = "O nome da permissao nao pode ser vazia")
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public RoleDTO(Role role) {
        this.name = role.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
