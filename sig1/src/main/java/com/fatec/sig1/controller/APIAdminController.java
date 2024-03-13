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

import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.User;
import com.fatec.sig1.security.DadosTokenJWT;
import com.fatec.sig1.security.TokenService;
import com.fatec.sig1.model.Admin;
import com.fatec.sig1.model.AdminDTO;
import com.fatec.sig1.model.Comentario;
import com.fatec.sig1.model.DadosAutenticacao;
import com.fatec.sig1.model.Exclusao;
import com.fatec.sig1.services.MantemAdmin;
import com.fatec.sig1.services.MantemComentario;
import com.fatec.sig1.services.MantemExclusao;
import com.fatec.sig1.services.MantemOng;
import com.fatec.sig1.services.MantemUser;


@RestController
@RequestMapping("/api/v1/admin")
public class APIAdminController {
	
	@Autowired
	MantemAdmin mantemAdmin;

	Admin admin;
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
		
		var JwtToken = tokenService.gerarTokenAdmin((Admin) autentication.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJWT(JwtToken));
	}

		
	@CrossOrigin // desabilita o cors do spring security
	@PostMapping("/{id}")
	public ResponseEntity<Object> saveCliente(@PathVariable(value = "id") Long id, @RequestBody @Valid AdminDTO adminDTO, BindingResult result) {
		admin = new Admin();
		
		if(id == 1) {
			if (result.hasErrors()) {
				logger.info(">>>>>> apicontroller validacao da entrada dados invalidos  %s" , result.getFieldError());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
			}
			
			try {
				
				Optional<Admin> adminRetorno = mantemAdmin.save(adminDTO.retornaUmCliente());
				
				Admin adminFinal = new Admin(
						adminRetorno.get().getNome(),
						adminRetorno.get().getSobrenome(),
						adminRetorno.get().getLogin(),
						adminRetorno.get().getSenha()
						);
				
				var JwtToken = tokenService.gerarTokenAdmin(adminFinal);
				
				AdminDTO adminDTOResposta = new AdminDTO(
						adminRetorno.get().getNome(),
						adminRetorno.get().getSobrenome(),
						adminRetorno.get().getLogin(),
						adminRetorno.get().getSenha(),
						JwtToken
						);
				
				return ResponseEntity.status(HttpStatus.CREATED).body(adminDTOResposta.retornaUmClienteToken());
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro não esperado");
			}			
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Apenas administradores master podem criar outros adminstradores");
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping
	public ResponseEntity<List<Admin>> consultaTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemAdmin.consultaTodos());
	}

	@CrossOrigin // desabilita o cors do spring security
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePorId(@PathVariable(value = "id") Long id) {
		Optional<Admin> adminConsultadoD = mantemAdmin.consultaPorId(id);
		if (adminConsultadoD.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para deletar admin");
		}
		mantemAdmin.delete(adminConsultadoD.get().getId());
		return ResponseEntity.status(HttpStatus.OK).body("Administrador excluido");
	}
	 
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/{id}")
	public ResponseEntity<Object> consultaPorId(@PathVariable Long id) {
		logger.info(">>>>>> apicontroller consulta por id chamado");
		Optional<Admin> adminConsultadoC = mantemAdmin.consultaPorId(id);
		if (adminConsultadoC.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para consultar");
		}
		return ResponseEntity.status(HttpStatus.OK).body(adminConsultadoC.get());
	}
	
	
	@CrossOrigin // desabilita o cors do spring security
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualiza(@PathVariable long id, @RequestBody @Valid AdminDTO adminDTO,
			BindingResult result) {

		logger.info(">>>>>> api atualiza informações da ong chamado");

		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller atualiza informações do ADM chamado dados invalidos");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}

		Optional<Admin> c = mantemAdmin.consultaPorId(id);

		if (c.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para atualizar");
		}
		
		Optional<Admin> adminAtualizado;
		if(adminDTO.getSenha() == null) {
			adminAtualizado = mantemAdmin.atualiza(id, adminDTO.retornaUmClientePUT());
		}else {
			adminAtualizado = mantemAdmin.atualiza(id, adminDTO.retornaUmCliente());
		}

		if (adminAtualizado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador não encontrado");
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(adminAtualizado.get());			
		}
		
	}

	@Autowired
	MantemExclusao mantemExclusao;
	
	@Autowired
	MantemUser mantemUser;
	
	@CrossOrigin // desabilita o cors do spring security
	@DeleteMapping("deletarUsuario/{id}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "id") Long id) {
		
		List<Comentario> comentarioUsuario = mantemComentario.consultaTodosOsComentariosUser(id);
		
		if (!(comentarioUsuario.isEmpty())) {
			mantemComentario.deleteAll(comentarioUsuario);			
		}
		
		Optional<User> user = mantemUser.consultaPorId(id);

		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para deletar usuario pelo admin");
		}
		mantemUser.delete(user.get().getId());
		
		Optional <Exclusao> excluiID = mantemExclusao.consultaPorId((long) 1);
		
		if (excluiID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id de exclusão não encontrada");
		}
		
		Optional<Exclusao> userExclui = mantemExclusao.atualiza((long) 1, new Exclusao(excluiID.get().getOngExcluidas(), excluiID.get().getUsuariosExcluidos() + 1));
		logger.info(">>>>>> apicontroller mais um usuario foi excluido  %s" , userExclui);
		
		
		return ResponseEntity.status(HttpStatus.OK).body("Usuário excluido");
	}
	
	@Autowired
	MantemOng mantemOng;
	
	@CrossOrigin // desabilita o cors do spring security
	@DeleteMapping("deletarOng/{id}")
	public ResponseEntity<Object> deleteONG(@PathVariable(value = "id") Long id) {
		
		List<Comentario> comentarioOng = mantemComentario.consultaTodosOsComentariosOng(id);
		
		if (!(comentarioOng.isEmpty())) {
			mantemComentario.deleteAll(comentarioOng);			
		}
			
		Optional<Ong> ong = mantemOng.consultaPorId(id);
		
		if (ong.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para deletar ong pelo admin");
		}
		mantemOng.delete(ong.get().getId());
		
		Optional <Exclusao> excluiID = mantemExclusao.consultaPorId((long) 1);
		
		if (excluiID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id de exclusão não encontrada");
		}
		
		Optional<Exclusao> ongExclui = mantemExclusao.atualiza((long) 1, new Exclusao(excluiID.get().getOngExcluidas() + 1, excluiID.get().getUsuariosExcluidos()));
		logger.info(">>>>>> apicontroller mais um usuario foi excluido  %s" , ongExclui);
		
		return ResponseEntity.status(HttpStatus.OK).body("ONG excluida");
	}
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/todasAsOngsExcluidas")
	public ResponseEntity<Integer> consultaTodasAsOngExcluidas() {
		Optional <Exclusao> excluiID = mantemExclusao.consultaPorId((long) 1);
		
		if (excluiID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(-1);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(excluiID.get().getOngExcluidas());
	}
	
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/todasAsUserExcluidas")
	public ResponseEntity<Integer> consultaTodasAsUsuariosExcluidas() {
		Optional <Exclusao> excluiID = mantemExclusao.consultaPorId((long) 1);
		
		if (excluiID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(-1);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(excluiID.get().getUsuariosExcluidos());
	}
	
	// ----------------------------------------------------- EXCLUIR COMENTARIO  -----------------------------------------------------
	
	@Autowired
	MantemComentario mantemComentario;
	
	@CrossOrigin
	@DeleteMapping("deletaComentario/{id}")
	public ResponseEntity<Object> deletaComentario(@PathVariable(value = "id") Long id){
		Optional<Comentario> comentarioConsultado = mantemComentario.consultaPorId(id);
		
		if(comentarioConsultado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para o comentario");
		}
		
		mantemComentario.delete(comentarioConsultado.get().getId());
	
		return ResponseEntity.status(HttpStatus.OK).body("Comentario Excluido");
	}
	
}
