package com.br.proxwork.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.proxwork.Dtos.EnderecoDto;
import com.br.proxwork.Dtos.PessoaDto;
import com.br.proxwork.Dtos.UsuarioDto;
import com.br.proxwork.Dtos.UsuarioSaidaDto;
import com.br.proxwork.Dtos.UsuarioCompletoDto;
import com.br.proxwork.Dtos.UsuarioCompletoSaidaDto;
import com.br.proxwork.Entities.Endereco;
import com.br.proxwork.Entities.Pessoa;
import com.br.proxwork.Entities.Usuario;
import com.br.proxwork.Repositories.EnderecoRepository;
import com.br.proxwork.Repositories.PessoaRepository;
import com.br.proxwork.Repositories.ProvedorRepository;
import com.br.proxwork.Repositories.UsuarioRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class PessoaService {

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

    public String cadastrarPessoa(
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {
        EnderecoDto enderecoDto = usuarioCompletoDto.getEnderecoDto();
        UsuarioDto usuarioDto = usuarioCompletoDto.getUsuarioDto();
        PessoaDto pessoaDto = usuarioCompletoDto.getPessoaDto();

        if (usuarioRepository.findByEmail(usuarioDto.getEmail()).isPresent()) {
            return "Email já cadastrado";
        } else {
            if (pessoaRepository.findByCpf(pessoaDto.getCpf()).isPresent()) {
                return "CPF já cadastrado";
            } else {

                Endereco endereco = new Endereco(enderecoDto.getCidade(), enderecoDto.getEstado());

                String senha = passwordEncoder.encode(usuarioDto.getSenha());

                Usuario usuario = new Usuario(usuarioDto.getNome(), usuarioDto.getEmail(), usuarioDto.getTelefone(),
                        endereco, senha);

                int genero;

                if ("Feminino" == pessoaDto.getGenero()) {
                    genero = 1;
                } else if ("Masculino" == pessoaDto.getGenero()) {
                    genero = 2;
                } else {
                    genero = 0;
                }

                Pessoa pessoa = new Pessoa(pessoaDto.getCpf(), pessoaDto.getDataNascimento(), genero,
                        usuario);

                if (endereco.getEstado().equals("")) {
                    return "Estado não encontrado";
                }

                enderecoRepository.save(endereco);
                usuarioRepository.save(usuario);
                pessoaRepository.save(pessoa);

                return "Usuário cadastrado com sucesso";
            }
        }
    }

    public ResponseEntity<?> atualizarPessoa(
            @RequestParam String email,
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {
        if (!usuarioRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        } else {
            System.out.println("vai pegar usuário");
            Usuario usuario = usuarioRepository.findByEmail(email).get();
            System.out.println("pegou usuário");
            if (usuario.getDeletado() == false) {

                if (!passwordEncoder.matches(usuarioCompletoDto.getUsuarioDto().getSenha(), usuario.getSenha())) {
                    return ResponseEntity.badRequest().body("Senha incorreta");
                }

                if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent() && !usuario.getEmail().equals(email)) {
                    return ResponseEntity.badRequest().body("Email já cadastrado.");
                }

                System.out.println("vai pegar pessoa");
                Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();
                System.out.println("Pegou pessoa");

                Endereco endereco = usuario.getEndereco();

                UsuarioDto usuarioDto = usuarioCompletoDto.getUsuarioDto();

                PessoaDto pessoaDto = usuarioCompletoDto.getPessoaDto();

                EnderecoDto enderecoDto = usuarioCompletoDto.getEnderecoDto();

                endereco.setCidade(enderecoDto.getCidade());
                endereco.setEstado(enderecoDto.getEstado());

                usuario.setNome(usuarioDto.getNome());
                usuario.setEmail(usuarioDto.getEmail());
                usuario.setTelefone(usuarioDto.getTelefone());

                int genero;

                if ("Feminino" == pessoaDto.getGenero()) {
                    genero = 1;
                } else if ("Masculino" == pessoaDto.getGenero()) {
                    genero = 2;
                } else {
                    genero = 0;
                }

                pessoa.setCpf(pessoaDto.getCpf());
                pessoa.setGenero(genero);

                if (endereco.getEstado().equals("")) {
                    ResponseEntity.badRequest().body("Estado não encontrado");
                }

                enderecoRepository.save(endereco);
                usuarioRepository.save(usuario);
                pessoaRepository.save(pessoa);

                Map<String, Object> body = new HashMap<>();
                body.put("mensagem", "Usuário cadastrado com sucesso");
                body.put("usuarioId", usuario.getId());

                return ResponseEntity.status(HttpStatus.CREATED).body(body);

            } else {
                return ResponseEntity.badRequest().body("Usuário deletado");
            }
        }
    }

    public List<UsuarioCompletoSaidaDto> listarPessoas() {

        List<Pessoa> pessoas = pessoaRepository.findAll();

        if (pessoas.isEmpty()) {
            System.out.println("Nenhum provedor encontrado");
            List<UsuarioCompletoSaidaDto> erro = new ArrayList<>();
            return erro;
        } else {
            List<UsuarioCompletoSaidaDto> usuariosCompletos = new ArrayList<>();

            for (Pessoa pessoa : pessoas) {

                PessoaDto pessoaDto = new PessoaDto(pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getGeneroId());

                Usuario usuario = pessoa.getUsuario();

                UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(usuario.getNome(), usuario.getEmail(),
                        usuario.getTelefone());

                Endereco endereco = usuario.getEndereco();

                EnderecoDto enderecoDto = new EnderecoDto(endereco.getCidade(), endereco.getEstado());

                UsuarioCompletoSaidaDto usuarioCompletoSaidaDto = new UsuarioCompletoSaidaDto(usuarioSaidaDto,
                        enderecoDto, pessoaDto, null);

                if (usuario.getDeletado() == false) {
                    usuariosCompletos.add(usuarioCompletoSaidaDto);
                }

            }

            return usuariosCompletos;
        }
    }

    public UsuarioCompletoSaidaDto encontrarPessoa(
            @RequestParam String email) {
        if (!usuarioRepository.findByEmail(email).isPresent()) {
            System.out.println("Nenhum usuário encontrado");
            UsuarioCompletoSaidaDto erro = new UsuarioCompletoSaidaDto();
            return erro;
        } else {
            Usuario usuario = usuarioRepository.findByEmail(email).get();

            PessoaDto pessoaDto = new PessoaDto();

            UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(usuario.getNome(), usuario.getEmail(),
                    usuario.getTelefone());

            Endereco endereco = usuario.getEndereco();

            EnderecoDto enderecoDto = new EnderecoDto(endereco.getCidade(), endereco.getEstado());

            if (pessoaRepository.findByUsuario(usuario).isPresent()) {
                Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();

                pessoaDto = new PessoaDto(pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getGeneroId());

            }

            UsuarioCompletoSaidaDto usuarioCompletoSaidaDto = new UsuarioCompletoSaidaDto(usuarioSaidaDto, enderecoDto,
                    pessoaDto, null);

            return usuarioCompletoSaidaDto;
        }
    }
}
