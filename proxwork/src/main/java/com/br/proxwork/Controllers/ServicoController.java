package com.br.proxwork.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.proxwork.Dtos.ServicoDto;
import com.br.proxwork.Dtos.ServicoSaidaDto;
import com.br.proxwork.Repositories.ServicoRepository;
import com.br.proxwork.Services.ServicoService;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    ServicoService servicoService;

    @PostMapping("/cadastrarServico")
    public String cadastrarServico(@RequestBody ServicoDto servicoDto) {
        return servicoService.cadastrarServico(servicoDto);
    }

    @PutMapping("/atualizarServico/{id}")
    public String atualizarServico(
            @PathVariable int id,
            @RequestBody ServicoDto servicoDto) {

        return servicoService.atualizarServico(id, servicoDto);
    }

    @GetMapping("/listarServicos")
    public List<ServicoSaidaDto> listarServicos() {

        return servicoService.listarServicos();
    }

    @DeleteMapping("/deletarServico/{id}")
    public String deletarServico(@PathVariable int id) {

        return servicoService.deletarServico(id);
    }
}
