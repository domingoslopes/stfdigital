package br.jus.stf.plataforma.pesquisas.domain.model.indexacao;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Indice implements ValueObject<Indice> {

	private static final long serialVersionUID = -6744783434092030939L;
	
	private String nome;
	private String configuracao;
	private Map<String, String> mapeamentos = new HashMap<String, String>();
	
	public Indice(String nome) {
		Validate.notBlank(nome, "indice.nome.required");
		
		this.nome = nome;
	}
	
	public Indice(String nome, String configuracao, Map<String, String> mapeamentos) {
		Validate.notBlank(nome, "indice.nome.required");
		Validate.notBlank(configuracao, "indice.configuracao.required");
		Validate.notEmpty(mapeamentos, "indice.mapeamentos.required");
		
		this.nome = nome;
		this.configuracao = configuracao;
		this.mapeamentos.putAll(mapeamentos);
	}
	
	public String nome() {
		return nome;
	}
	
	public String configuracao() {
		return configuracao;
	}
	
	public Map<String, String> mapeamentos() {
		return mapeamentos;
	}

	@Override
	public boolean sameValueAs(Indice other) {
		return other != null && nome.equals(other.nome) &&
				configuracao.equals(other.configuracao) &&
				mapeamentos.equals(other.mapeamentos);
	}

}
