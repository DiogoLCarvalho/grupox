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
import com.auth0.jwt.exceptions.JWTVerificationException;
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
					.withClaim("role", usuario.getRole())
					.withClaim("id", usuario.getId())
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
					.withClaim("role", admin.getRole())
					.withClaim("id", admin.getId())
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
					.withClaim("role", ong.getRole())
					.withClaim("id", ong.getId())
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
	
	
	// Verifica token
	public String pegaSubject(String tokenJWT) {
		try {
			var algoritmo = Algorithm.HMAC256(secret_master_hiper_key);
			return JWT.require(algoritmo)
					.withIssuer("Socieloo")
					.build()
					.verify(tokenJWT)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new RuntimeException("Token inválido!");
		}
	}

}
