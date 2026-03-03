package com.br.proxwork.Configurations;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import org.springframework.http.HttpStatus;

import org.springframework.security.config.Customizer;

import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class Security {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/v3/api-docs/swagger-config",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/webjars/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(b -> {
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository repo) {
        return username -> repo.findByEmail(username)
                .map(p -> org.springframework.security.core.userdetails.User
                        .withUsername(p.getEmail())
                        .password(p.getSenha()) // precisa estar BCRYPT
                        .roles("USER") // vira ROLE_USER
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    } 
               
    
     
     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     return http
     .csrf(csrf -> csrf.disable())
     .authorizeHttpRequests(auth -> auth
     .anyRequest().permitAll())
     .httpBasic(org.springframework.security.config.Customizer.withDefaults()).
     build();
     } */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/provedor/cadastrarProvedor").permitAll()
                .requestMatchers(HttpMethod.POST, "/servicoPrestado/cadastrarServicoPrestado").permitAll()
                .requestMatchers(HttpMethod.POST, "/servico/cadastrarServico").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/provedor/listarProvedores").permitAll()
                .requestMatchers(HttpMethod.GET, "/provedor/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/provedor/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/provedor/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/servicoPrestado/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/servicoPrestado/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/servicoPrestado/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/*").permitAll()

                .requestMatchers(
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/v3/api-docs/swagger-config",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()

                .anyRequest().permitAll()
            )
    
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
