package br.jus.stf.plataforma.pesquisas.domain.model.query;

import java.util.Map;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Resultado implements ValueObject<Resultado> {

	private static final long serialVersionUID = -5222692946892693788L;
	
	private String id;
	private String tipo;
	private Map<String, Object> objeto;
	
	public Resultado(String id, String tipo, Map<String, Object> objeto) {
		Validate.notBlank(id, "resultado.id.required");
		Validate.notBlank(tipo, "resultado.tipo.required");
		Validate.notNull(objeto, "resultado.objeto.required");
		
		this.id = id;
		this.tipo = tipo;	
		this.objeto = objeto;
	}
	
	public String id() {
		return id;
	}
	
	public String tipo() {
		return tipo;
	}
	
	public Map<String, Object> objeto() {
		return objeto;
	}

	@Override
	public boolean sameValueAs(Resultado other) {
		return other != null && id.equals(other.id) &&
				tipo.equals(other.tipo) && objeto.equals(other.objeto);
	}	
	
}
