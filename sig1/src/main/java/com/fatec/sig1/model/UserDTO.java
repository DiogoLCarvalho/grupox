package com.fatec.sig1.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;


public class UserDTO {
	@NotBlank(message = "Nome é requerido")
	private String nome;
	
	@NotBlank(message = "Sobrenome é requerido")
	private String sobrenome;
		
	@NotBlank(message = "O Email é obrigatório")
	private String email;

	@NotBlank(message = "A senha é obrigatório")
	private String senha;
		
	private List<Long> favoritos = new ArrayList<>();


	public UserDTO(String nome, String sobrenome,String email, String senha, List<Long> favoritos) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
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


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return new User(nome, sobrenome, email, senha, favoritos);
	}

}

