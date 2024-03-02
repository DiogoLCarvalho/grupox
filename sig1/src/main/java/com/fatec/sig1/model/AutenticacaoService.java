package com.fatec.sig1.model;

import com.fatec.sig1.model.MantemLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService{
	// Classe que o Spring security vai chamar automaticamente quando ele precisar fazer uma autenticação/login

	@Autowired
	MantemLoginRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repository.findByLogin(username);
	}

	
	
}
