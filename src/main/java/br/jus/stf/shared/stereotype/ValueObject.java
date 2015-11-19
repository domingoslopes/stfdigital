package br.jus.stf.shared.stereotype;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable{
	
	boolean sameValueAs(T other);
}
