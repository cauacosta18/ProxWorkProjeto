package com.br.proxwork.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
  name = "servico_prestado",
  uniqueConstraints = @UniqueConstraint(
    name = "uk_servico_prestado_usuario_servico",
    columnNames = {"usuario_id", "servico_id"}
  )
)
public class ServicoPrestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Boolean deletado;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Servico servico;

    public ServicoPrestado() {
    }

    public ServicoPrestado(Usuario usuario, Servico servico) {
        this.usuario = usuario;
        this.servico = servico;
        this.deletado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getDeletado() {
        return deletado;
    }

    public void setDeletado() {
        this.deletado = true;
    }

    public void setDeletadoFalse() {
        this.deletado = false;
    }

    
}