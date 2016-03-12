
package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;


/**
 * @author Lucas.Rodrigues
 *
 */
@Embeddable
public class FonteDados implements ValueObject<FonteDados> {
	
    private static final long serialVersionUID = 1L;

	@Column(name = "DSC_API")
	private String api;
	
	@Column(name = "DSC_VALOR")
	private String valor;
	
	@Column(name = "DSC_DESCRICAO")
	private String descricao;
	
	FonteDados() {
		
	}
	 
    public FonteDados(final URI api, final String valor, final String descricao) {
    	Validate.notNull(api, "fonteDados.api.required");
    	Validate.notEmpty(valor, "fonteDados.valor.required");
    	Validate.notEmpty(descricao, "fonteDados.descricao.required");

    	this.api = api.toString();
    	this.valor = valor;
    	this.descricao = descricao;
    }
    
    public String api() {
    	return api;
    }
    
    public String valor() {
    	return valor;
    }
    
    public String descricao() {
    	return descricao;
    }
	
	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((api == null) ? 0 : api.hashCode());
	    return result;
    }
	
	@Override
    public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    FonteDados other = (FonteDados) obj;
	    if (api == null) {
		    if (other.api != null) return false;
	    } else if (!api.equals(other.api)) return false;
	    return true;
    }
	
	@Override
    public boolean sameValueAs(FonteDados other) {
	    return other != null && other.api.equals(api);
    }
	
}
