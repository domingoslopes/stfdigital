package br.jus.stf.plataforma.workflow.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class MetadadoDto {

	private Object informacao;
	private String tipoInformacao;
	private String status;
	
	public MetadadoDto(Object informacao, String tipoInformacao, String status) {
		this.informacao = informacao;
		this.tipoInformacao = tipoInformacao;
		this.status = status;
	}

	public Object getInformacao() {
		return informacao;
	}

	public String getTipoInformacao() {
		return tipoInformacao;
	}

	public String getStatus() {
		return status;
	}	
	
}
