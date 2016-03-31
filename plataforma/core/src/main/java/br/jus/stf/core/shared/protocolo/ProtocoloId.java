package br.jus.stf.core.shared.protocolo;

import java.io.Serializable;

import javax.persistence.Column;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;

/**
 * @author Rafael Esdras
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.02.2016
 */
public class ProtocoloId extends ValueObjectSupport<ProtocoloId> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_PROTOCOLO")
	private Long id;
	
    public ProtocoloId() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    public ProtocoloId(Long id) {
    	this.id = id;
    }
    
    public Long toLong() {
    	return this.id;
    }
    
    @Override
    public String toString() {
        return this.id.toString();
    }

}
