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
import com.fatec.sig1.model.User;
import com.fatec.sig1.model.UserDTO;
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
			logger.info(">>>>>> apicontroller validacao da entrada dados invalidos %s" , result.getFieldError());
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

	@CrossOrigin // desabilita o cors do spring security
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePorId(@PathVariable(value = "id") Long id) {
		Optional<User> userDeletePorId = mantemUser.consultaPorId(id);
		if (userDeletePorId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID de usuario não encontrado para exclusão");
		}
		mantemUser.delete(userDeletePorId.get().getId());
		return ResponseEntity.status(HttpStatus.OK).body("ONG excluida");
	}
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/{id}")
	public ResponseEntity<Object> consultaPorId(@PathVariable Long id) {
		logger.info(">>>>>> apicontroller consulta por id chamado");
		Optional<User> userConsultaPorId = mantemUser.consultaPorId(id);
		if (userConsultaPorId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID de usuario não encontrado para consulta");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userConsultaPorId.get());
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
		}

		Optional<User> userAtualizado = mantemUser.atualiza(id, userDTO.retornaUmCliente());

		if (!userAtualizado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(userAtualizado.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao atualizar :(");
		}
	
	}


}
