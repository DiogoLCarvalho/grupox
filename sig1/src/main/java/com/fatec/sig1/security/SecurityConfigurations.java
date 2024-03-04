package com.fatec.sig1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	// Classe responsável por reconfigurar opções padrões de segurança do spring security
	
	@Autowired
	private SecurityFilter SecurityFilter;
	
	// Por padrão o spring security cria autenticações state, mas no contexto do projeto isso não se aplica. Por isso a mudança
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
		        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		        .authorizeHttpRequests(req -> {
		            req.requestMatchers("/api/v1/user/login").permitAll()
		            .requestMatchers("/api/v1/admin/login").permitAll()
		            .requestMatchers("/api/v1/ong/login").permitAll()
		            .requestMatchers("/api/v1/ong").permitAll()
		            .requestMatchers("/api/v1/ong/{id}").permitAll()
		            .requestMatchers("/api/v1/user").permitAll()
		            ;
		            req.anyRequest().authenticated();
		        }).addFilterBefore(SecurityFilter, UsernamePasswordAuthenticationFilter.class)
		    .build();
		
	}
	
	// .csrf(csrf -> csrf.disable()) = desabilita o cors, o jwt vai cuidar disso
	// .sessionManagement(sm -> sm.sessionCreationPolicy = muda o tipo de autenticação para stateless, que é usado com APIs
	// authorizeHttpRequests - rotas que não precisam de autorização
	// addFilterBefore - faz com que o filtro personalizado venha antes do filtro do spring
	
	// Classe usada no controller de Login, serve para criar uma instância automática
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	// Configura Hash ao usuario passar uma senha
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
