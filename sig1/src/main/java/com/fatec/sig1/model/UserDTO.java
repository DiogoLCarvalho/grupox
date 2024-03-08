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
	
	private long id;

	public UserDTO(String nome, long id, String sobrenome,String login, List<Long> favoritos, String token, String senha) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.login = login;
		this.token = token;
		this.senha = senha;
		this.id = id;
		this.setFavoritos(favoritos);
	}
		
	public UserDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public UserDTO retornaUmClienteComToken() {
		return new UserDTO(nome, id, sobrenome, login, favoritos, token, senha);
	}

}

