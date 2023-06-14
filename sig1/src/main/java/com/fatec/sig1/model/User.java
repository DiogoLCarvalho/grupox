package com.fatec.sig1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Long id;
	
    @JsonView(Views.Public.class)
	@NotBlank(message = "Nome é requerido")
	private String nome;
	
	@NotBlank(message = "Sobrenome é requerido")
	private String sobrenome;
		
	@NotBlank(message = "O Email é obrigatório")
	private String email;

	@NotBlank(message = "A senha é obrigatório")
	private String senha;

	private LocalDate dataCadastro;
	
	private List<Long> favoritos = new ArrayList<>();
    
	private String role;
 
	private static final String USUARIO = "USUARIO";
	
	
	
	public User(String nome, String sobrenome, String email, String senha, List<Long> favoritos) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
		this.setFavoritos(favoritos);
		setDataCadastro(LocalDate.now());
		setRole(USUARIO);
	}

	public User(String nome, String sobrenome, String email, String senha, LocalDate dataCadastro, List<Long> favoritos) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
		this.dataCadastro = dataCadastro;
		this.setFavoritos(favoritos);
		setRole(USUARIO);
	}

	public User(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		setRole(USUARIO);
	}
	
	public User(Long id, String nome) {
		this.id = id;
		this.nome = nome;
		setRole(USUARIO);
	}
	
	public User() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
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

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(LocalDate dataAtual) {
		this.dataCadastro = dataAtual;
	}
	

	public List<Long> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Long> favoritos) {
		this.favoritos = favoritos;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
