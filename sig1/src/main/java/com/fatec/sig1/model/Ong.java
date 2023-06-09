package com.fatec.sig1.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

//The JPA was renamed as Jakarta Persistence in 2019 and version 3.0 was released in 2020. This included the renaming of packages and properties
//from javax. persistence to jakarta. persistence.
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ong {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Long id;
	
	@NotBlank(message = "Nome é requerido")
	@JsonView(Views.Public.class)
	private String nome;
	
	@NotBlank(message = "Insira apenas números")
	private long telefone;
	
	@CNPJ
	@NotBlank(message = "O CNPJ é obrigatório")
	private String cnpj;
	
	@NotBlank(message = "O CEP é obrigatório.")
	private String cep;
	
	private String endereco;
	
	@NotBlank(message = "O CNAE é obrigatório")
    private String cnae;
	
	@NotBlank(message = "O complemento deve ser informado")
	private String complemento;
	
	@Column(columnDefinition = "Longtext")
	private String descricao;
	private String segmento;
	
	@NotBlank(message = "O Email é obrigatório")
	private String email;

	@JsonIgnore
	@NotBlank(message = "A senha é obrigatório")
	private String senha;
	
	@NotBlank(message = "A região é obrigatório")
	private String regiao;
	
	private String contaCorrente;
	private String agencia;
	private String banco;
	private String pix;
	private String cpf;
	private LocalDate dataCadastro;
	private String role;
	
	
	public Ong(String nome, long telefone, String cep, String complemento, 
			String descricao, String segmento, String email, String senha, String cnpj, 
			String cnae, String contaCorrente, String agencia, String banco, String pix, String cpf, String regiao) {
		this.nome = nome;
		this.telefone = telefone;
		this.cep = cep;
		this.complemento = complemento;
		this.descricao = descricao;
		this.segmento = segmento;
		this.email = email;
		this.senha = senha;
		this.cnpj = cnpj;
		this.cnae = cnae;
		this.contaCorrente = contaCorrente;
		this.agencia = agencia;
		this.banco = banco;
		this.pix = pix;
		this.cpf = cpf;
		this.regiao = regiao;
		setDataCadastro(LocalDate.now());
		setRole("ONG");
	}

	public Ong(String nome, long telefone, String cep, String complemento,
			   String descricao, String segmento, String email, String senha, String cnpj,
			   String cnae, String contaCorrente, String agencia, String banco, String pix, String cpf, String regiao, LocalDate dataCadastro) {
		this.nome = nome;
		this.telefone = telefone;
		this.cep = cep;
		this.complemento = complemento;
		this.descricao = descricao;
		this.segmento = segmento;
		this.email = email;
		this.senha = senha;
		this.cnpj = cnpj;
		this.cnae = cnae;
		this.contaCorrente = contaCorrente;
		this.agencia = agencia;
		this.banco = banco;
		this.pix = pix;
		this.cpf = cpf;
		this.regiao = regiao;
		this.dataCadastro = dataCadastro;
		setRole("ONG");
	}

	public Ong(String nome, String email, String senha, String cnpj, String cnae) {
		this.nome = nome;
		this.cnpj = cnpj;
		this.email = email;
		this.senha = senha;
		this.cnae = cnae;
		setRole("ONG");
	}
	
	public Ong() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getTelefone() {
		return telefone;
	}

	public void setTelefone(long telefone) {
		this.telefone = telefone;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(LocalDate dataAtual) {
		this.dataCadastro = dataAtual;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
	
	
