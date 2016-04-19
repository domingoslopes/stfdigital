package br.jus.stf.core.shared.classe;

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
public class ClasseId extends ValueObjectSupport<ClasseId> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "SIG_CLASSE")
    private String id;
    
    public ClasseId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }
    
    public ClasseId(String id) {
        this.id = id;
    }

     @Override
    public String toString(){
        return id;
    }
    
}
