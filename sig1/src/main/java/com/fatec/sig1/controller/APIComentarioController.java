package com.fatec.sig1.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.annotation.JsonView;
import com.fatec.sig1.model.AvaliacoesDTO;
import com.fatec.sig1.model.Comentario;
import com.fatec.sig1.model.ComentarioDTO;
import com.fatec.sig1.model.User;
import com.fatec.sig1.model.Views;
import com.fatec.sig1.services.MantemComentario;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/comentario")
public class APIComentarioController {
	
	@Autowired
	MantemComentario mantemComentario;
	
	@JsonView(Views.Public.class)
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Comentario> comentar(@RequestBody ComentarioDTO comentarioDTO, HttpServletRequest request){
		
		final String authorizationHeaderValue = request.getHeader("Authorization");

		final String token = authorizationHeaderValue.replace("Bearer ", "");

		if(!(JWT.decode(token).getClaim("role").toString().equalsIgnoreCase("\"USUARIO\"")) ) {
			Comentario ListaVazia = null;
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ListaVazia);			
		}
		
		
		Comentario comentario = mantemComentario.save(comentarioDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(comentario); 
	}
	
	@JsonView(Views.Public.class)
	@CrossOrigin
	@GetMapping("/todosComentarioOng/{ongId}")
	public ResponseEntity<List<Comentario>> todosComentariosPorOng(@PathVariable Long ongId, HttpServletRequest request){
		
		final String authorizationHeaderValue = request.getHeader("Authorization");

		final String token = authorizationHeaderValue.replace("Bearer ", "");

		if(!(JWT.decode(token).getClaim("role").toString().equalsIgnoreCase("\"ADMIN\"")) ) {
			List<Comentario> ListaVazia = null;
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ListaVazia);			
		}
		
		
		
		List<Comentario> comentarios = mantemComentario.consultaTodosOsComentariosOng(ongId);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios); 
	}
	
	@JsonView(Views.Public.class)
	@CrossOrigin
	@GetMapping("/todosComentarioUser/{userId}")
	public ResponseEntity<List<Comentario>> todosComentariosPorUsuario(@PathVariable Long userId, HttpServletRequest request){
		
		final String authorizationHeaderValue = request.getHeader("Authorization");

		final String token = authorizationHeaderValue.replace("Bearer ", "");

		if(!(JWT.decode(token).getClaim("role").toString().equalsIgnoreCase("\"ADMIN\"")) ) {
			List<Comentario> ListaVazia = null;
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ListaVazia);			
		}
		
		List<Comentario> comentarios = mantemComentario.consultaTodosOsComentariosUser(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios); 
	}
	
	@JsonView(Views.Public.class)
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Comentario>> todosComentarios(HttpServletRequest request){
		
		final String authorizationHeaderValue = request.getHeader("Authorization");

		final String token = authorizationHeaderValue.replace("Bearer ", "");

		if(!(JWT.decode(token).getClaim("role").toString().equalsIgnoreCase("\"ADMIN\"")) ) {
			List<Comentario> ListaVazia = null;
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ListaVazia);			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(mantemComentario.consultaTodos());
	}
	
	
	@CrossOrigin
	@GetMapping("/avaliacoes/{ongId}")
	public ResponseEntity<List<AvaliacoesDTO>> mediaAvaliacoes(@PathVariable Long ongId, HttpServletRequest request){
		
		final String authorizationHeaderValue = request.getHeader("Authorization");

		final String token = authorizationHeaderValue.replace("Bearer ", "");

		if(!(JWT.decode(token).getClaim("role").toString().equalsIgnoreCase("\"ADMIN\"")) ) {
			List<AvaliacoesDTO> ListaVazia = null;
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ListaVazia);			
		}
		
		
		List<AvaliacoesDTO> comentarios = mantemComentario.consultaMediaAvaliacoes(ongId);
		return ResponseEntity.status(HttpStatus.OK).body(comentarios); 
	}
	
	@JsonView(Views.Public.class)
	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualiza(@PathVariable long id, @RequestBody @Valid ComentarioDTO comentarioDTO, BindingResult result, HttpServletRequest request){
		LocalDateTime horaAtual = LocalDateTime.now();
		
		
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}
		
		Optional<Comentario> c = mantemComentario.consultaPorId(id);
		
		if (c.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para atualizar comentario");
		}
		
		final String authorizationHeaderValue = request.getHeader("Authorization");

		final String token = authorizationHeaderValue.replace("Bearer ", "");

		if(!(JWT.decode(token).getClaim("role").toString().equalsIgnoreCase("\"USUARIO\"")) ) {
			List<AvaliacoesDTO> ListaVazia = null;
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ListaVazia);			
		}
		
		
		LocalDateTime dataCadastro = c.get().getDataCadastro();
		LocalDateTime limite = dataCadastro.plusDays(1);
		
		if(horaAtual.isBefore(limite)) {
			Optional<Comentario> comentarioConsultadoA = mantemComentario.atualiza(id, comentarioDTO, c.get());
			
			if (comentarioConsultadoA.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao atualizar o comentario :(");
			}
			
			
			return ResponseEntity.status(HttpStatus.OK).body(comentarioConsultadoA);		
			
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não pode atualizar o comentário, já se passaram 24 horas. ");
		}
		
	}

	
}
