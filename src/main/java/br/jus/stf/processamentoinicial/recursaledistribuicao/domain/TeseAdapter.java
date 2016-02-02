package br.jus.stf.processamentoinicial.recursaledistribuicao.domain;

import java.util.Set;

import br.jus.stf.shared.TeseId;

public interface TeseAdapter {
	
	/**
	 * Verifica se a lista de ids contém alguma tese do tipo repercurssão geral.
	 * 
	 * @param tesesIds
	 * @return
	 */
	boolean contemTeseDoTipoRepercursaoGeral(Set<TeseId> tesesIds);
	
}
