package com.fatec.sig1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	// Classe responsável por reconfigurar opções padrões de segurança do spring security
	
	
	// Por padrão o spring security cria autenticações state, mas no contexto do projeto isso não se aplica. Por isso a mudança
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
	            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .build();
	}
	
	
	// .csrf(csrf -> csrf.disable()) = desabilita o cors, o jwt vai cuidar disso
	// .sessionManagement(sm -> sm.sessionCreationPolicy = muda o tipo de autenticação para stateless, que é usado com APIs
}
