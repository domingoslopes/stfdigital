package br.jus.stf.plataforma.notificacoes.interfaces.command;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando para marcar notificações como lidas")
public class MarcarLidasCommand {

	@ApiModelProperty("Lista de identificadores notificações")
	private List<String> notificacoes;
	
	public MarcarLidasCommand() {
		
	}

	/**
	 * @return the notificacoes
	 */
	public List<String> getNotificacoes() {
		return Optional.ofNullable(notificacoes).orElse(Collections.emptyList());
	}

	/**
	 * @param notificacoes
	 */
	public void setNotificacoes(List<String> notificacoes) {
		this.notificacoes = notificacoes;
	}
	
}
