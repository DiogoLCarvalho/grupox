package com.fatec.sig1.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.MantemOngRepository;
import com.fatec.sig1.model.Endereco;


/**
 * 
 * A classe mantem cliente implementa o padrao Service. Servce eh um padrao que
 * basicamente encapsula
 * 
 * o processo de obtencao de serviços(objetos). O Service cria uma camada de
 * abstracao neste
 * 
 * processo. Ao inves da classe dependente instanciar suas dependencias
 * diretamente, eles são
 * 
 * solicitados a partir de um objeto centralizado que atua como localizador de
 * serviços.
 * 
 * @author
 *
 * 
 * 
 */

@Service

public class MantemOngI implements MantemOng {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	MantemOngRepository repository;

	public List<Ong> consultaTodos() {

		logger.info(">>>>>> servico consultaTodos chamado");

		return repository.findAll();
	}

	// ----------------------------------------------------- PARA FAVORITOS -----------------------------------------------------
	
	@Override
	public List<Ong> ongsFavoritas(List<Long> favoritos) {
		logger.info(">>>>>> servico buscandos todas as ONG Favoritas");

		return repository.getOngFavoritos(favoritos);
	}
	
	@Override
	public List<Object>  listaOngFavoritasPorUser() {
		logger.info(">>>>>> servico listando as ongs favoritadas");
		
		return repository.listaOngFavoritasPorUser();
	}
	
	// ----------------------------------------------------- PARA FAVORITOS -----------------------------------------------------
	
	
	// ----------------------------------------------------- PARA RELATÓRIO -----------------------------------------------------
	public Long todasAsONGPorRegiao(String regiao) {
		logger.info(">>>>>> Pesquisando todas as ongs por regiao");
		return repository.countByRegiao(regiao);
	}

	public Long todasAsONGcadastradas() {
		logger.info(">>>>>> Pesquisando todas as ongs");
		return repository.count();
	}

	public Long todasAsONGPorSegmento(String segmento) {
		logger.info(">>>>>> Pesquisando todas as ongs por segmento");
		return repository.countBySegmento(segmento);
	}
	
	public int todasAsONGCadastradasNoMes() {
		int mesAtual = LocalDate.now().getMonth().getValue();
		int anoAtual = LocalDate.now().getYear();
		String anoAtualFormatado = Integer.toString(anoAtual);
		String month;
		
		if(mesAtual <= 9) {
			month = anoAtualFormatado + "-" + String.format("%02d", mesAtual);			
		}else {
			month = anoAtualFormatado + "-" + Integer.toString(mesAtual);	
		}
		
		return repository.getCadastroMes(month);
	}
	
	public int todasAsONGCadastradasNoMesPassado() {
		int mesAtual = LocalDate.now().getMonth().getValue() -1 ;
		int anoAtual = LocalDate.now().getYear();
		String anoAtualFormatado = Integer.toString(anoAtual);
		String month;
		
		if(mesAtual <= 9) {
			month = anoAtualFormatado + "-" + String.format("%02d", mesAtual);			
		}else {
			month = anoAtualFormatado + "-" + Integer.toString(mesAtual);	
		}
		
		return repository.getCadastroMes(month);
	}
	
	// ----------------------------------------------------- PARA RELATÓRIO -----------------------------------------------------

	@Override

	public Optional<Ong> consultaPorCnpj(String cnpj) {

		logger.info(">>>>>> servico consultaPorCnpj chamado");

		return repository.findByCnpj(cnpj);

	}

	@Override

	public Optional<Ong> consultaPorId(Long id) {

		logger.info(">>>>>> servico consultaPorId chamado");

		return repository.findById(id);

	}
	
	@Override

	public Optional<Ong> save(Ong ong) {

		logger.info(">>>>>> servico save chamado ");

		if (ong.getCep() != null) {
			Endereco endereco = obtemEndereco(ong.getCep());

			ong.setEndereco(endereco.getLogradouro());
		}

		return Optional.ofNullable(repository.save(ong));

	}

	@Override

	public void delete(Long id) {

		logger.info(">>>>>> servico delete por id chamado");

		repository.deleteById(id);

	}
	
	@Override

	public Optional<Ong> atualiza(Long id, Ong ong) {

		logger.info(">>>>>> 1.servico atualiza informações da ong chamado");
		Endereco endereco = obtemEndereco(ong.getCep());
		
		Ong ongModificado = new Ong(ong.getNome(), ong.getTelefone(), ong.getCep(), ong.getComplemento(),
				ong.getDescricao(), ong.getSegmento(), ong.getLogin(), ong.getSenha(), ong.getCnpj(), ong.getCnae(), 
				ong.getContaCorrente(),ong.getAgencia(),ong.getBanco() , ong.getPix(), ong.getCpf(), ong.getRegiao(), ong.getDataCadastro());

		Optional<Ong> ongGetIdConsulta = this.repository.findById(id);
		Ong ongGetId;
		
		if (!(ongGetIdConsulta.isEmpty())) {
			ongGetId = ongGetIdConsulta.get();
		}else {
			return Optional.empty();
		}
		
		ongModificado.setId(id);
		
		if (ongModificado.getCep() == null) {
			ongModificado.setEndereco(ongGetId.getEndereco());
		}else {			
			ongModificado.setEndereco(endereco.getLogradouro());
		}
		
		logger.info(
				">>>>>> 2. servico atualiza informacoes da ong cep valido para o id => %s" , ongModificado.getId());
	
		LocalDate dataAtual = LocalDate.now();
		
		if (ongModificado.getDataCadastro().isEqual(dataAtual)) {
			ongModificado.setDataCadastro(ongGetId.getDataCadastro());
		}
		
		return Optional.ofNullable(repository.save(ongModificado));

	}


	public Endereco obtemEndereco(String cep) {

		RestTemplate template = new RestTemplate();

		String url = "https://viacep.com.br/ws/{cep}/json/";

		logger.info(">>>>>> servico consultaCep -  %s" , cep);

		ResponseEntity<Endereco> resposta = null;

		try {

			resposta = template.getForEntity(url, Endereco.class, cep);

			return resposta.getBody();

		} catch (ResourceAccessException e) {

			logger.info(">>>>>> consulta CEP erro nao esperado ");

			return null;

		} catch (HttpClientErrorException e) {

			logger.info(">>>>>> consulta CEP inválido erro HttpClientErrorException => %s" , e.getMessage());

			return null;

		}

	}


	@Override
	public Optional<Ong> findBySenha(String senha) {
		logger.info(">>>>>> servico consulta Senha chamado");

		return repository.findBySenha(senha);
	}





}
