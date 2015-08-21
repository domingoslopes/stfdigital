package br.jus.stf.shared.domain.stereotype;

public interface Entity<T> {
	
	boolean sameIdentityAs(T other);
}
