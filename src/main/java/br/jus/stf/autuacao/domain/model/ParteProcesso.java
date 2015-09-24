package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.jus.stf.shared.domain.model.PessoaId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@Table(name = "PROCESSO_PARTE", schema = "AUTUACAO")
public class ParteProcesso extends Parte {

	private static final long serialVersionUID = -729211641099556612L;
	
	public ParteProcesso(PessoaId pessoaId, TipoPolo tipoPolo) {
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
	
	//Hibernate
	@Id
	@Column(name = "SEQ_PROCESSO_PARTE")
	@SequenceGenerator(name = "PROCESSOPARTEID", sequenceName = "AUTUACAO.SEQ_PROCESSO_PARTE", allocationSize = 1)
	@GeneratedValue(generator = "PROCESSOPARTEID", strategy=GenerationType.SEQUENCE)
	private Long sequencial;
	
	ParteProcesso() {
		
	}
	
}
