package com.stefanini.uhubank.rest.dto;

import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.stefanini.uhubank.models.Arquivo;

public class UsuarioDTO implements DTO<UsuarioDTO>{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty(message = "login: é obrigatório")
	private String login;
	
	@NotEmpty(message = "senha: é obrigatório")
	private String senha;
	
	@NotEmpty(message = "email: é obrigatório")
	private String email;
	
	private Set<PerfilDTO> perfis;
	
	private Arquivo imagem;
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(String login, String senha, String email) {
		this.login = login;
		this.senha = senha;
		this.email = email;
	}
	
	public UsuarioDTO(String login, String senha, String email, Set<PerfilDTO> perfis) {
		this(login,senha,email);
		this.perfis = perfis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public Set<PerfilDTO> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<PerfilDTO> perfis) {
		this.perfis = perfis;
	}

	public Arquivo getImagem() {
		return imagem;
	}

	public void setImagem(Arquivo imagem) {
		this.imagem = imagem;
	}
}
