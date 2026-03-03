package com.br.proxwork.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.proxwork.Entities.Pessoa;
import com.br.proxwork.Entities.Provedor;

import jakarta.transaction.Transactional;
import java.util.Optional;

public interface ProvedorRepository extends JpaRepository<Provedor, Integer> {

    @Query
    @Transactional
    Optional<Provedor> findByPessoa(Pessoa pessoa);

}
