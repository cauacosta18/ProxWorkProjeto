package com.br.proxwork.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.br.proxwork.Configurations.JwtUtil;
import com.br.proxwork.Dtos.LoginDto;
import com.br.proxwork.Dtos.MeDto;
import com.br.proxwork.Dtos.RefreshRequest;
import com.br.proxwork.Dtos.TokenResponseDto;
import com.br.proxwork.Entities.Pessoa;
import com.br.proxwork.Entities.Usuario;
import com.br.proxwork.Repositories.PessoaRepository;
import com.br.proxwork.Repositories.ProvedorRepository;
import com.br.proxwork.Repositories.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProvedorRepository provedorRepository;

    public ResponseEntity<?> login(LoginDto request) {

        System.out.println(request.getEmail() + " - " + request.getSenha());
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }

        Usuario usuario = userOpt.get();

        System.out.println(usuario.getEmail() + " - " + usuario.getSenha());

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida");
        }

        String accessToken = JwtUtil.generateAccessToken(usuario.getEmail());
        String refreshToken = JwtUtil.generateRefreshToken(usuario.getEmail());

        return ResponseEntity.ok(new TokenResponseDto(accessToken, refreshToken));
    }

    public ResponseEntity<?> refresh(RefreshRequest request) {
        try {
            String username = JwtUtil.validateToken(request.getRefreshToken());
            String newAccessToken = JwtUtil.generateAccessToken(username);
            return ResponseEntity.ok(new TokenResponseDto(newAccessToken, request.getRefreshToken()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido");
        }
    }
    
    public ResponseEntity<MeDto> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Boolean provedor = false;

            if (pessoaRepository.findByUsuario(usuario).isPresent()) {
                Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();
                if (provedorRepository.findByPessoa(pessoa).isPresent()) {
                    provedor = true;
                }
            }
            System.out.println(provedor);
        return ResponseEntity.ok(new MeDto(usuario.getNome(), usuario.getEmail(), provedor));
    }

}
