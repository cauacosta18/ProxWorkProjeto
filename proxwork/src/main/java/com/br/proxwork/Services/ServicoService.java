package com.br.proxwork.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.proxwork.Dtos.ServicoDto;
import com.br.proxwork.Dtos.ServicoSaidaDto;
import com.br.proxwork.Entities.Servico;
import com.br.proxwork.Repositories.ServicoRepository;
import com.br.proxwork.Repositories.UsuarioRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public String cadastrarServico(@RequestBody ServicoDto servicoDto) {
        Servico servico = new Servico(servicoDto.getDescricao());
        servicoRepository.save(servico);
        return "Serviço cadastrado com sucesso";
    }

    public String deletarServico (@RequestParam int id) {
        if (!servicoRepository.findById(id).isPresent()) {
            return "Serviço não encontrado";
        } else {
            Servico servico = servicoRepository.findById(id).get();
            if (servico.getDeletado() == false) {
                servico.setDeletado();
                return "Serviço deletado com sucesso";
            } else {
                return "Serviço já deletado";
            }
        }
    }

    public String atualizarServico (
        @PathVariable int id,
        @RequestBody ServicoDto servicoDto
    
    ) {
        if (!servicoRepository.findById(id).isPresent()) {
            return "Serviço não encontrado";
        } else {
            Servico servico = servicoRepository.findById(id).get();
            if (servico.getDeletado() == false) {
                servico.setDescricao(servicoDto.getDescricao());
                servicoRepository.save(servico);
                return "Serviço atualizado com sucesso";
            } else {
                return "Serviço já deletado";
            }
        }
    }

    public List<ServicoSaidaDto> listarServicos () { 
        List<Servico> servicos = servicoRepository.findAll();

        if (servicos.isEmpty()) {
            List<ServicoSaidaDto> erro = new ArrayList<>();
            return erro;
        } else {
            List<ServicoSaidaDto> servicoDtos = new ArrayList<>();
            for (Servico servico : servicos) {
                ServicoSaidaDto servicoDto = new ServicoSaidaDto(servico.getId(), servico.getDescricao());
                if (servico.getDeletado() == false) {
                    servicoDtos.add(servicoDto);
                }
            }
            return servicoDtos;
        }
    }

}
