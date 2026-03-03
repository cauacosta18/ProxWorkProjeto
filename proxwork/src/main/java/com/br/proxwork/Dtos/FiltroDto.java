package com.br.proxwork.Dtos;

import java.util.List;

public class FiltroDto {

    private String nome;
    private String email;
    private Double valorMax;
    private Double valorMin;
    private String estado;
    private List<Integer> servicos;

    public FiltroDto() {
    }

    public FiltroDto(String nome, String email, Double valorMax, Double valorMin, String estado,
            List<Integer> servicos) {
        this.nome = nome;
        this.email = email;
        this.valorMax = valorMax;
        this.valorMin = valorMin;
        this.estado = estado;
        this.servicos = servicos;
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

    public Double getValorMax() {
        return valorMax;
    }

    public void setValorMax(Double valorMax) {
        this.valorMax = valorMax;
    }

    public Double getValorMin() {
        return valorMin;
    }

    public void setValorMin(Double valorMin) {
        this.valorMin = valorMin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Integer> getServicos() {
        return servicos;
    }

    public void setServicos(List<Integer> servicos) {
        this.servicos = servicos;
    }

}
