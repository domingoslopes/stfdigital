package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Rafael Alencar
 *
 */
public enum ProcessoSituacao implements ValueObject<ProcessoSituacao> {
	
	DISTRIBUIDO;
	
	@Override
	public String toString() {
		String tipo = null;
		
		switch(this) {
			case DISTRIBUIDO: tipo = "Distribuído"; break;
			default: break;
		}
		return tipo;
	}
	
	@Override
	public boolean sameValueAs(final ProcessoSituacao other) {
		return this.equals(other);
	}
	
}
