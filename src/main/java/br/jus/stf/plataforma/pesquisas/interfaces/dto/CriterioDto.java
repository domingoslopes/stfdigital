package br.jus.stf.plataforma.pesquisas.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class CriterioDto {
	
	private String campo;
	private String descricao;
	private String indice;
	private String tipo;
	private FonteDadosDto fonteDados;
	
			
	public CriterioDto(String campo, String descricao, String indice, String tipo, FonteDadosDto fonteDados) {
	    this.campo = campo;
	    this.descricao = descricao;
	    this.indice = indice;
	    this.tipo = tipo;
	    this.fonteDados = fonteDados;
    }
	
    public String getCampo() {
    	return campo;
    }
    
    public String getDescricao() {
    	return descricao;
    }
    
    public String getIndice() {
    	return indice;
    }
    
    public String getTipo() {
    	return tipo;
    }
    
    public FonteDadosDto getFonteDados() {
    	return fonteDados;
    }
	
}
