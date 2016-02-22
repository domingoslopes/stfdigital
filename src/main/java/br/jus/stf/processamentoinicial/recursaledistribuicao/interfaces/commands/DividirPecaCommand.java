package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para dividir uma peça processual.
 *  
 * @author Anderson.Araujo
 * @since 19.02.2016
 *
 */
@ApiModel(value="Comando usado para dividir uma peça processual.")
public class DividirPecaCommand {
	
	@ApiModelProperty(value="Id do processo.")
	private Long processoId;
	
	@ApiModelProperty(value="Id da peça original.")
	private Long pecaId;
	
	@ApiModelProperty(value="Lista de peças criadas.")
	private List<PecaProcessual> pecas;
	
	public Long getProcessoId(){
		return processoId;
	}
	
	public Long getPecaId(){
		return pecaId;
	}
	
	public List<PecaProcessual> getPecas(){
		return pecas;
	}
}
