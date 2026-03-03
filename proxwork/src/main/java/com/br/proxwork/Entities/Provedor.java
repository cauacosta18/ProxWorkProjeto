package com.br.proxwork.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Provedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caminhoCarteira;
    private String caminhoCertidao;
    private Boolean verificado;
    private String whatsapp;
    private Double valorMin;
    private Double valorMax;

    @OneToOne
    private Pessoa pessoa;

    public Provedor() {
    }

    public Provedor(String caminhoCarteira, String caminhoCertidao, Pessoa pessoa, String whatsapp, Double valorMax, Double valorMin) {
        this.caminhoCarteira = caminhoCarteira;
        this.caminhoCertidao = caminhoCertidao;
        this.pessoa = pessoa;
        this.verificado = false;
        this.whatsapp = whatsapp;
        this.valorMax = valorMax;
        this.valorMin = valorMin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaminhoCarteira() {
        return caminhoCarteira;
    }

    public void setCaminhoCarteira(String caminhoCarteira) {
        this.caminhoCarteira = caminhoCarteira;
    }

    public String getCaminhoCertidao() {
        return caminhoCertidao;
    }

    public void setCaminhoCertidao(String caminhoCertidao) {
        this.caminhoCertidao = caminhoCertidao;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado() {
        this.verificado = true;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public Double getValorMin() {
        return valorMin;
    }

    public void setValorMin(Double valorMin) {
        this.valorMin = valorMin;
    }

    public Double getValorMax() {
        return valorMax;
    }

    public void setValorMax(Double valorMax) {
        this.valorMax = valorMax;
    }

    public Double getMedia() {
        return (valorMax + valorMin) / 2;
    }

}
