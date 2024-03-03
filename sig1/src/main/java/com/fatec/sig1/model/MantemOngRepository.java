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
public interface MantemOngRepository extends JpaRepository<Ong, Long> {
	
	UserDetails findByLogin(String login);
    
    Optional<Ong> findByCnpj(String cnpj);

    List<Ong> findAllByNomeIgnoreCaseContaining(String nome);

    Optional<Ong> findBySegmento(String segmento);
    
    Optional<Ong> findBySenha(String senha);

    // ----------------------------------------------------- PARA RELATÓRIO -----------------------------------------------------

    Long countByRegiao(String regiao);

    long count();
 
    Long countBySegmento(String segmento);
    
    
	@Query(value = "SELECT count(*) FROM ONG where data_cadastro like ?1%", nativeQuery = true)
	public int getCadastroMes(String month);
    
    // ----------------------------------------------------- PARA Favoritos  -----------------------------------------------------
    
	@Query(value = "select * from ONG WHERE id IN (:numbers);", nativeQuery = true)
	public List<Ong> getOngFavoritos(List<Long> numbers);
	
	@Query(value = "SELECT ONG.NOME, (SELECT count(*) FROM usuario where ARRAY_CONTAINS(USUARIO.FAVORITOS, ONG.ID) ) ONGS_FAVORITAS FROM ONG INNER JOIN USUARIO ON ARRAY_CONTAINS(USUARIO.FAVORITOS, ONG.ID) GROUP BY ONG.NOME ORDER BY ONGS_FAVORITAS DESC;", nativeQuery = true)
	public List<Object> listaOngFavoritasPorUser();
	
}
