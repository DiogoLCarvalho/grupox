package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;
import com.fatec.sig1.model.Admin;

public interface MantemAdmin {

	List<Admin> consultaTodos();

	Optional<Admin> consultaPorId(Long id);
	
	Optional<Admin> save(Admin admin);

	void delete(Long id);
    
    Optional<Admin> findBySenha(String senha);

	Optional<Admin> atualiza(Long id, Admin admin);
    
}
