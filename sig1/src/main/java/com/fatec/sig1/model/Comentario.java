package com.fatec.sig1.model;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Long id;
	
	@ManyToOne
	@JsonView(Views.Public.class)
	private Ong ong;
	
	@ManyToOne
	@JsonView(Views.Public.class)
	private User usuario;
	
	@JsonView(Views.Public.class)
	@Column(columnDefinition = "Longtext")
	private String textoComentario;
	
	@JsonView(Views.Public.class)
	private int avaliacao;
	
	@JsonIgnore
	private LocalDateTime dataCadastro;
	
	public Comentario(Ong ong, User usuario, String textoComentario, int avaliacao) {
		this.ong = ong;
		this.usuario = usuario;
		this.textoComentario = textoComentario;
		this.avaliacao = avaliacao;
		setDataCadastro(LocalDateTime.now());
	}
	
	public Comentario(Ong ong, User usuario, String textoComentario, int avaliacao, LocalDateTime dataCadastro) {
		this.ong = ong;
		this.usuario = usuario;
		this.textoComentario = textoComentario;
		this.avaliacao = avaliacao;
		this.dataCadastro = dataCadastro;
	}
	
	
	public Comentario(Long id, String textoComentario, int avaliacao) {
		this.id = id;
		this.textoComentario = textoComentario;
		this.avaliacao = avaliacao;
	}
	

	public Comentario() {
		//Construtor vazio
	}
	
	public Ong getOng() {
		return ong;
	}

	public void setOng(Ong ong) {
		this.ong = ong;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	

	public String getTextoComentario() {
		return textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
}
