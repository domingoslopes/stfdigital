package br.jus.stf.plataforma.notificacoes.interfaces.command;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando para notificação")
public class NotificarCommand {

	@ApiModelProperty("Tipo de notificação, Ex: UI, EMAIL")
	@NotNull
	private String tipo;
	
	@ApiModelProperty("Mensagem a ser enviada")
	@NotBlank
	private String mensagem;
	
	@ApiModelProperty("Usuário a serem notificados")
	@NotEmpty
	private List<String> notificados;
	
	public NotificarCommand() {
		
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<String> getNotificados() {
		return notificados;
	}

	public void setNotificados(List<String> notificados) {
		this.notificados = notificados;
	}
	
}
