package br.jus.stf.plataforma.workflow.domain.model;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.UsuarioId;
import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Responsavel implements ValueObject<Responsavel> {

	private static final long serialVersionUID = 1L;
	private UsuarioId usuarioId;
	private String nome;

	public Responsavel(UsuarioId usuarioId) {
		Validate.notNull(usuarioId, "responsavel.usuarioId.required");
		
		this.usuarioId = usuarioId;
	}
	
	public Responsavel(UsuarioId usuarioId, String nome) {
		this(usuarioId);
		Validate.notBlank(nome, "responsavel.nome.required");
		
		this.nome = nome;
	}

	public UsuarioId usuarioId() {
		return usuarioId;
	}
	
	public String nome() {
		return nome;
	}

	@Override
	public boolean sameValueAs(Responsavel other) {
		return usuarioId.equals(other.usuarioId) &&
				nome.equalsIgnoreCase(other.nome);
	}

}
