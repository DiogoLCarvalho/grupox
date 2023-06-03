package com.fatec.sig1.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")

public class Admin {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@NotBlank(message = "Nome é requerido")
		private String nome;
		
		@NotBlank(message = "Sobrenome é requerido")
		private String sobrenome;
			
		@NotBlank(message = "O Email é obrigatório")
		private String email;

		@JsonIgnore
		@NotBlank(message = "A senha é obrigatório")
		private String senha;
		private String role;
		
		public Admin(String nome, String sobrenome, String email, String senha) {
			this.nome = nome;
			this.sobrenome = sobrenome;
			this.email = email;
			this.senha = senha;
			setRole("ADMIN");
		}

		public Admin(String nome, String email, String senha) {
			this.nome = nome;
			this.email = email;
			this.senha = senha;
			setRole("ADMIN");
		}
		
		public Admin() {
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

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

	}

