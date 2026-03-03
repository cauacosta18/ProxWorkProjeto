package com.br.proxwork.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.proxwork.Dtos.LoginDto;
import com.br.proxwork.Dtos.UsuarioAtualizarDto;
import com.br.proxwork.Dtos.UsuarioCompletoSaidaDto;
import com.br.proxwork.Repositories.EnderecoRepository;
import com.br.proxwork.Repositories.PessoaRepository;
import com.br.proxwork.Repositories.ProvedorRepository;
import com.br.proxwork.Repositories.UsuarioRepository;
import com.br.proxwork.Services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

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
    UsuarioService usuarioService;

    
    @PutMapping("/atualizar/{id}")
    public String atualizar(
            @RequestParam String email,
            @RequestBody UsuarioAtualizarDto usuarioAtualizarDto
    ) {

        return usuarioService.atualizar(email, usuarioAtualizarDto);
    }

    @GetMapping("/listarUsuarios")
    public List<UsuarioCompletoSaidaDto> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/encontrarUsuario/{id}")
    public UsuarioCompletoSaidaDto encontrarUsuario(@RequestParam String email) {
        return usuarioService.encontrarUsuario(email);
    }

    @DeleteMapping("/deletarUsuario/{id}")
    public String deletarUsuario(@RequestParam String email) {
        return usuarioService.deletarUsuario(email);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return usuarioService.login(loginDto);
    }
}