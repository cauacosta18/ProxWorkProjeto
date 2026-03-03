package com.br.proxwork.Dtos;

import java.util.List;

public class ServicosPrestadosDto {

    private String email;
    private List<Integer> servicos;

    public ServicosPrestadosDto() {
    }

    public ServicosPrestadosDto(String email, List<Integer> servicos) {
        this.email = email;
        this.servicos = servicos;
    }

    public String getUsuarioEmail() {
        return email;
    }

    public void setUsuarioEmail(String email) {
        this.email = email;
    }

    public List<Integer> getServicos() {
        return servicos;
    }

    public void setServicos(List<Integer> servicos) {
        this.servicos = servicos;
    }


}
