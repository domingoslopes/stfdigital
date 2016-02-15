package br.jus.stf.processamentoinicial.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.jus.stf.processamentoinicial.suporte.domain.model.Parte;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.shared.PessoaId;

/**
 * @author Lucas.Rodrigues
 */
@Entity
@Table(name = "PETICAO_PARTE", schema = "AUTUACAO")
public class PartePeticao extends Parte {

	private static final long serialVersionUID = 4186066720961522553L;
	
	@Id
	@Column(name = "SEQ_PETICAO_PARTE")
	@SequenceGenerator(name = "PETICAOPARTEID", sequenceName = "AUTUACAO.SEQ_PETICAO_PARTE", allocationSize = 1)
	@GeneratedValue(generator = "PETICAOPARTEID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	PartePeticao() {
		
	}
	
	public PartePeticao(PessoaId pessoaId, TipoPolo tipoPolo) {
		super(pessoaId, tipoPolo);
	}

	@Override
    public int hashCode() {
	    return super.hashCode();
    }

	@Override
    public boolean equals(Object obj) {
		return super.equals(obj);
    }
	
}
