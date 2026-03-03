package com.br.proxwork.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.proxwork.Entities.Pessoa;
import com.br.proxwork.Entities.Usuario;

import jakarta.transaction.Transactional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query
    @Transactional
    Optional<Pessoa> findByCpf(String cpf);

    @Query
    @Transactional
    Optional<Pessoa> findByUsuario(Usuario usuario);

}
