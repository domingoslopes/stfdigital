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
	private String informacao;
	private String status;
	private String descricao;
	
	public Metadado(String status, String descricao) {
		Validate.notBlank(status, "metadado.status.required");
		
		this.status = status;
		this.descricao = descricao;
	}
	
	public Metadado(String informacao, String tipoInformacao, String status, String descricao) {
		Validate.notBlank(informacao, "metadado.informacao.required");
		Validate.notBlank(tipoInformacao, "metadado.tipoInformacao.required");
		Validate.notBlank(status, "metadado.status.required");
		Validate.notBlank(descricao, "metadado.descricao.required");
		
		this.informacao = informacao;
		this.tipoInformacao = tipoInformacao;
		this.status = status;
		this.descricao = descricao;
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
	
	public String descricao() {
		return descricao;
	}
	
	public static Metadado converte(Map<String, Object> variaveis) {
		String informacao = (String) variaveis.get("informacao");
		String tipoInformacao = (String) variaveis.get("tipoInformacao");
		String status = (String) variaveis.get("status");
		String descricao = (String) variaveis.get("descricao");
		return new Metadado(informacao, tipoInformacao, status, descricao);
	}

	@Override
	public boolean sameValueAs(Metadado other) {
		return informacao.equals(other.informacao) &&
				tipoInformacao.equalsIgnoreCase(other.tipoInformacao) &&
				status.equalsIgnoreCase(other.status);
	}
	
	
}
