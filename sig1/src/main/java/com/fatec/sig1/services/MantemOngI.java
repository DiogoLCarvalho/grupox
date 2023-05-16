package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.User;
import com.fatec.sig1.model.MantemOngRepository;
import com.fatec.sig1.model.Cnae;
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

	public Optional<Ong> consultaPorEmail(String email) {

		logger.info(">>>>>> servico constultaPorEmail chamado");

		return repository.findByEmail(email);

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
		logger.info(">>>>>> 1.servico atualiza informações de cliente chamado");
		Optional<Ong> ongM = this.repository.findById(id);
		
		Ong ongM2;
		
		if (!(ongM.isEmpty())) {
			ongM2 = ongM.get();
		}else {
			return Optional.empty();
		}
		
		ong.setId(id);
		
		logger.info(">>>>>> 2. servico atualiza informacoes de medicamento valido para o id => %s" , ongM2.getId());
		return Optional.ofNullable(repository.save(ong));
	}
	

	public Cnae obtemCnae(String cnae) {
		RestTemplate template = new RestTemplate();

		String url = "https://servicodados.ibge.gov.br/api/v2/cnae/classes/{cnae}";
		logger.info("Consultar CNAE:  %s", cnae);
		ResponseEntity<Cnae> resposta = null;

		try {
			resposta = template.getForEntity(url, Cnae.class, cnae);
			return resposta.getBody();
		} catch (ResourceAccessException e) {
			logger.info(">>>>>> consulta CNAE erro nao esperado ");
			return null;
		} catch (HttpClientErrorException e) {
			logger.info(">>>>>> consulta CNAE inválido erro HttpClientErrorException =>  %s", e.getMessage());
			return null;
		}
	}

	public Endereco obtemEndereco(String cep) {

		RestTemplate template = new RestTemplate();

		String url = "https://viacep.com.br/ws/{cep}/json/";

		logger.info(">>>>>> servico consultaCep -  %s", cep);

		ResponseEntity<Endereco> resposta = null;

		try {

			resposta = template.getForEntity(url, Endereco.class, cep);

			return resposta.getBody();

		} catch (ResourceAccessException e) {

			logger.info(">>>>>> consulta CEP erro nao esperado ");

			return null;

		} catch (HttpClientErrorException e) {

			logger.info(">>>>>> consulta CEP inválido erro HttpClientErrorException => %s", e.getMessage());

			return null;

		}

	}

	@Override
	public Optional<Ong> findByEmail(String email) {
		logger.info(">>>>>> servico consulta Email chamado");

		return repository.findByEmail(email);
	}

	@Override
	public Optional<Ong> findBySenha(String senha) {
		logger.info(">>>>>> servico consulta Senha chamado");

		return repository.findBySenha(senha);
	}

}
