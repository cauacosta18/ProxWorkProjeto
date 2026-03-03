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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.proxwork.Dtos.EnderecoDto;
import com.br.proxwork.Dtos.FiltroDto;
import com.br.proxwork.Dtos.PessoaDto;
import com.br.proxwork.Dtos.ProvedorDto;
import com.br.proxwork.Dtos.UsuarioCompletoDto;
import com.br.proxwork.Dtos.UsuarioCompletoSaidaDto;
import com.br.proxwork.Dtos.UsuarioDto;
import com.br.proxwork.Dtos.UsuarioSaidaDto;
import com.br.proxwork.Entities.Endereco;
import com.br.proxwork.Entities.Pessoa;
import com.br.proxwork.Entities.Provedor;
import com.br.proxwork.Entities.Usuario;
import com.br.proxwork.Repositories.*;

@Service
public class ProvedorService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    ProvedorRepository provedorRepository;

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    ServicoPrestadoRepository servicoPrestadoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> cadastrarProvedor(
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {
        EnderecoDto enderecoDto = usuarioCompletoDto.getEnderecoDto();
        UsuarioDto usuarioDto = usuarioCompletoDto.getUsuarioDto();
        PessoaDto pessoaDto = usuarioCompletoDto.getPessoaDto();
        ProvedorDto provedorDto = usuarioCompletoDto.getProvedorDto();

        if (usuarioRepository.findByEmail(usuarioDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado.");
        } else {
            if (pessoaRepository.findByCpf(pessoaDto.getCpf()).isPresent()) {
                return ResponseEntity.badRequest().body("CPF já cadastrado");
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

                Provedor provedor = new Provedor(provedorDto.getCaminhoCarteira(), provedorDto.getCaminhoCertidao(),
                        pessoa, provedorDto.getWhatsapp(), provedorDto.getValorMax(), provedorDto.getValorMin());

                if (endereco.getEstado().equals("")) {
                    return ResponseEntity.badRequest().body("Estado não encontrado");
                }

                enderecoRepository.save(endereco);
                usuarioRepository.save(usuario);
                pessoaRepository.save(pessoa);
                provedorRepository.save(provedor);

                Map<String, Object> body = new HashMap<>();
                body.put("mensagem", "Usuário cadastrado com sucesso");
                body.put("usuarioId", usuario.getId());

                return ResponseEntity.status(HttpStatus.CREATED).body(body);
            }
        }
    }

    public List<UsuarioCompletoSaidaDto> listarProvedores() {
        List<Provedor> provedores = provedorRepository.findAll();

        if (provedores.isEmpty()) {
            System.out.println("Nenhum provedor encontrado");
            List<UsuarioCompletoSaidaDto> erro = new ArrayList<>();
            return erro;
        } else {
            List<UsuarioCompletoSaidaDto> usuariosCompletos = new ArrayList<>();

            for (Provedor provedor : provedores) {

                ProvedorDto provedorDto = new ProvedorDto(provedor.getCaminhoCarteira(), provedor.getCaminhoCertidao(),
                        provedor.getWhatsapp(), provedor.getValorMax(), provedor.getValorMin());

                Pessoa pessoa = provedor.getPessoa();

                PessoaDto pessoaDto = new PessoaDto(pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getGeneroId());

                Usuario usuario = pessoa.getUsuario();

                UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(usuario.getNome(), usuario.getEmail(),
                        usuario.getTelefone());

                Endereco endereco = usuario.getEndereco();

                EnderecoDto enderecoDto = new EnderecoDto(endereco.getCidade(), endereco.getEstado());

                UsuarioCompletoSaidaDto usuarioCompletoSaidaDto = new UsuarioCompletoSaidaDto(usuarioSaidaDto,
                        enderecoDto, pessoaDto, provedorDto);

                if (usuario.getDeletado() == false) {
                    usuariosCompletos.add(usuarioCompletoSaidaDto);
                }

            }

            return usuariosCompletos;
        }
    }

    public UsuarioCompletoSaidaDto encontrarProvedor(
            @RequestParam String email) {
        if (!usuarioRepository.findByEmail(email).isPresent()) {
            System.out.println("Usuário não encontrado");
            UsuarioCompletoSaidaDto erro = new UsuarioCompletoSaidaDto();
            return erro;
        } else {

            Usuario usuario = usuarioRepository.findByEmail(email).get();

            if (usuario.getDeletado() == false) {

                Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();

                if (!provedorRepository.findByPessoa(pessoa).isPresent()) {
                    System.out.println("Usuário não encontrado");
                    UsuarioCompletoSaidaDto erro = new UsuarioCompletoSaidaDto();
                    return erro;
                } else {

                    Provedor provedor = provedorRepository.findByPessoa(pessoa).get();

                    UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(usuario.getNome(), usuario.getEmail(),
                            usuario.getTelefone());

                    PessoaDto pessoaDto = new PessoaDto(pessoa.getCpf(), pessoa.getDataNascimento(),
                            pessoa.getGeneroId());

                    ProvedorDto provedorDto = new ProvedorDto(provedor.getCaminhoCarteira(),
                            provedor.getCaminhoCertidao(), provedor.getWhatsapp(), provedor.getValorMax(),
                            provedor.getValorMin());

                    Endereco endereco = usuario.getEndereco();

                    EnderecoDto enderecoDto = new EnderecoDto(endereco.getCidade(), endereco.getEstado());

                    UsuarioCompletoSaidaDto usuarioCompletoSaidaDto = new UsuarioCompletoSaidaDto(usuarioSaidaDto,
                            enderecoDto, pessoaDto, provedorDto);

                    return usuarioCompletoSaidaDto;
                }

            } else {
                System.out.println("Usuário não encontrado");
                UsuarioCompletoSaidaDto erro = new UsuarioCompletoSaidaDto();
                return erro;
            }
        }
    }

    public ResponseEntity<?> atualizarProvedor(
            @RequestParam String email,
            @RequestBody UsuarioCompletoDto usuarioCompletoDto) {
        if (!usuarioRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        } else {
            Usuario usuario = usuarioRepository.findByEmail(email).get();
            if (usuario.getDeletado() == false) {

                if (!passwordEncoder.matches(usuarioCompletoDto.getUsuarioDto().getSenha(), usuario.getSenha())) {
                    return ResponseEntity.badRequest().body("Senha incorreta");
                }

                if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()
                        && !usuario.getEmail().equals(email)) {
                    return ResponseEntity.badRequest().body("Email já cadastrado.");
                }

                Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();

                Provedor provedor = provedorRepository.findByPessoa(pessoa).get();

                Endereco endereco = usuario.getEndereco();

                UsuarioDto usuarioDto = usuarioCompletoDto.getUsuarioDto();

                PessoaDto pessoaDto = usuarioCompletoDto.getPessoaDto();

                ProvedorDto provedorDto = usuarioCompletoDto.getProvedorDto();

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

                provedor.setCaminhoCarteira(provedorDto.getCaminhoCarteira());
                provedor.setCaminhoCertidao(provedorDto.getCaminhoCertidao());
                provedor.setWhatsapp(provedorDto.getWhatsapp());

                if (endereco.getEstado().equals("")) {
                    return ResponseEntity.badRequest().body("Estado não encontrado");
                }

                enderecoRepository.save(endereco);
                usuarioRepository.save(usuario);
                pessoaRepository.save(pessoa);
                provedorRepository.save(provedor);

                Map<String, Object> body = new HashMap<>();
                body.put("mensagem", "Usuário cadastrado com sucesso");
                body.put("usuarioId", usuario.getId());

                return ResponseEntity.status(HttpStatus.CREATED).body(body);

            } else {
                return ResponseEntity.badRequest().body("Usuário deletado");
            }
        }

    }

    public String verificarProvedor(
            @RequestParam String email) {
        if (!usuarioRepository.findByEmail(email).isPresent()) {
            return "Usuário não encontrado";
        } else {
            Usuario usuario = usuarioRepository.findByEmail(email).get();
            Pessoa pessoa = pessoaRepository.findByUsuario(usuario).get();

            if (usuario.getDeletado() == false) {
                if (!provedorRepository.findByPessoa(pessoa).isPresent()) {
                    return "Usuário não encontrado";
                } else {
                    Provedor provedor = provedorRepository.findByPessoa(pessoa).get();
                    provedor.setVerificado();
                    return "Usuário verificado com sucesso";
                }
            } else {
                return "Usuário já deletado";
            }

        }
    }

    public List<UsuarioCompletoSaidaDto> listarProvedoresFiltro(
            @RequestBody FiltroDto filtroDto) {

        List<Provedor> provedores = provedorRepository.findAll();

        if (provedores.isEmpty()) {
            System.out.println("Nenhum provedor encontrado");
            List<UsuarioCompletoSaidaDto> erro = new ArrayList<>();
            return erro;
        } else {
            List<UsuarioCompletoSaidaDto> usuariosCompletos = new ArrayList<>();

            for (Provedor provedor : provedores) {

                ProvedorDto provedorDto = new ProvedorDto(provedor.getCaminhoCarteira(), provedor.getCaminhoCertidao(),
                        provedor.getWhatsapp(), provedor.getValorMax(), provedor.getValorMin());

                Pessoa pessoa = provedor.getPessoa();

                PessoaDto pessoaDto = new PessoaDto(pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getGeneroId());

                Usuario usuario = pessoa.getUsuario();

                UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(usuario.getNome(), usuario.getEmail(),
                        usuario.getTelefone());

                Endereco endereco = usuario.getEndereco();

                EnderecoDto enderecoDto = new EnderecoDto(endereco.getCidade(), endereco.getEstado());

                UsuarioCompletoSaidaDto usuarioCompletoSaidaDto = new UsuarioCompletoSaidaDto(usuarioSaidaDto,
                        enderecoDto, pessoaDto, provedorDto);

                if (usuario.getDeletado() == false) {

                    if (!filtroDto.getNome().equals("")) {
                        if (!filtroDto.getNome().equals(usuarioSaidaDto.getNome())) {

                        } else {
                            usuariosCompletos.add(usuarioCompletoSaidaDto);
                        }
                    }
                    if (!filtroDto.getEmail().equals("")) {
                        if (!filtroDto.getEmail().equals(usuarioSaidaDto.getEmail())) {

                        } else {
                            usuariosCompletos.add(usuarioCompletoSaidaDto);
                        }
                    }
                    if (!(filtroDto.getValorMin() == 0 && filtroDto.getValorMax() == 0)) {
                        Double media = (provedorDto.getValorMin() + provedorDto.getValorMax()) / 2;
                        if (filtroDto.getValorMax() <= media || filtroDto.getValorMin() >= media) {

                        } else {
                            usuariosCompletos.add(usuarioCompletoSaidaDto);
                        }
                    }
                    if (!filtroDto.getEstado().equals("")) {
                        if (!filtroDto.getEstado().equals(enderecoDto.getEstado())) {

                        } else {
                            usuariosCompletos.add(usuarioCompletoSaidaDto);
                        }
                    } else {
                        usuariosCompletos.add(usuarioCompletoSaidaDto);
                    }

                }

            }

            return usuariosCompletos;
        }
    }

    public List<UsuarioCompletoSaidaDto> listarProvedoresFilter(@RequestBody FiltroDto filtroDto) {

        List<Provedor> provedores = provedorRepository.findAll();

        if (provedores.isEmpty()) {
            System.out.println("Nenhum provedor encontrado");
            return new ArrayList<>();
        } else {
            List<UsuarioCompletoSaidaDto> usuariosCompletos = new ArrayList<>();

            for (Provedor provedor : provedores) {

                Pessoa pessoa = provedor.getPessoa();
                Usuario usuario = pessoa.getUsuario();
                Endereco endereco = usuario.getEndereco();

                if (usuario.getDeletado() == Boolean.TRUE) {
                    continue;
                }

                String nomeFiltro = filtroDto.getNome() != null ? filtroDto.getNome() : "";
                String emailFiltro = filtroDto.getEmail() != null ? filtroDto.getEmail() : "";
                String estadoFiltro = filtroDto.getEstado() != null ? filtroDto.getEstado() : "";
                double faixaMin = filtroDto.getValorMin() != null ? filtroDto.getValorMin() : 0.0;
                double faixaMax = filtroDto.getValorMax() != null ? filtroDto.getValorMax() : 0.0;
                boolean filtraMedia = !(faixaMin == 0.0 && faixaMax == 0.0);
                List<Integer> servicos = filtroDto.getServicos() != null ? filtroDto.getServicos() : new ArrayList<>();

                boolean passa = true;

                if (!nomeFiltro.equals("")) {
                    String nomeUsuario = usuario.getNome() != null ? usuario.getNome() : "";
                    if (!nomeUsuario.equals(nomeFiltro)) {
                        passa = false;
                    }
                }

                if (!emailFiltro.equals("")) {
                    String emailUsuario = usuario.getEmail() != null ? usuario.getEmail() : "";
                    if (!emailUsuario.equals(emailFiltro)) {
                        passa = false;
                    }
                }

                if (filtraMedia) {
                    Double vMinObj = provedor.getValorMin();
                    Double vMaxObj = provedor.getValorMax();
                    if (vMinObj == null || vMaxObj == null) {
                        passa = false;
                    } else {
                        double media = (vMinObj + vMaxObj) / 2.0;
                        if (media < faixaMin || media > faixaMax) {
                            passa = false;
                        }
                    }
                }

                if (!estadoFiltro.equals("")) {
                    String estadoUsuario = (endereco != null && endereco.getEstado() != null) ? endereco.getEstado()
                            : "";
                    if (!estadoUsuario.equals(estadoFiltro)) {
                        passa = false;
                    }
                }

                if (!servicos.isEmpty()) {
                    Boolean encontrou = false;
                    for (Integer servico : servicos) {
                        if (!servicoPrestadoRepository.findByIdUsuarioServico(usuario.getId(), servico).isEmpty()) {
                            encontrou = true;
                        }
                    }
                    if (encontrou == false) {
                        passa = false;
                    }
                }

                if (passa) {
                    ProvedorDto provedorDto = new ProvedorDto(
                            provedor.getCaminhoCarteira(),
                            provedor.getCaminhoCertidao(),
                            provedor.getWhatsapp(),
                            provedor.getValorMax(),
                            provedor.getValorMin());

                    PessoaDto pessoaDto = new PessoaDto(
                            pessoa.getCpf(),
                            pessoa.getDataNascimento(),
                            pessoa.getGeneroId());

                    UsuarioSaidaDto usuarioSaidaDto = new UsuarioSaidaDto(
                            usuario.getNome(),
                            usuario.getEmail(),
                            usuario.getTelefone());

                    EnderecoDto enderecoDto = new EnderecoDto(
                            endereco != null ? endereco.getCidade() : null,
                            endereco != null ? endereco.getEstado() : null);

                    UsuarioCompletoSaidaDto usuarioCompletoSaidaDto = new UsuarioCompletoSaidaDto(
                            usuarioSaidaDto, enderecoDto, pessoaDto, provedorDto);

                    usuariosCompletos.add(usuarioCompletoSaidaDto);
                }
            }

            System.out.println(usuariosCompletos);
            return usuariosCompletos;
        }
    }

}
