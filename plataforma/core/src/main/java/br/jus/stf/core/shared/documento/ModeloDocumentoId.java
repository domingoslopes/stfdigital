package br.jus.stf.core.shared.documento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 15.04.2016
 */
@Embeddable
public class ModeloDocumentoId extends ValueObjectSupport<ModeloDocumentoId> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "SEQ_MODELO_DOCUMENTO")
    private Long id;
    
    public ModeloDocumentoId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    public ModeloDocumentoId(Long id) {
        this.id = id;
    }
    
    public Long toLong() {
        return id;
    }

     @Override
    public String toString(){
        return id.toString();
    }
    
}
