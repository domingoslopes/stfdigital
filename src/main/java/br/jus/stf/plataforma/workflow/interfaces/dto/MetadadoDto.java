package br.jus.stf.plataforma.workflow.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class MetadadoDto {

	private Object informacao;
	private String tipoInformacao;
	private String status;
	private String descricao;
	
	public MetadadoDto(Object informacao, String tipoInformacao, String status, String descricao) {
		this.informacao = informacao;
		this.tipoInformacao = tipoInformacao;
		this.status = status;
		this.descricao = descricao;
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
	
	public String getDescricao(){
		return descricao;
	}
	
}
