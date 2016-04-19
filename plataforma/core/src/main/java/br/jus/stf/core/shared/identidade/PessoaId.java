package br.jus.stf.core.shared.identidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Esdras
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
@Embeddable
public class PessoaId extends ValueObjectSupport<PessoaId> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "SEQ_PESSOA")
    private Long id;
    
    public PessoaId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    public PessoaId(Long id) {
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
