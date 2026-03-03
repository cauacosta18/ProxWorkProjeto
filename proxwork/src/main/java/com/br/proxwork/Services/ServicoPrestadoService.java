package com.br.proxwork.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.proxwork.Dtos.ServicosPrestadosDto;
import com.br.proxwork.Entities.Servico;
import com.br.proxwork.Entities.ServicoPrestado;
import com.br.proxwork.Entities.Usuario;
import com.br.proxwork.Repositories.ServicoPrestadoRepository;
import com.br.proxwork.Repositories.ServicoRepository;
import com.br.proxwork.Repositories.UsuarioRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class ServicoPrestadoService {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ServicoPrestadoRepository servicoPrestadoRepository;

    public List<String> listarServicosPorPrestador (@RequestParam String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            Usuario usuario = usuarioRepository.findByEmail(email).get();
            if (usuario.getDeletado() == false) {

            List<ServicoPrestado> servicoPrestados = servicoPrestadoRepository.findByUsuario(usuario);
            List<String> servicos = new ArrayList<>();

            for (ServicoPrestado servicoPrestado : servicoPrestados) {
                if (servicoPrestado.getDeletado() == false){
                    Servico servico = servicoPrestado.getServico();
                    servicos.add(servico.getDescricao());
                }
                
            }
            return servicos;

            } else {
                List<String> erro = new ArrayList<>();
                return erro;
            }
        } else {
            List<String> erro = new ArrayList<>();
            return erro;
        }
    }
    
    public ResponseEntity<String> cadastrarServicoPrestado(@RequestBody ServicosPrestadosDto servicosPrestados) {
        System.out.println("entrou");

        Usuario usuario = usuarioRepository.findByEmail(servicosPrestados.getUsuarioEmail())
            .orElseThrow(() -> new RuntimeException(
                "Usuário com e-mail '" + servicosPrestados.getUsuarioEmail() + "' não encontrado"
            ));
        System.out.println("pegou usuário");

        if (servicosPrestados.getServicos() == null || servicosPrestados.getServicos().isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum serviço foi informado.");
        }

        for (Integer servicoId : servicosPrestados.getServicos()) {
            Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço id=" + servicoId + " não encontrado"));
            System.out.println("pegou serviço");

            ServicoPrestado servicoPrestado = new ServicoPrestado(usuario, servico);
            servicoPrestadoRepository.save(servicoPrestado);
            System.out.println("salvou serviço");
        }

        return ResponseEntity.ok("Serviços cadastrados com sucesso");
    }

    public ResponseEntity<?> atualizarServicoPrestado (
        @RequestBody ServicosPrestadosDto servicosPrestadosDto
    ) {

        Usuario usuario = usuarioRepository.findByEmail(servicosPrestadosDto.getUsuarioEmail()).get();
        List<ServicoPrestado> servicosPrestados = servicoPrestadoRepository.findByUsuario(usuario);
        for (ServicoPrestado servicoPrestado : servicosPrestados) {
            servicoPrestado.setDeletado();
            servicoPrestadoRepository.save(servicoPrestado);
        }

        for (Integer servicoId : servicosPrestadosDto.getServicos()) {
            
            Servico servico = servicoRepository.findById(servicoId).get();

            if (servicoPrestadoRepository.findByIdUsuarioServico(servicoId, usuario.getId()).isPresent()) {
                ServicoPrestado servicoPrestado = servicoPrestadoRepository.
                findByIdUsuarioServico(servicoId, usuario.getId()).get();
                servicoPrestado.setDeletadoFalse();
                servicoPrestadoRepository.save(servicoPrestado);
            } else {
                ServicoPrestado servicoPrestado = new ServicoPrestado( usuario, servico);
                servicoPrestadoRepository.save(servicoPrestado);
            }
        }
        Map<String, Object> body = new HashMap<>();
                body.put("mensagem", "Usuário cadastrado com sucesso");
                body.put("usuarioId", usuario.getId()); 
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
