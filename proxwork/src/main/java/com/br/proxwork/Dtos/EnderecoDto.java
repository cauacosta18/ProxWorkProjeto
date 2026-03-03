package com.br.proxwork.Dtos;

public class EnderecoDto {
    private String cidade;
    private String estado;

    public EnderecoDto() {
    }

    public EnderecoDto(String cidade, String estado) {
        
        this.cidade = cidade;
        this.estado = estado;
    }


    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
