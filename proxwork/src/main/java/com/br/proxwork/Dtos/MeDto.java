package com.br.proxwork.Dtos;

public class MeDto {

    private String nome;
    private String email;
    private Boolean provedor;

    public MeDto() {
    }

    public MeDto(String nome, String email, Boolean provedor) {
        this.nome = nome;
        this.email = email;
        this.provedor = provedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getProvedor() {
        return provedor;
    }

    public void setProvedor(Boolean provedor) {
        this.provedor = provedor;
    }

}
