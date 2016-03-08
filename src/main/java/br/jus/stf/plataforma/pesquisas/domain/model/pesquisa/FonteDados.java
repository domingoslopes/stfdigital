
package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;


/**
 * @author Lucas.Rodrigues
 *
 */
@Embeddable
public class FonteDados implements ValueObject<FonteDados> {
	
    private static final long serialVersionUID = 1L;

	@Column(name = "TXT_CONSULTA")
	@Lob
	public String consulta;
	
	@Column(name = "DSC_VALOR")
	public String valor;
	
	@Column(name = "DSC_CONSULTA")
	public String descricao;
	
	FonteDados() {
		
	}
	 
    public FonteDados(final String consulta) {
    	Validate.notBlank(consulta, "fonteDados.consulta.required");

    	this.consulta = consulta;
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
	    result = prime * result + ((consulta == null) ? 0 : consulta.hashCode());
	    return result;
    }
	
	@Override
    public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    FonteDados other = (FonteDados) obj;
	    if (consulta == null) {
		    if (other.consulta != null) return false;
	    } else if (!consulta.equals(other.consulta)) return false;
	    return true;
    }
	
	@Override
    public boolean sameValueAs(FonteDados other) {
	    return other != null && other.consulta.equals(consulta);
    }
	
}
