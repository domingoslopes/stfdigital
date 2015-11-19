package br.jus.stf.plataforma.notificacoes.interfaces.dto;

import java.util.Date;

/**
 * @author Lucas.Rodrigues
 *
 */
public class NotificacaoDto {

	private String id;
	private String mensagem;
	private Date dataCriacao;
	private boolean lida;
	
	public NotificacaoDto(final String id, final String mensagem, final Date dataCriacao, final boolean lida) {
		this.id = id;
		this.mensagem = mensagem;
		this.dataCriacao = dataCriacao;
		this.lida = lida;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}
	
	/**
	 * @return the dataCriacao
	 */
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	/**
	 * @return the lida
	 */
	public boolean getLida() {
		return lida;
	}
	
}
