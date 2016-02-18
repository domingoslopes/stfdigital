package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para enviar as peças de um processo para a exclusão, do front-end para o back-end.
 * 
 * @author Anderson.Araujo
 * @since 18.02.2016
 *
 */
@ApiModel(value="Comando usado para enviar as peças de um processo para a exclusão, do front-end para o back-end.")
public class ExcluirPecasCommand {
	@ApiModelProperty(value="Id do processo.")
	private Long processoId;
	
	@ApiModelProperty(value="Lista de peças a serem excluídas.")
	private List<Long> pecas;
	
	public Long getProcessoId(){
		return processoId;
	}
	
	public List<Long> getPecas(){
		return pecas;
	}
}
