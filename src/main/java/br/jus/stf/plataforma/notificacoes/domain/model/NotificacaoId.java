package br.jus.stf.plataforma.notificacoes.domain.model;

import java.util.UUID;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class NotificacaoId implements ValueObject<NotificacaoId> {

	private static final long serialVersionUID = 7390940575653313816L;
	
	private UUID uuid;
		
	public NotificacaoId(UUID uuid) {
		Validate.notNull(uuid, "notificacaoid.uuid.required");
		
		this.uuid = uuid;
	}
	
	@Override
	public String toString() {
		return uuid.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		NotificacaoId other = (NotificacaoId) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(NotificacaoId other) {
		return uuid.equals(other.uuid);
	}

}
