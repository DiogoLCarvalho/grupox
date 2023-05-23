package com.fatec.sig1.model;

import javax.validation.constraints.NotBlank;

public class AdminDTO {
	
	@NotBlank(message = "Nome é requerido")
	private String nome;
	
	@NotBlank(message = "Sobrenome é requerido")
	private String sobrenome;
		
	@NotBlank(message = "O Email é obrigatório")
	private String email;

	@NotBlank(message = "A senha é obrigatório")
	private String senha;
		
	@NotBlank(message = "A senha é obrigatório")


	public AdminDTO(String nome, String sobrenome,String email, String senha) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
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
	
	public Admin retornaUmCliente() {
		return new Admin(nome, sobrenome, email, senha);
	}

}
