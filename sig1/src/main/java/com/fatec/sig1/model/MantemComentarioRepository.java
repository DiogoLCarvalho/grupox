package com.fatec.sig1.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MantemComentarioRepository extends JpaRepository<Comentario, Long>{

	
	@Query(value = "SELECT * from COMENTARIO where COMENTARIO.ONG_ID = :ongId", nativeQuery = true)
	List<Comentario> consultaComentariosOng(long ongId);
	
	@Query(value = "SELECT * from COMENTARIO where COMENTARIO.USUARIO_ID = :userId", nativeQuery = true)
	List<Comentario> consultaComentariosUser(long userId);

	@Query(value = "SELECT count(avaliacao) as QtdAvaliacoes, Sum(avaliacao) as SomaAvaliacoes,avg(avaliacao) as MediaAvaliacoes, from COMENTARIO where COMENTARIO.ONG_ID = :ongId", nativeQuery = true)
	List<AvaliacoesDTO> consultaMediaAvaliacoes(long ongId);

}

