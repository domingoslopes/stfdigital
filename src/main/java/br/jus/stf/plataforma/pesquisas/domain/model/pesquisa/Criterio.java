package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;


/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@Table(name = "CRITERIO_PESQUISA", schema = "PLATAFORMA")
public class Criterio implements ValueObject<Criterio> {
		
    private static final long serialVersionUID = 1L;
    
	@Id
	@Column(name = "SEQ_CRITERIO_PESQUISA")
	@SequenceGenerator(name = "CRITERIOPESQUISAID", sequenceName = "PLATAFORMA.SEQ_CRITERIO_PESQUISA", allocationSize = 1)
	@GeneratedValue(generator = "CRITERIOPESQUISAID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	@Column(name = "DSC_CRITERIO")
	private String descricao;
	
	@Column(name = "DSC_CAMPO")
	private String campo;
	
	@Column(name = "DSC_INDICE")
	private String indice;
	
	@Column(name = "TIP_CRITERIO")
	@Enumerated(EnumType.STRING)
	private TipoCriterio tipo;
	
	@Embedded
	private FonteDados fonteDados;
	
	Criterio() {
		
	}
	
    public Criterio(String descricao, String campo, String indice, TipoCriterio tipo) {
    	Validate.notBlank(descricao, "criterio.descricao.required");
    	Validate.notBlank(campo, "criterio.campo.required");
    	Validate.notBlank(indice, "criterio.indice.required");
    	Validate.notNull(tipo, "criterio.tipo.required");   	
    	
    	this.descricao = descricao;
    	this.campo = campo;
    	this.indice = indice;
    	this.tipo = tipo;
    }
    
    public Criterio(String descricao, String campo, String indice, TipoCriterio tipo, FonteDados fonteDados) {
    	this(descricao, campo, indice, tipo);
    	Validate.notNull(fonteDados, "criterio.fonteDados.required");
    	
    	this.fonteDados = fonteDados;
    }
    
    public String campo() {
    	return campo;
    }
	
	public String descricao() {
		return descricao;
	}
	
	public String indice() {
		return indice;
	}
	
	public TipoCriterio tipo() {
		return tipo;
	}
	
	public FonteDados fonteDados() {
		return fonteDados;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((sequencial == null) ? 0 : sequencial.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    Criterio other = (Criterio) obj;
	    if (sequencial == null) {
		    if (other.sequencial != null) return false;
	    } else if (!sequencial.equals(other.sequencial)) return false;
	    return true;
    }

	@Override
    public boolean sameValueAs(Criterio other) {
	    if (this == other) return true;
	    if (other == null) return false;
	    if (fonteDados == null) {
		    if (other.fonteDados != null) return false;
	    } else if (!fonteDados.equals(other.fonteDados)) return false;
	    if (campo == null) {
		    if (other.campo != null) return false;
	    } else if (!campo.equals(other.campo)) return false;
	    if (descricao == null) {
		    if (other.descricao != null) return false;
	    } else if (!descricao.equals(other.descricao)) return false;
	    if (tipo != other.tipo) return false;
	    return true;
    }
}
