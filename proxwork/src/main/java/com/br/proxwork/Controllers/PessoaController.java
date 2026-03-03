package com.br.proxwork.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.proxwork.Dtos.UsuarioCompletoDto;
import com.br.proxwork.Dtos.UsuarioCompletoSaidaDto;
import com.br.proxwork.Repositories.EnderecoRepository;
import com.br.proxwork.Repositories.PessoaRepository;
import com.br.proxwork.Repositories.ProvedorRepository;
import com.br.proxwork.Repositories.UsuarioRepository;
import com.br.proxwork.Services.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    ProvedorRepository provedorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PessoaService pessoaService;

    @PostMapping("/cadastrarPessoa")
    public String cadastrarPessoa(
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {
        return pessoaService.cadastrarPessoa(usuarioCompletoDto);
    }

    @GetMapping("listarPessoas")
    public List<UsuarioCompletoSaidaDto> listarPessoas() {
        return pessoaService.listarPessoas();
    }

    @GetMapping("/encontrarPessoa")
    public UsuarioCompletoSaidaDto encontrarPessoa(@RequestParam String email) {

        return pessoaService.encontrarPessoa(email);
    }

    @PutMapping("/atualizarPessoa")
    public ResponseEntity<?> atualizar(
            @RequestParam String email,
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {

        return pessoaService.atualizarPessoa(email, usuarioCompletoDto);
    }

}
