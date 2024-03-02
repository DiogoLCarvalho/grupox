package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;
import com.fatec.sig1.model.User;

public interface MantemUser {

	List<User> consultaTodos();
	
	Optional<User> consultaPorId(Long id);
	
	Optional<User> consultaPorEmail(String email);

	Optional<User> save(User user);

	void delete(Long id);

	Optional<User> atualiza(Long id, User user);
	
    
    Optional<User> findBySenha(String senha);

	// ----------------------------------------------------- PARA RELATÓRIO -----------------------------------------------------

	Long todosOsUsuarioCadastrados();
	
	int todasAsONGCadastradasNoMes();
	
	int todasAsONGCadastradasNoMesPassado();


}
