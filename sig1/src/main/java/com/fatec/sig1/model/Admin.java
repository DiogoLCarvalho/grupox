package com.fatec.sig1.model;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")

public class Admin implements UserDetails{
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@NotBlank(message = "Nome é requerido")
		private String nome;
		
		@NotBlank(message = "Sobrenome é requerido")
		private String sobrenome;
			
		@NotBlank(message = "O Email é obrigatório")
		private String login;

		@JsonIgnore
		@NotBlank(message = "A senha é obrigatório")
		private String senha;
		
		private String role;
		
		public Admin(String nome, String sobrenome, String login, String senha) {
			this.nome = nome;
			this.sobrenome = sobrenome;
			this.login = login;
			this.senha = senha;
			setRole("ADMIN");
		}

		public Admin(String nome, String login, String senha) {
			this.nome = nome;
			this.login = login;
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

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {

			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		@JsonIgnore
		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return this.senha;
		}
		
		@JsonIgnore
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return this.login;
		}
		
		@JsonIgnore
		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@JsonIgnore
		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@JsonIgnore
		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@JsonIgnore
		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}

	}

