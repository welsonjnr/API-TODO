package com.ubistart.challenge.main.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

public class NewUsuarioDTO {
    @NotBlank(message = "O nome do usuario não pode ser vazio")
    @Size(min = 4, message = "O nome do usuario precisa ter no mínimo 5 caracteres")
    private String name;
    @Email(message = "Informe um email valido")
    @NotBlank(message = "O email do usuario não pode ser vazio")
    private String email;
    @NotBlank(message = "A senha do usuario não pode ser vazio")
    @Size(min = 4, message = "A senha do usuario precisa ter no mínimo 8 caracteres")
    private String password;
    @NotNull(message = "As permissoes nao podem ser vazias")
    private Collection<RoleDTO> roles;

    public NewUsuarioDTO() {
    }

    public NewUsuarioDTO(String name, String email, String password, Collection<RoleDTO> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public Collection<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleDTO> roles) {
        this.roles = roles;
    }
}
