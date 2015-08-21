package br.jus.stf.shared.domain.stereotype;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {
	
	boolean sameValueAs(T other);
}
