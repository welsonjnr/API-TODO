package com.ubistart.challenge.main.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioUpdateDTO {
    @NotBlank(message = "O nome do usuario não pode ser vazio")
    @Size(min = 4, message = "O nome do usuario precisa ter no mínimo 5 caracteres")
    private String name;
    @Email(message = "Informe um email valido")
    @NotBlank(message = "O email do usuario não pode ser vazio")
    private String email;
    @NotBlank(message = "A senha do usuario não pode ser vazio")
    @Size(min = 4, message = "A senha do usuario precisa ter no mínimo 8 caracteres")
    private String password;

    public UsuarioUpdateDTO() {
    }

    public UsuarioUpdateDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
