package br.jus.stf.plataforma.dashboards.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
@Embeddable
public class DashboardId implements ValueObject<DashboardId> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SEQ_DASHBOARD", nullable = false)
	private Long sequencial;

	DashboardId() {

	}
	
	public DashboardId(final Long sequencial){
		Validate.notNull(sequencial, "dashboardId.sequencial.required");
		
		this.sequencial = sequencial;
	}

	public Long toLong(){
		return sequencial;
	}
	
	@Override
	public String toString(){
		return sequencial.toString();
	}	
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}

	@Override
	public boolean equals(final Object o){
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
	
		DashboardId other = (DashboardId) o;
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final DashboardId other){
		return other != null && this.sequencial.equals(other.sequencial);
	}

}
