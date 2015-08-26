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
@Table(name = "PARTE_PROCESSO")
public class ParteProcesso extends Parte {

	private static final long serialVersionUID = -729211641099556612L;
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    final ParteProcesso other = (ParteProcesso) obj;
	    return sameValueAs(other);
	}
	
	//Hibernate
	@Id
	@Column(name = "SEQ_PARTE_PROCESSO")
	@SequenceGenerator(name = "PARTEPROCESSOID", sequenceName = "SEQ_PARTE_PROCESSO", allocationSize = 1)
	@GeneratedValue(generator = "PARTEPROCESSOID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	ParteProcesso() {
		
	}
	
}
