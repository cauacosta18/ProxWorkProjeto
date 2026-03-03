package com.br.proxwork.Dtos;

public class ProvedorDto {
    private String caminhoCarteira;
    private String caminhoCertidao;
    private String whatsapp;
    private Double valorMin;
    private Double valorMax;

    public ProvedorDto() {
    }

    public ProvedorDto(String caminhoCarteira, String caminhoCertidao, String whatsapp, Double valorMax,
            Double valorMin) {
        this.caminhoCarteira = caminhoCarteira;
        this.caminhoCertidao = caminhoCertidao;
        this.whatsapp = whatsapp;
        this.valorMin = valorMin;
        this.valorMax = valorMax;
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

    @Override
    public String toString() {
        return super.toString();
    }

    
}
