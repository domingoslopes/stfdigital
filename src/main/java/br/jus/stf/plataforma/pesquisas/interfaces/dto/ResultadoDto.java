package br.jus.stf.plataforma.pesquisas.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ResultadoDto {

	private String id;
	private String tipo;
	private Object objeto;
	
	public ResultadoDto(String id, String tipo, Object objeto) {
		this.id = id;
		this.tipo = tipo;
		this.objeto = objeto;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the objeto
	 */
	public Object getObjeto() {
		return objeto;
	}
	
	/**
	 * @param objeto the objeto to set
	 */
	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
		
	
}
