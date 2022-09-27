package com.ubistart.challenge.main.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class TarefasUsuarioDTO {

    @Email(message = "Informe um email valido")
    @NotBlank(message = "O email do usuario não pode ser vazio")
    private String email;

    public TarefasUsuarioDTO() {
    }

    public TarefasUsuarioDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
