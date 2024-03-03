package com.fatec.sig1.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * Esta classe abstrai a programação de consultas para acesso ao banco de dados.
 * O nome dos metodos para consulta têm duas partes separadas pela palavra-chave
 * By. A primeira parte é o “find” seguido de By. A segunda parte é o nome do
 * atributo na tabela por exemplo Cpf - findByCpf
 * 
 * @author
 */


@Repository
public interface MantemAdminRepository extends JpaRepository<Admin, Long> {
	
	UserDetails findByLogin(String login);

	List<Admin> findAllByNomeIgnoreCaseContaining(String nome);

	Optional<Admin> findBySobrenome(String sobrenome);
	    
    Optional<Admin> findBySenha(String senha);

}
