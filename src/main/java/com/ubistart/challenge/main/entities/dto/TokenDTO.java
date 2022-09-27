package com.ubistart.challenge.main.entities.dto;

public class TokenDTO {
    private String emailLogin;
    private String token;

    public TokenDTO() {
    }

    public TokenDTO(String emailLogin, String token) {
        this.emailLogin = emailLogin;
        this.token = token;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
