package br.jus.stf.plataforma.documentos.domain.model;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

public class Edicao implements ValueObject<Edicao> {

	private static final long serialVersionUID = 1L;
	
	private String numero;
	private boolean ativo;
	
	public Edicao(final String numero) {
		Validate.notBlank(numero, "edicao.numero.required");
		
		this.numero = numero;
	}
	
	public void ativar() {
		this.ativo = true;
	}
	
	public String numero() {
		return numero;
	}
	
	public boolean ativo() {
		return ativo;
	}
	
	@Override
	public boolean sameValueAs(Edicao other) {
		return other != null && numero.equals(other.numero);
	}

	
}
