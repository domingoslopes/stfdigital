package br.jus.stf.shared.stereotype;

public interface DomainEvent<T> {
	
	boolean sameEventAs(T other);
}
