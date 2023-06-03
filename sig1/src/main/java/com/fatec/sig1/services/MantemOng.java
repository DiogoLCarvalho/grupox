package com.fatec.sig1.services;

import java.util.List;
import java.util.Optional;
import com.fatec.sig1.model.Ong;
import com.fatec.sig1.model.Endereco;


public interface MantemOng {

	List<Ong> consultaTodos();
	
	// ----------------------------------------------------- PARA FAVORITOS -----------------------------------------------------
	List<Ong> ongsFavoritas(List<Long> favoritos);
	
	List<Object> listaOngFavoritasPorUser();
	// ----------------------------------------------------- PARA FAVORITOS-----------------------------------------------------

	// ----------------------------------------------------- PARA RELATÓRIO -----------------------------------------------------
	Long todasAsONGPorRegiao(String regiao);

	Long todasAsONGcadastradas();

	Long todasAsONGPorSegmento(String segmento);
	
	int todasAsONGCadastradasNoMes();
	
	int todasAsONGCadastradasNoMesPassado();
	// ----------------------------------------------------- PARA RELATÓRIO -----------------------------------------------------

	Optional<Ong> consultaPorCnpj(String cnpj);

	Optional<Ong> consultaPorId(Long id);
	
	Optional<Ong> consultaPorEmail(String email);

	Optional<Ong> save(Ong ong);

	void delete(Long id);

	Optional<Ong> atualiza(Long id, Ong ong);

	Endereco obtemEndereco(String cep);
	
    Optional<Ong> findByEmail(String email);
    
    Optional<Ong> findBySenha(String senha);
}
