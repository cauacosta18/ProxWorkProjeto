package com.br.proxwork.Entities;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String cpf;
    private LocalDate dataNascimento;
    private Integer generoId;

    @OneToOne
    private Usuario usuario;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.REMOVE)
    private Provedor provedor;

    public Pessoa() {
    }

    private static final int IDADE_MINIMA = 18;

    public Pessoa(String cpf, String dataNascimento, Integer generoId, Usuario usuario) {
        System.out.println("data recebida: " + dataNascimento);
        this.cpf = cpf;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);

        if (nascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser futura.");
        }

        int idade = Period.between(nascimento, LocalDate.now()).getYears();
        if (idade < IDADE_MINIMA) {
            throw new IllegalArgumentException(
                "Idade mínima é " + IDADE_MINIMA + " anos. Idade calculada: " + idade
            );
        }

        this.dataNascimento = nascimento;
        this.generoId = generoId;
        this.usuario = usuario;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataNascimento.format(formatter); 
        return dataFormatada;
    }

    public void setDataNascimento(String dataNascimentoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
    }

    public String getGenero() {
        String genero;

        if (1 == generoId) {
            genero = "Feminino";
        } else if (2 == generoId) {
            genero = "Masculino";
        } else {
            genero = "Outro";
        }

        return genero;
    }

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGenero(Integer generoId) {
        this.generoId = generoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}