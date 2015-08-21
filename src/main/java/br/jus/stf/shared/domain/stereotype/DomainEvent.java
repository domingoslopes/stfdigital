package br.jus.stf.shared.domain.stereotype;

public interface DomainEvent<T> {
	
	boolean sameEventAs(T other);
}
