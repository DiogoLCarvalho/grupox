package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.Cnae;
import com.fatec.sig1.model.Endereco;

public interface MantemOng {

	List<Ong> consultaTodos();

	Optional<Ong> consultaPorCnpj(String cnpj);

	Optional<Ong> consultaPorId(Long id);
	
	Optional<Ong> consultaPorEmail(String email);

	Optional<Ong> save(Ong ong);

	void delete(Long id);

	Optional<Ong> atualiza(Long id, Ong ong);

	Endereco obtemEndereco(String cep);
	
	Cnae obtemCnae(String cnae);
	
    Optional<Ong> findByEmail(String email);
    
    Optional<Ong> findBySenha(String senha);

	
}
