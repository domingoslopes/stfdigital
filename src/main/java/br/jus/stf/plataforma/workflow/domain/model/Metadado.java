package br.jus.stf.plataforma.workflow.domain.model;

import java.util.Map;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Metadado implements ValueObject<Metadado>{

	private static final long serialVersionUID = -5599873356967540055L;
	
	private String tipoInformacao;
	private Object informacao;
	private String status;
	
	public Metadado(Object informacao, String tipoInformacao, String status) {
		Validate.notNull(informacao, "metadado.informacao.required");
		Validate.notBlank(tipoInformacao, "metadado.informacao.required");
		Validate.notBlank(status, "metadado.informacao.required");
		
		this.informacao = informacao;
		this.tipoInformacao = tipoInformacao;
		this.status = status;
	}
	
	public Object informacao() {
		return informacao;
	}
	
	public String tipoInformacao() {
		return tipoInformacao;
	}
	
	public String status() {
		return status;
	}
	
	public static Metadado converte(Map<String, Object> variaveis) {
		Object informacao = variaveis.get("informacao");
		String tipoInformacao = (String) variaveis.get("tipoInformacao");
		String status = (String) variaveis.get("status");
		return new Metadado(informacao, tipoInformacao, status);
	}

	@Override
	public boolean sameValueAs(Metadado other) {
		return informacao.equals(other.informacao) &&
				tipoInformacao.equalsIgnoreCase(other.tipoInformacao) &&
				status.equalsIgnoreCase(other.status);
	}
	
	
}
