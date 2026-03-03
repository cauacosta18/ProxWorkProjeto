package com.br.proxwork.Dtos;

public class PessoaDto {
    private String cpf;
    private String dataNascimento;
    private Integer genero;

    public PessoaDto() {
    }

    public PessoaDto(String cpf, String dataNascimento, Integer genero) {
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        String genero;

        if (1 == this.genero) {
            genero = "Feminino";
        } else if (2 == this.genero) {
            genero = "Masculino";
        } else {
            genero = "Outro";
        }

        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
