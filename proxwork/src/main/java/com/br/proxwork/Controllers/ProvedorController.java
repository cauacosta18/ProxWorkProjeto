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

import com.br.proxwork.Dtos.FiltroDto;
import com.br.proxwork.Dtos.UsuarioCompletoDto;
import com.br.proxwork.Dtos.UsuarioCompletoSaidaDto;
import com.br.proxwork.Repositories.EnderecoRepository;
import com.br.proxwork.Repositories.PessoaRepository;
import com.br.proxwork.Repositories.ProvedorRepository;
import com.br.proxwork.Repositories.UsuarioRepository;
import com.br.proxwork.Services.ProvedorService;

@RestController
@RequestMapping("/provedor")
public class ProvedorController {

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
    ProvedorService provedorService;

    @PostMapping("/cadastrarProvedor")
    public ResponseEntity<?> cadastrarProvedor(
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {

        return provedorService.cadastrarProvedor(usuarioCompletoDto);
    }

    @GetMapping("listarProvedores")
    public List<UsuarioCompletoSaidaDto> listarProvedores() {
        return provedorService.listarProvedores();
    }

    @GetMapping("/encontrarProvedor")
    public UsuarioCompletoSaidaDto encontrarProvedor(@RequestParam String email) {
        return provedorService.encontrarProvedor(email);
    }

    @PutMapping("/atualizarProvedor")
    public ResponseEntity<?> atualizarProvedor(
            @RequestParam String email,
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {

        return provedorService.atualizarProvedor(email, usuarioCompletoDto);

    }

    @PutMapping("/verificarProvedor/{id}")
    public String verificarProvedor(
            @RequestParam String email) {
        return provedorService.verificarProvedor(email);
    }

    @PostMapping("/listarProvedoresFiltro")
    public List<UsuarioCompletoSaidaDto> listarProvedoresFilter (@RequestBody FiltroDto filtroDto) {
        return provedorService.listarProvedoresFilter(filtroDto);
    }

}
