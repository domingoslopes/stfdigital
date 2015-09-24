package br.jus.stf.shared.domain.stereotype;

public interface Entity<T, ID> {
	
	ID id();
	
	boolean sameIdentityAs(T other);
}
