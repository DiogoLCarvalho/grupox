package com.fatec.sig1.model;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdminDTO {
	
	@NotBlank(message = "Nome é requerido")
	private String nome;
	
	@NotBlank(message = "Sobrenome é requerido")
	private String sobrenome;
		
	@NotBlank(message = "O Email é obrigatório")
	private String login;

	@NotBlank(message = "A senha é obrigatório")
	private String senha;
		
	private String token;


	public AdminDTO(String nome, String sobrenome,String login, String senha) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.login = login;
		this.senha = senha;
	}
	
	public AdminDTO(String nome, String sobrenome,String login, String senha, String token) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.login = login;
		this.senha = senha;
		this.setToken(token);
	}
		
	public AdminDTO() {
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

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Admin retornaUmCliente() {
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
		
		return new Admin(nome, sobrenome, login, senhaCriptografada);
	}
	
	
	public Admin retornaUmClientePUT() {
				
		return new Admin(nome, sobrenome, login, senha);
	}

	public AdminDTO retornaUmClienteToken() {
		return new AdminDTO(nome, sobrenome, login, senha, token);
	}


	
}
