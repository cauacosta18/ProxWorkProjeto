package com.br.proxwork.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.proxwork.Services.PessoaService;
import com.br.proxwork.Services.ProvedorService;
import com.br.proxwork.Services.ServicoPrestadoService;
import com.br.proxwork.Services.ServicoService;
import com.br.proxwork.Services.UsuarioService;

@Configuration
public class AppConfig {
    
    @Bean
    public UsuarioService usuarioService () {
        return new UsuarioService();
    }

    @Bean
    public ProvedorService provedorService () {
        return new ProvedorService();
    }

    @Bean
    public PessoaService pessoaService () {
        return new PessoaService();
    }

    @Bean
    public ServicoService servicoService () {
        return new ServicoService();
    }

    @Bean
    public ServicoPrestadoService servicoPrestadoService () {
        return new ServicoPrestadoService();
    }
}
