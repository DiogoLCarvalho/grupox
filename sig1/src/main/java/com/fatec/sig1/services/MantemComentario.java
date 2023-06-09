package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;

import com.fatec.sig1.model.AvaliacoesDTO;
import com.fatec.sig1.model.Comentario;
import com.fatec.sig1.model.ComentarioDTO;

public interface MantemComentario {
	Comentario save(ComentarioDTO comentarioDTO);
	
	List<Comentario> consultaTodosOsComentariosOng(long ongId);
	
	List<Comentario> consultaTodosOsComentariosUser(long userId);
	
	List<AvaliacoesDTO> consultaMediaAvaliacoes(long ongId);
	
	Optional<Comentario> consultaPorId(Long id);
	
	void delete(Long id);
	
	void deleteAll(List<Comentario> comentarios);
	
	List<Comentario> consultaTodos();
	
	Optional<Comentario> atualiza(Long id, ComentarioDTO comentarioDTO, Comentario comentario);
	
	Optional<List<Comentario>> usuarioExcluido(Long id);
	
}
