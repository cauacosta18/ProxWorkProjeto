package com.br.proxwork.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.proxwork.Dtos.ServicosPrestadosDto;
import com.br.proxwork.Entities.Servico;
import com.br.proxwork.Entities.ServicoPrestado;
import com.br.proxwork.Entities.Usuario;
import com.br.proxwork.Repositories.ServicoPrestadoRepository;
import com.br.proxwork.Repositories.ServicoRepository;
import com.br.proxwork.Repositories.UsuarioRepository;
import com.br.proxwork.Services.ServicoPrestadoService;


@RestController
@RequestMapping("/servicoPrestado")
public class ServicoPrestadoController {

    @Autowired
    ServicoPrestadoRepository servicoPrestadoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    ServicoPrestadoService servicoPrestadoService;

    @PostMapping("/cadastrarServicoPrestado")
    public ResponseEntity<String> cadastrarServicoPrestado(
            @RequestBody ServicosPrestadosDto servicosPrestadosDto) {

        return servicoPrestadoService.cadastrarServicoPrestado(servicosPrestadosDto);
    }

    @PutMapping("/atualizarServicoPrestado")
    public ResponseEntity<?> atualizarServicoPrestado(
            @RequestBody ServicosPrestadosDto servicosPrestadosDto) {

        return servicoPrestadoService.atualizarServicoPrestado(servicosPrestadosDto);
    }

    @GetMapping("/listarServicosPrestadosPorPrestador")
    public List<String> listarServicosPorPrestador(@RequestParam String email) {
        return servicoPrestadoService.listarServicosPorPrestador(email);
    }

    @GetMapping("/encontrarServicoPrestado/{id}")
    public String encontrarServicoPrestado(@PathVariable int id) {

        if (!servicoPrestadoRepository.findById(id).isPresent()) {
            System.out.println("Nenhum serviço Prestado foi encontrado");
            return "Nenhum serviço Prestado foi encontrado";
        } else {
            ServicoPrestado servicoPrestado = servicoPrestadoRepository.findById(id).get();

            System.out.println("------------");

            Usuario usuario = servicoPrestado.getUsuario();
            Servico servico = servicoPrestado.getServico();

            String info = String.format(
                    "Serviço Prestado: " +
                            "Serviço: Descrição: %s | " +
                            "Usuário: Nome: %s Email: %s | ",
                    servico.getDescricao(),
                    usuario.getNome(),
                    usuario.getEmail());

            System.out.println(info);
            return info;
        }
    }

    @GetMapping("/encontrarServicoPrestadoPorUsuario/{id}")
    public String encontrarServicoPrestadoPorUsuario(@PathVariable int id) {

        if (!usuarioRepository.findById(id).isPresent()) {
            System.out.println("Nenhum Usuario foi encontrado");
            return "Nenhum Usuario foi encontrado";
        } else {

            Usuario usuario = usuarioRepository.findById(id).get();
            StringBuilder resposta = new StringBuilder();

            List<ServicoPrestado> servicoPrestados = usuario.getServicosPrestados();

            if (servicoPrestados.isEmpty()) {
                System.out.println("Nenhum serviço Prestado foi encontrado");
                return "Nenhum serviço Prestado foi encontrado";
            } else {
                for (ServicoPrestado servicoPrestado : servicoPrestados) {
                    System.out.println("------------");

                    Servico servico = servicoPrestado.getServico();

                    String info = String.format(
                            "Serviço Prestado: " +
                                    "Serviço: Descrição: %s | " +
                                    "Usuário: Nome: %s Email: %s | ",
                            
                            servico.getDescricao(),
                            usuario.getNome(),
                            usuario.getEmail());

                    System.out.println(info);
                    resposta.append(info);
                }
                return resposta.toString();
            }
        }
    }

}
