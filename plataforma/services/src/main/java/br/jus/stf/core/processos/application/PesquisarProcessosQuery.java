package br.jus.stf.core.processos.application;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.02.2016
 */
public class PesquisarProcessosQuery {
	
	private String protocolo;
	
	private String parte;
	
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getProtocolo() {
		return protocolo;
	}
	
	public void setParte(String parte) {
		this.parte = parte;
	}
	
	public String getParte() {
		return parte;
	}
	
}
