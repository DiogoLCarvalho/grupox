package com.fatec.sig1.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.fatec.sig1.model.DadosAutenticacao;
import com.fatec.sig1.model.Exclusao;
import com.fatec.sig1.model.User;
import com.fatec.sig1.model.UserDTO;
import com.fatec.sig1.security.DadosTokenJWT;
import com.fatec.sig1.security.TokenService;
import com.fatec.sig1.services.MantemComentario;
import com.fatec.sig1.services.MantemExclusao;
import com.fatec.sig1.services.MantemUser;

@RestController
@RequestMapping("/api/v1/user")
public class APIUserController {

	@Autowired
	MantemUser mantemUser;

	@Autowired
	MantemExclusao mantemExclusao;
	
	@Autowired
	MantemComentario mantemComentario;
	
	User user;
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		
		// DTO do spring
		var AuthenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		
		// verifica os dados e armazena a resposta - Essa classe chama a findByLogin
		var autentication = manager.authenticate(AuthenticationToken);
		
		var JwtToken = tokenService.gerarTokenUser((User) autentication.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJWT(JwtToken));
	}
	

	@CrossOrigin // desabilita o cors do spring security
	@PostMapping
	public ResponseEntity<Object> saveCliente(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
		user = new User();

		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller validacao da entrada dados invalidos  %s", result.getFieldError());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}
		

		try {
			// Salva Cliente
			Optional<User> userRetorno = mantemUser.save(userDTO.retornaUmCliente());
			
			User userFinal = new User(userRetorno.get().getNome(), userRetorno.get().getSobrenome(),userRetorno.get().getLogin(), userRetorno.get().getSenha(), userRetorno.get().getFavoritos());

			var JwtToken = tokenService.gerarTokenUser(userFinal);
			
			UserDTO userDtoResposta = new UserDTO(
					userRetorno.get().getNome(), 
					userRetorno.get().getSobrenome(),
					userRetorno.get().getLogin(),
					userRetorno.get().getSenha(),
					userRetorno.get().getFavoritos(),
					JwtToken);
		
		
			return ResponseEntity.status(HttpStatus.CREATED).body(userDtoResposta.retornaUmClienteComToken());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro não esperado" + e);
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
		
		List<Comentario> comentarioUsuario = mantemComentario.consultaTodosOsComentariosUser(id);
		
		if (!(comentarioUsuario.isEmpty())) {
			mantemComentario.deleteAll(comentarioUsuario);			
		}
		
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
