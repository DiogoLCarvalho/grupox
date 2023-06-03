package com.fatec.sig1.model;

public class ComentarioDTO {
	private Long ongId;
	
	private Long usuarioId;
	
	private String textoComentario;
	
	private int avaliacao;
	
	public Long getOngId() {
		return ongId;
	}
	public void setOngId(Long ongId) {
		this.ongId = ongId;
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
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}
