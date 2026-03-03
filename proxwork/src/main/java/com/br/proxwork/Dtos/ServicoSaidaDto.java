package com.br.proxwork.Dtos;

public class ServicoSaidaDto {

    private int id;
    private String descricao;

    public ServicoSaidaDto() {
    }

    public ServicoSaidaDto(int id, String descricao) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
