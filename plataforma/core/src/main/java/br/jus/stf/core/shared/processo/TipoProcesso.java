package br.jus.stf.core.shared.processo;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 11.04.2016
 */
public enum TipoProcesso {
	
	ORIGINARIO("Originário"),
	RECURSAL("Recursal");
	
	private String descricao;
	
	private TipoProcesso(String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

}
