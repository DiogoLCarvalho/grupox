package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.sig1.model.AvaliacoesDTO;
import com.fatec.sig1.model.Comentario;
import com.fatec.sig1.model.ComentarioDTO;
import com.fatec.sig1.model.MantemComentarioRepository;
import com.fatec.sig1.model.MantemOngRepository;
import com.fatec.sig1.model.MantemUserRepository;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.User;

@Service
public class MantemComentarioI implements MantemComentario{
	
	@Autowired
	MantemComentarioRepository repository;
	
	@Autowired
	MantemOngRepository ongRepository;
	
	@Autowired
	MantemUserRepository userRepository;
	
	@Override
	public Comentario save(ComentarioDTO comentarioDTO) {
		
		Ong ong =  ongRepository.findById(comentarioDTO.getOngId()).get();
		
		User usuario = userRepository.findById(comentarioDTO.getUsuarioId()).get();
				
		Comentario comentario = new Comentario(ong, usuario, comentarioDTO.getTextoComentario(), comentarioDTO.getAvaliacao());
	
		return repository.save(comentario);
	}
	
	@Override
	public List<Comentario> consultaTodosOsComentariosOng(long ongId){
		
		return repository.consultaComentariosOng(ongId);
	}
	
	@Override
	public List<Comentario> consultaTodosOsComentariosUser(long userId){
		
		return repository.consultaComentariosUser(userId);
	}
	

	@Override
	public List<AvaliacoesDTO> consultaMediaAvaliacoes(long ongId) {
		
		return repository.consultaMediaAvaliacoes(ongId);
	}

	@Override
	public Optional<Comentario> consultaPorId(Long id) {
		
		return repository.findById(id);
	}

	@Override
	public void delete(Long id) {
		
		repository.deleteById(id);
	}

	@Override
	public List<Comentario> consultaTodos() {
		return repository.findAll();
	}

	@Override
	public Optional<Comentario> atualiza(Long id, ComentarioDTO comentarioDTO, Comentario comentario) {
		
		Comentario comentarioAtualizado = new Comentario(comentario.getOng(), comentario.getUsuario(), comentarioDTO.getTextoComentario(), comentario.getAvaliacao());
		comentarioAtualizado.setId(id);
	
		return Optional.ofNullable(repository.save(comentarioAtualizado));
	}
	
	
	
}
