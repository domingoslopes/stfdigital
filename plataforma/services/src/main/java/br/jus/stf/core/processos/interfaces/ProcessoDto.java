package br.jus.stf.core.processos.interfaces;

import java.util.Date;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.03.2016
 */
public class ProcessoDto {

	private String processo;
	private String texto;
	private String observacao;
	private String fase;
	private String ministro;
	private String responsavel;
	private Date dataInicio;
	private Boolean controleVoto;
	
	public ProcessoDto(String processo, String fase, String ministro, Date dataInicio) {
		this.processo = processo;
		this.fase = fase;
		this.ministro = ministro;
		this.dataInicio = dataInicio;
	}

	public String getProcesso() {
		return processo;
	}

	public String getTexto() {
		return texto;
	}

	public String getObservacao() {
		return observacao;
	}

	public String getFase() {
		return fase;
	}

	public String getMinistro() {
		return ministro;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public Boolean getControleVoto() {
		return controleVoto;
	}

}
