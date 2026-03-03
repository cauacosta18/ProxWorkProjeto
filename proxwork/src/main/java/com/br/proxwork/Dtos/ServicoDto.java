package com.br.proxwork.Dtos;

public class ServicoDto {

    private String descricao;

    public ServicoDto() {
    }

    public ServicoDto( String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
