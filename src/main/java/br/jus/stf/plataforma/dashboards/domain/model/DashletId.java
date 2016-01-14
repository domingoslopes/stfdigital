package br.jus.stf.plataforma.dashboards.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.elasticsearch.common.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
@Embeddable
public class DashletId implements ValueObject<DashletId> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_DASHLET")
	private Long sequencial;

	DashletId() {
		
	}
	
	public DashletId(Long sequencial) {
		Validate.notNull(sequencial, "dashletid.sequencial.required");
		
		this.sequencial = sequencial;
	}
	
	public Long toLong() {
		return sequencial;
	}
	
	@Override
	public String toString() {
		return sequencial.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((sequencial == null) ? 0 : sequencial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		DashletId other = (DashletId) obj;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(DashletId other) {
		return sequencial.equals(sequencial);
	}	

}
