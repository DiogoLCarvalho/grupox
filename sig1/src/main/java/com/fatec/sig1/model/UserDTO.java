package com.fatec.sig1.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserDTO {
	@NotBlank(message = "Nome é requerido")
	private String nome;
	
	@NotBlank(message = "Sobrenome é requerido")
	private String sobrenome;
		
	@NotBlank(message = "O Email é obrigatório")
	private String login;

	@NotBlank(message = "A senha é obrigatório")
	private String senha;
		
	private List<Long> favoritos = new ArrayList<>();
	
	private String token;


	public UserDTO(String nome, String sobrenome,String login, String senha, List<Long> favoritos, String token) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.login = login;
		this.senha = senha;
		this.token = token;
		this.setFavoritos(favoritos);
	}
		
	public UserDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome(String sobrenome) {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	

	public List<Long> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Long> favoritos) {
		this.favoritos = favoritos;
	}
	
	public User retornaUmCliente() {
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);

		return new User(nome, sobrenome, login, senhaCriptografada, favoritos);
	}
	
	public User retornaUmClientePUT() {

		return new User(nome, sobrenome, login, senha, favoritos);
	}
	
	public UserDTO retornaUmClienteComToken() {
		return new UserDTO(nome, sobrenome, login, senha, favoritos, token);
	}

}

