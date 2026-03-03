package com.br.proxwork.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.proxwork.Dtos.EnderecoDto;
import com.br.proxwork.Dtos.LoginDto;
import com.br.proxwork.Dtos.PessoaDto;
import com.br.proxwork.Dtos.ProvedorDto;
import com.br.proxwork.Dtos.UsuarioAtualizarDto;
import com.br.proxwork.Dtos.UsuarioCompletoSaidaDto;
import com.br.proxwork.Dtos.UsuarioDto;
import com.br.proxwork.Dtos.UsuarioSaidaDto;
import com.br.proxwork.Entities.Endereco;
import com.br.proxwork.Entities.Pessoa;
import com.br.proxwork.Entities.Provedor;
import com.br.proxwork.Entities.Usuario;
import com.br.proxwork.Repositories.PessoaRepository;
import com.br.proxwork.Repositories.ProvedorRepository;
import com.br.proxwork.Repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    ProvedorRepository provedorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    
    public String atualizar(
            @RequestParam String email,
            @RequestBody UsuarioAtualizarDto request) {

        UsuarioDto usuarioDto = request.getUsuarioDto();
        EnderecoDto enderecoDto = request.getEnderecoDto();
        PessoaDto pessoaDto = request.getPessoaDto();
        ProvedorDto provedorDto = request.getProvedorDto();

        if (usuarioRepository.findByEmail(email).isPresent()) {
            Usuario usuario = usuarioRepository.findByEmail(email).get();
            Endereco endereco = usuario.getEndereco();
            Pessoa pessoa = null;
            Provedor provedor = null;
            Boolean cpfExiste = false;
            Boolean emailExiste = false;
            Boolean cnpjExiste = false;

            if (pessoaRepository.findByUsuario(usuario).isPresent()) {
                pessoa = pessoaRepository.findByUsuario(usuario).get();

                if (pessoaRepository.findByCpf(pessoaDto.getCpf()).isPresent()
                        && !pessoa.getCpf().equals(pessoaDto.getCpf())) {
                    cpfExiste = true;
                    System.out.println("CPF já cadastrado.");
                    return "CPF já cadastrado.";
                }

                if (provedorRepository.findByPessoa(pessoa).isPresent()) {
                    provedor = provedorRepository.findByPessoa(pessoa).get();
                }
            }
            

            if (usuarioRepository.findByEmail(usuarioDto.getEmail()).isPresent()
                    && !usuario.getEmail().equals(usuarioDto.getEmail())) {
                emailExiste = true;
                System.out.println("Email já cadastrado.");
                return "Email já cadastrado.";
            }

            System.out.println(emailExiste);

            if (emailExiste == false && cnpjExiste == false && cpfExiste == false) {

                usuario.setNome(usuarioDto.getNome());

                usuario.setEmail(usuarioDto.getEmail());

                usuario.setTelefone(usuarioDto.getTelefone());

                String novaSenha = passwordEncoder.encode(usuarioDto.getSenha());
                usuario.setSenha(novaSenha);

                if (pessoaRepository.findByUsuario(usuario).isPresent()) {

                    pessoa.setCpf(pessoaDto.getCpf());

                    pessoa.setDataNascimento(pessoaDto.getDataNascimento());

                    int genero;

                    if ("Feminino" == pessoaDto.getGenero()) {
                        genero = 1;
                    } else if ("Masculino" == pessoaDto.getGenero()) {
                        genero = 2;
                    } else {
                        genero = 0;
                    }

                    pessoa.setGenero(genero);

                    if (provedorRepository.findByPessoa(pessoa).isPresent()) {

                        provedor.setCaminhoCarteira(provedorDto.getCaminhoCarteira());

                        provedor.setCaminhoCertidao(provedorDto.getCaminhoCertidao());
                    }
                }

                endereco.setCidade(enderecoDto.getCidade());

                endereco.setEstado(enderecoDto.getEstado());

                usuarioRepository.save(usuario);

                if (pessoaRepository.findByUsuario(usuario).isPresent()) {
                    pessoaRepository.save(pessoa);
                    if (provedorRepository.findByPessoa(pessoa).isPresent()) {
                        provedorRepository.save(provedor);
                    }
                }
               
                System.out.println("Usuário atualizado com sucesso");
                return "Usuário atualizado com sucesso";
            } else {
                System.out.println("");
                return "";
            }
        } else {
            System.out.println("Usuário não encontrado.");
            return "Usuário não encontrado.";
        }
    }

    public List<UsuarioCompletoSaidaDto> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado");
            List<UsuarioCompletoSaidaDto> erro = new ArrayList<>();
            return erro;

        } else {
            List<UsuarioCompletoSaidaDto> usuariosCompletos = new ArrayList<>();
            PessoaDto pessoaDto = new PessoaDto();
            ProvedorDto provedorDto = new ProvedorDto();

            for (Usuario usuario : usuarios) {

                UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(usuario.getNome(), usuario.getEmail(), usuario.getTelefone());

                Endereco endereco = usuario.getEndereco();

                EnderecoDto enderecoDto = new EnderecoDto(endereco.getCidade(), endereco.getEstado());

                if (pessoaRepository.findByUsuario(usuario).isPresent()) {
                    Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();

                    pessoaDto = new PessoaDto(pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getGeneroId());

                    if (provedorRepository.findByPessoa(pessoa).isPresent()) {
                        Provedor provedor = provedorRepository.findByPessoa(pessoa).get();

                        provedorDto = new ProvedorDto(provedor.getCaminhoCarteira(), provedor.getCaminhoCertidao(), provedor.getWhatsapp(), provedor.getValorMax(), provedor.getValorMin());
                    }

                }

                usuariosCompletos.add(new UsuarioCompletoSaidaDto(usuarioSaidaDto, enderecoDto, pessoaDto, provedorDto));
            }
            return usuariosCompletos;
        }
    }

    public UsuarioCompletoSaidaDto encontrarUsuario(
        @RequestParam String email
    ) {

        if (!usuarioRepository.findByEmail(email).isPresent()) {
            System.out.println("Nenhum usuário encontrado");
            UsuarioCompletoSaidaDto erro = new UsuarioCompletoSaidaDto();
            return erro;
        } else {
            Usuario usuario = usuarioRepository.findByEmail(email).get();

            PessoaDto pessoaDto = new PessoaDto();
            ProvedorDto provedorDto = new ProvedorDto();

            UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(usuario.getNome(), usuario.getEmail(), usuario.getTelefone());

                Endereco endereco = usuario.getEndereco();

                EnderecoDto enderecoDto = new EnderecoDto( endereco.getCidade(), endereco.getEstado());

                if (pessoaRepository.findByUsuario(usuario).isPresent()) {
                    Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();

                    pessoaDto = new PessoaDto(pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getGeneroId());

                    if (provedorRepository.findByPessoa(pessoa).isPresent()) {
                        Provedor provedor = provedorRepository.findByPessoa(pessoa).get();

                        provedorDto = new ProvedorDto(provedor.getCaminhoCarteira(), provedor.getCaminhoCertidao(), provedor.getWhatsapp(), provedor.getValorMax(), provedor.getValorMin());
                    }

                }

                UsuarioCompletoSaidaDto usuarioCompletoSaidaDto = new UsuarioCompletoSaidaDto(usuarioSaidaDto, enderecoDto, pessoaDto, provedorDto);

                return usuarioCompletoSaidaDto;
        }
    }

    public String deletarUsuario (
        @RequestParam String email
    ) {
        if (!usuarioRepository.findByEmail(email).isPresent()) {
            System.out.println("Usuário não encontrado.");
            return "Usuário não encontrado";
        } else {
            Usuario usuario = usuarioRepository.findByEmail(email).get();

            if (usuario.getDeletado() == false) {
                usuario.setDeletado();
                return "Usuário deletado com sucesso";
            } else {
                return "Usuário já deletado";
            }
            
            
        }
    }

    public String login (
        @RequestBody LoginDto loginDto
    ) {

        if (!usuarioRepository.findByEmail(loginDto.getEmail()).isPresent()) {

            return "Usuário não encontrado";

        } else {

            Usuario usuario = usuarioRepository.findByEmail(loginDto.getEmail()).get();

            if (usuario.getDeletado() == false) {

                if (passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha())) {

                    return "Login realizado com sucesso";

                } else {

                    return "Senha incorreta";

                }

            } else {

                return "Usuário não encontrado";
                
            }
        }
    }
}
