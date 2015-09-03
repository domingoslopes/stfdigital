package br.jus.stf.autuacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Lucas.Rodrigues
 *
 */
@Entity
@Table(name = "PETICAO_PARTE", schema = "AUTUACAO")
public class PartePeticao extends Parte {

	private static final long serialVersionUID = 4186066720961522553L;
	
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
	@Column(name = "SEQ_PETICAO_PARTE")
	@SequenceGenerator(name = "PETICAOPARTEID", sequenceName = "AUTUACAO.SEQ_PETICAO_PARTE", allocationSize = 1)
	@GeneratedValue(generator = "PETICAOPARTEID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	PartePeticao() {
		
	}
	
}
