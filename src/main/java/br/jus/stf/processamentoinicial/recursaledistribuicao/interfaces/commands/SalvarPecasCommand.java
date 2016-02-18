package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando de envio de peças processuais do front-end para o back-end.
 * 
 * @author Anderson.Araujo
 *
 * @since 17.02/2016
 */
@ApiModel(value="Comando de envio de peças processuais do front-end para o back-end.")
public class SalvarPecasCommand {
	@NotNull
	@ApiModelProperty(value = "Identificador do processo", required=true)
	private Long processoId;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista de peças processuais", required=true)
	private List<PecaProcessual> pecas;
	
	public Long getProcessoId(){
		return processoId;
	}
	
	public List<PecaProcessual> getPecas(){
		return pecas;
	}
}
