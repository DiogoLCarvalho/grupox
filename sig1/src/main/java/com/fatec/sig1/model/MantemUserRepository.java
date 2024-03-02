package com.fatec.sig1.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface MantemUserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByLogin(String login);

	List<User> findAllByNomeIgnoreCaseContaining(String nome);

	Optional<User> findBySobrenome(String sobrenome);

	Optional<User> findBySenha(String senha);

	// ----------------------------------------------------- PARA O RELATÓRIO
	// -----------------------------------------------------
	long count();
	
	@Query(value = "SELECT count(*) FROM USUARIO where data_cadastro like ?1%", nativeQuery = true)
	public int getCadastroMes(String month);

}
