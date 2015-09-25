package br.jus.stf.shared.stereotype;

public interface Entity<T, ID> {
	
	ID id();
	
	boolean sameIdentityAs(T other);
}
