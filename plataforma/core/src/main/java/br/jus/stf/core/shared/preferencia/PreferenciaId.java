package br.jus.stf.core.shared.preferencia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Esdras
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@Embeddable
public class PreferenciaId extends ValueObjectSupport<PreferenciaId> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "SEQ_PREFERENCIA")
    private Long id;
    
    public PreferenciaId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    public PreferenciaId(Long id) {
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
