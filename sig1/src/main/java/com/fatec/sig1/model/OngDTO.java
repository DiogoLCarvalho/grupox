package com.fatec.sig1.model;
import javax.validation.constraints.NotBlank;



import org.hibernate.validator.constraints.br.CNPJ;

public class OngDTO {
	
	@NotBlank(message = "Nome é requerido")
	private String nome;
	
	@NotBlank(message = "Insira apenas números")
	private long telefone;
	
	@CNPJ	
	private String cnpj;
	
	@NotBlank(message = "O CNAE é obrigatorio")
    private String cnae;
    
	@NotBlank(message = "O CEP é obrigatório.")
	private String cep;
	
	private String complemento;
	private String descricao;
	private String segmento;

	@NotBlank(message = "O Email é obrigatório")
	private String email;

	@NotBlank(message = "A senha é obrigatório")
	private String senha;
	
	@NotBlank(message = "A senha é obrigatório")
	private String regiao;
	
	private String contaCorrente;
	private String agencia;
	private String pix;

	public OngDTO(String nome, long telefone, String cep, String complemento, 
			String descricao, String segmento, String email, String senha, String cnpj, 
			String cnae, String contaCorrente, String agencia, String pix, String regiao) {
		this.nome = nome;
		this.telefone = telefone;
		this.cnpj = cnpj;
		this.cnae = cnae;
		this.cep = cep;
		this.complemento = complemento;
		this.descricao = descricao;
		this.segmento = segmento;
		this.email = email;
		this.senha = senha;
		this.contaCorrente = contaCorrente;
		this.agencia = agencia;
		this.pix = pix;
		this.regiao = regiao;
	}
	
	public OngDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getCnae() {
		return cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public long getTelefone() {
		return telefone;
	}

	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getPix() {
		return pix;
	}

	public void setPix(String pix) {
		this.pix = pix;
	}
	
	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public Ong retornaUmCliente() {
		return new Ong(nome, telefone, cep, complemento, descricao, segmento, email, senha, cnpj, cnae, contaCorrente, agencia, pix,regiao);
	}
}
