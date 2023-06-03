package com.fatec.sig1.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.sig1.model.Comentario;
import com.fatec.sig1.model.Exclusao;
import com.fatec.sig1.model.User;
import com.fatec.sig1.model.UserDTO;
import com.fatec.sig1.services.MantemComentario;
import com.fatec.sig1.services.MantemExclusao;
import com.fatec.sig1.services.MantemUser;

@RestController
@RequestMapping("/api/v1/user")

public class APIUserController {

	@Autowired
	MantemUser mantemUser;

	User user;
	Logger logger = LogManager.getLogger(this.getClass());

	@CrossOrigin // desabilita o cors do spring security
	@PostMapping
	public ResponseEntity<Object> saveCliente(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
		user = new User();

		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller validacao da entrada dados invalidos  %s", result.getFieldError());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}

		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(mantemUser.save(userDTO.retornaUmCliente()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro não esperado");
		}
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping
	public ResponseEntity<List<User>> consultaTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemUser.consultaTodos());
	}

	@Autowired
	MantemExclusao mantemExclusao;

	@CrossOrigin // desabilita o cors do spring security
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePorId(@PathVariable(value = "id") Long id) {
		Optional<User> userConsultaD = mantemUser.consultaPorId(id);

		if (userConsultaD.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para deletar usuario");
		}

		mantemUser.delete(userConsultaD.get().getId());
		Optional<Exclusao> excluiID = mantemExclusao.consultaPorId((long) 1);

		if (excluiID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id de exclusão não encontrada");
		}

		Optional<Exclusao> userExclui = mantemExclusao.atualiza((long) 1,
				new Exclusao(excluiID.get().getOngExcluidas(), excluiID.get().getUsuariosExcluidos() + 1));
		logger.info(">>>>>> apicontroller mais um usuario foi excluido  %s", userExclui);

		return ResponseEntity.status(HttpStatus.OK).body("Usuário excluido");
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/{id}")
	public ResponseEntity<Object> consultaPorId(@PathVariable Long id) {
		logger.info(">>>>>> apicontroller consulta por id chamado");
		Optional<User> userConsultaC = mantemUser.consultaPorId(id);
		if (userConsultaC.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para consultar usuario");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userConsultaC.get());
	}

	@CrossOrigin // desabilita o cors do spring security
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualiza(@PathVariable long id, @RequestBody @Valid UserDTO userDTO,
			BindingResult result) {

		logger.info(">>>>>> api atualiza informações da ong chamado");

		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller atualiza informações da ong chamado dados invalidos");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}

		Optional<User> c = mantemUser.consultaPorId(id);

		if (c.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para atualizar usuario");
		}

		Optional<User> userConsultaA = mantemUser.atualiza(id, userDTO.retornaUmCliente());

		if (userConsultaA.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario não foi encontrado em atualizar");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(userConsultaA.get());
		}

	}

	// ----------------------------------------------------- PARA RELATÓRIO
	// -----------------------------------------------------

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/todosUsuarios")
	public ResponseEntity<Long> relatorioTodosOsUsuarios() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemUser.todosOsUsuarioCadastrados());
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/cadastramentoOng")
	public ResponseEntity<Integer> relatorioTodasAsOngCadastradasNoMes() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemUser.todasAsONGCadastradasNoMes());
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/cadastramentoOngMesPassado")
	public ResponseEntity<Integer> relatorioTodasAsOngCadastradasNoMesPassado() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemUser.todasAsONGCadastradasNoMesPassado());
	}

	// ----------------------------------------------------- EXCLUIR COMENTARIO
	// -----------------------------------------------------

	@Autowired
	MantemComentario mantemComentario;

	@CrossOrigin
	@DeleteMapping("deletaComentario/{id}")
	public ResponseEntity<Object> deletaComentario(@PathVariable(value = "id") Long id) {
		Optional<Comentario> comentarioConsultado = mantemComentario.consultaPorId(id);

		if (comentarioConsultado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para o comentario");
		}

		mantemComentario.delete(comentarioConsultado.get().getId());

		return ResponseEntity.status(HttpStatus.OK).body("Comentario Excluido");
	}
}
