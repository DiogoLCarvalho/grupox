package com.fatec.sig1.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fatec.sig1.model.Admin;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.User;

@Service
public class TokenService {
	
	@Value("${the.most.epic.name.ever}")
	private String secret_master_hiper_key;
	
	//Vc precisa passar a classe quer vc quer autenticar
	public String gerarTokenUser(User usuario) {
		try {
			var algoritmo = Algorithm.HMAC256(secret_master_hiper_key);
			return JWT
					.create()
					.withIssuer("Socieloo")
					.withSubject(usuario.getLogin())
					.withExpiresAt(dataExpiracao())
					.sign(algoritmo);
			
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar token JWT", e);
		}
	}
	
	
	public String gerarTokenAdmin(Admin admin) {
		try {
			var algoritmo = Algorithm.HMAC256(secret_master_hiper_key);
			return JWT
					.create()
					.withIssuer("Socieloo")
					.withSubject(admin.getLogin())
					.withExpiresAt(dataExpiracao())
					.sign(algoritmo);
			
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar token JWT", e);
		}
	}
	
	
	public String gerarTokenOng(Ong ong) {
		try {
			var algoritmo = Algorithm.HMAC256(secret_master_hiper_key);
			return JWT
					.create()
					.withIssuer("Socieloo")
					.withSubject(ong.getLogin())
					.withExpiresAt(dataExpiracao())
					.sign(algoritmo);
			
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar token JWT", e);
		}
	}
	
	
	// Faz o token durar 1 dia
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
	}
}
