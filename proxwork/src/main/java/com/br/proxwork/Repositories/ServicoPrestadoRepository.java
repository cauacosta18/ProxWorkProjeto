package com.br.proxwork.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.proxwork.Entities.ServicoPrestado;
import com.br.proxwork.Entities.Usuario;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {

    @Query(value = "SELECT * FROM servico_prestado WHERE servico_id = :servicoId AND usuario_id = :usuarioId", nativeQuery = true)
    @Transactional
    Optional<ServicoPrestado> findByIdUsuarioServico(Integer usuarioId, Integer servicoId);

    @Query
    @Transactional
    List<ServicoPrestado> findByUsuario(Usuario usuario);

}
