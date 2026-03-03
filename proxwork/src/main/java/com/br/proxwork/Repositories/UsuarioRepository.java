package com.br.proxwork.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.proxwork.Entities.Usuario;

import jakarta.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query
    @Transactional
    Optional<Usuario> findByEmail(String email);
}
