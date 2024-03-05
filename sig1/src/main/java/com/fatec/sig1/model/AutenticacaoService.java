package com.fatec.sig1.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService{
	// Classe que o Spring security vai chamar automaticamente quando ele precisar fazer uma autenticação/login

	@Autowired
	MantemUserRepository repositoryUser;
	
	@Autowired
	MantemAdminRepository repositoryAdmin;
	
	@Autowired
	MantemOngRepository repositoryOng;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(repositoryUser.findByLogin(username) != null) {
			return repositoryUser.findByLogin(username);
		}else if(repositoryAdmin.findByLogin(username) != null) {
			return repositoryAdmin.findByLogin(username);
		}
		
		return repositoryOng.findByLogin(username);
	}	
	
}
