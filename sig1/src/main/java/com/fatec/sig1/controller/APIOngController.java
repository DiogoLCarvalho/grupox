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

import com.fatec.sig1.model.Comentario;
import com.fatec.sig1.model.DadosAutenticacao;
import com.fatec.sig1.model.Endereco;
import com.fatec.sig1.model.Exclusao;
import com.fatec.sig1.services.MantemUser;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.OngDTO;
import com.fatec.sig1.model.User;
import com.fatec.sig1.model.UserDTO;
import com.fatec.sig1.security.DadosTokenJWT;
import com.fatec.sig1.security.TokenService;
import com.fatec.sig1.services.MantemComentario;
import com.fatec.sig1.services.MantemExclusao;
import com.fatec.sig1.services.MantemOng;

@RestController
@RequestMapping("/api/v1/ong")
public class APIOngController {

	@Autowired
	MantemOng mantemOng;
	
	@Autowired
	MantemComentario mantemComentario;

	Ong ong;
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
		
		var JwtToken = tokenService.gerarTokenOng((Ong) autentication.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJWT(JwtToken));
	}
	
	
	@CrossOrigin // desabilita o cors do spring security
	@PostMapping
	public ResponseEntity<Object> saveCliente(@RequestBody @Valid OngDTO ongDTO, BindingResult result) {
		ong = new Ong();

		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller validacao da entrada dados invalidos  %s" , result.getFieldError());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}

		if (ongDTO.getCnpj() != null &&  (mantemOng.consultaPorCnpj(ongDTO.getCnpj()).isPresent())) {
				logger.info(">>>>>> apicontroller consultaporcnpj CNPJ ja cadastrado");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado");				
		}
		
		
		if (ongDTO.getCep() != null) {
			Optional<Endereco> endereco = Optional.ofNullable(mantemOng.obtemEndereco(ongDTO.getCep()));

			logger.info(">>>>>> apicontroller obtem endereco =>  %s" , ongDTO.getCep());

			if (endereco.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CEP invalido");
			}
		}

		try {
			Optional<Ong> ongRetorno = mantemOng.save(ongDTO.retornaUmCliente());
			
			Ong ongFinal = new Ong(
					ongRetorno.get().getNome(),
					ongRetorno.get().getTelefone(),
					ongRetorno.get().getCep(),
					ongRetorno.get().getComplemento(),
					ongRetorno.get().getDescricao(),
					ongRetorno.get().getSegmento(),
					ongRetorno.get().getLogin(),
					ongRetorno.get().getSenha(),
					ongRetorno.get().getCnpj(),
					ongRetorno.get().getCnae(),
					ongRetorno.get().getContaCorrente(),
					ongRetorno.get().getAgencia(),
					ongRetorno.get().getBanco(),
					ongRetorno.get().getPix(),
					ongRetorno.get().getCpf(),
					ongRetorno.get().getRegiao()
					);
			
			var JwtToken = tokenService.gerarTokenOng(ongFinal);
			
			OngDTO ongDTOResposta = new OngDTO(
					ongRetorno.get().getNome(),
					ongRetorno.get().getTelefone(),
					ongRetorno.get().getCep(),
					ongRetorno.get().getComplemento(),
					ongRetorno.get().getDescricao(),
					ongRetorno.get().getSegmento(),
					ongRetorno.get().getLogin(),
					ongRetorno.get().getSenha(),
					ongRetorno.get().getCnpj(),
					ongRetorno.get().getCnae(),
					ongRetorno.get().getContaCorrente(),
					ongRetorno.get().getAgencia(),
					ongRetorno.get().getBanco(),
					ongRetorno.get().getPix(),
					ongRetorno.get().getCpf(),
					ongRetorno.get().getRegiao(),
					JwtToken,
					ongRetorno.get().getId()
					);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(ongDTOResposta.retornaUmClienteToken());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro não esperado");
		}

	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping
	public ResponseEntity<List<Ong>> consultaTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.consultaTodos());
	}

	@Autowired
	MantemExclusao mantemExclusao;
	
	@CrossOrigin // desabilita o cors do spring security
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePorId(@PathVariable(value = "id") Long id) {
		
		List<Comentario> comentarioOng = mantemComentario.consultaTodosOsComentariosOng(id);
		
		if (!(comentarioOng.isEmpty())) {
			mantemComentario.deleteAll(comentarioOng);			
		}
		
		Optional<Ong> ongConsultadaD = mantemOng.consultaPorId(id);
		
		if (ongConsultadaD.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para deletar ong");
		}
		
		mantemOng.delete(ongConsultadaD.get().getId());
		
		Optional <Exclusao> excluiID = mantemExclusao.consultaPorId((long) 1);
		
		if (excluiID.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id de exclusão não encontrada");
		}
		
		Optional<Exclusao> ongExclui = mantemExclusao.atualiza((long) 1, new Exclusao(excluiID.get().getOngExcluidas() + 1, excluiID.get().getUsuariosExcluidos()));
		logger.info(">>>>>> apicontroller mais um usuario foi excluido  %s" , ongExclui);
		
		
		return ResponseEntity.status(HttpStatus.OK).body("ONG excluida");
	}
	
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/{id}")
	public ResponseEntity<Object> consultaPorId(@PathVariable Long id) {
		logger.info(">>>>>> apicontroller consulta por id chamado");
		Optional<Ong> ongConsultadaC = mantemOng.consultaPorId(id);
		if (ongConsultadaC.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para consultar ong");
		}
		return ResponseEntity.status(HttpStatus.OK).body(ongConsultadaC.get());
	}
	
	
	@CrossOrigin // desabilita o cors do spring security
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualiza(@PathVariable long id, @RequestBody @Valid OngDTO ongDTO,
			BindingResult result) {

		logger.info(">>>>>> api atualiza informações da ong chamado");

		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller atualiza informações da ong chamado dados invalidos");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}

		Optional<Ong> c = mantemOng.consultaPorId(id);

		if (c.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para atualizar ong");
		}

		if (ongDTO.getCep() != null) {
			Optional<Endereco> e = Optional.ofNullable(mantemOng.obtemEndereco(ongDTO.getCep()));
			if (e.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CEP não localizado.");
			}
		}

		Optional<Ong> ongConsultadaA = mantemOng.atualiza(id, ongDTO.retornaUmCliente());

		if (!ongConsultadaA.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(ongConsultadaA.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao atualizar :(");
		}
		
		
	}

	// ----------------------------------------------------- PARA RELATÓRIO -----------------------------------------------------
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/buscaRegiao/{zona}")
	public ResponseEntity<Long> relatorioTotalPorRegiao(@PathVariable String zona) {
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.todasAsONGPorRegiao(zona));
	}

	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/todasAsOngs")
	public ResponseEntity<Long> relatorioTodasASONG() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.todasAsONGcadastradas());
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/buscaSegmento/{seg}")
	public ResponseEntity<Long> relatorioTotalPorSegmento(@PathVariable String seg) {
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.todasAsONGPorSegmento(seg));
	}
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/cadastramentoOng")
	public ResponseEntity<Integer> relatorioTodasAsOngCadastradasNoMes() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.todasAsONGCadastradasNoMes());
	}
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/cadastramentoOngMesPassado")
	public ResponseEntity<Integer> relatorioTodasAsOngCadastradasNoMesPassado() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.todasAsONGCadastradasNoMesPassado());
	}

	// ----------------------------------------------------- PARA FAVORITOS -----------------------------------------------------
	
	@Autowired
	MantemUser mantemUser;
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/favoritos/{id}")
	public ResponseEntity<Object> buscarFavoritos(@PathVariable(value = "id") Long id, @Valid UserDTO userDTO, BindingResult result) {
		
		Optional<User> user = mantemUser.consultaPorId(id);
		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado para favoritar ong");
		}
		List<Long> favoritosDoUsuario = user.get().getFavoritos();
		
		if (favoritosDoUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não tem favoritos cadastrados");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.ongsFavoritas(favoritosDoUsuario));
		
	}
	
	
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/ongFavoritadas")
	public ResponseEntity<List<Object>> listaFavoritos() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemOng.listaOngFavoritasPorUser());
	}
	
	
}
