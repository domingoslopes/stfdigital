package br.jus.stf.core.shared.processo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Esdras
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 14.08.2015
 */
@Embeddable
public class ProcessoId extends ValueObjectSupport<ProcessoId> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "SEQ_PROCESSO")
    private Long id;
    
    public ProcessoId(Long id) {
        this.id = id;
    }
    
    public ProcessoId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    public Long toLong() {
        return id;
    }
    
    @Override
    public String toString(){
        return toLong().toString();
    }
    
}
