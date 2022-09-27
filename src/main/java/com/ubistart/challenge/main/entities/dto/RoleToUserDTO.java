package com.ubistart.challenge.main.entities.dto;

import javax.validation.constraints.NotBlank;

public class RoleToUserDTO {

    @NotBlank(message = "O nome da permissao nao pode ser vazia")
    private String roleName;
    @NotBlank(message = "O email do usuario nao pode ser vazio")
    private String emailUsuario;

    public RoleToUserDTO() {
    }

    public RoleToUserDTO(String roleName, String emailUsuario) {
        this.roleName = roleName;
        this.emailUsuario = emailUsuario;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
