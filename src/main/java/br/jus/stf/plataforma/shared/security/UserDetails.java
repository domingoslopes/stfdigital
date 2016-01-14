package br.jus.stf.plataforma.shared.security;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.UsuarioId;

/**
 * @author Lucas.Rodrigues
 *
 */
public class UserDetails {

	private UsuarioId usuarioId;
	private PessoaId pessoaId;
	private String nome;
	private List<String> roles = new ArrayList<String>(0);
	
	public UserDetails(UsuarioId usuarioId, PessoaId pessoaId, String nome) {
		
		Validate.notNull(usuarioId);
		Validate.notNull(pessoaId);
		Validate.notBlank(nome);
		
		this.usuarioId = usuarioId;
		this.pessoaId = pessoaId;
		this.nome = nome;
	}

	/**
	 * @return identificador do usuário
	 */
	public UsuarioId getUsuarioId() {
		return usuarioId;
	}
	
	/**
	 * @return identificador da pessoa
	 */
	public PessoaId getPessoaId() {
		return pessoaId;
	}
	
	/**
	 * @return nome completo do usuário
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @return os papéis do usuário
	 */
	public List<String> getPapeis() {
		return roles;
	}
	
}
