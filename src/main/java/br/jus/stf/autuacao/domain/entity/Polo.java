package br.jus.stf.autuacao.domain.entity;

import java.util.List;

/**
 * Representa um polo do litígio.
 * 
 * @author anderson.araujo
 * @version 1.0.0
 * @since 23.07.2015
 *
 */
public class Polo {
	private List<Parte> partes;

	public List<Parte> getPartes() {
		return partes;
	}

	public void setPartes(List<Parte> partes) {
		this.partes = partes;
	}
	
	public Polo(){
		
	}
	
	public Polo(List<Parte> partes){
		this.partes = partes;
	}
}
