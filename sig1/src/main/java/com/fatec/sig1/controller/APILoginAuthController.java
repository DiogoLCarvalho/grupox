package com.fatec.sig1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.sig1.model.DadosAutenticacao;
import com.fatec.sig1.model.User;
import com.fatec.sig1.security.DadosTokenJWT;
import com.fatec.sig1.security.TokenService;

@RestController
@RequestMapping("/api/v1/login")
public class APILoginAuthController {
	
	// Para a validação da autenticação é preciso usar uma classe do próprio spring
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		
		// DTO do spring
		var AuthenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		
		// verifica os dados e armazena a resposta - Essa classe chama a findByLogin
		var autentication = manager.authenticate(AuthenticationToken);
		
		var JwtToken = tokenService.gerarTokenUser((User) autentication.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJWT(JwtToken));
	}
	
	
}
