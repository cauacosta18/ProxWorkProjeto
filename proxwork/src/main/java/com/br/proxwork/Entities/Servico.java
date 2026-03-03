package com.br.proxwork.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descricao;
    private Boolean deletado;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.REMOVE)
    private List<ServicoPrestado> servicosPrestados;

    public Servico() {
    }

    public Servico(String descricao) {
        this.descricao = descricao;
        this.deletado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getDeletado() {
        return deletado;
    }

    public void setDeletado() {
        this.deletado = true;
    }

    

}
