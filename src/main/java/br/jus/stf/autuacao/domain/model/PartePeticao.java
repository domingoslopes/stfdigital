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
@Table(name = "PARTE_PETICAO")
public class PartePeticao extends Parte {

	private static final long serialVersionUID = 4186066720961522553L;
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    final PartePeticao other = (PartePeticao) obj;
	    return sameValueAs(other);
	}
	
	//Hibernate
	@Id
	@Column(name = "SEQ_PARTE_PETICAO")
	@SequenceGenerator(name = "PARTEPETICAOID", sequenceName = "SEQ_PARTE_PETICAO", allocationSize = 1)
	@GeneratedValue(generator = "PARTEPETICAOID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	PartePeticao() {
		
	}
	
}
