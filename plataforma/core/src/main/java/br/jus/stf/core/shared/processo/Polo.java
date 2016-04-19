package br.jus.stf.core.shared.processo;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
public enum Polo {
	
	ATIVO("Polo Ativo"),
	PASSIVO("Polo Passivo"),
	INTERESSADO("Interessado");
	
	private String descricao;
	
	private Polo(String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

}
