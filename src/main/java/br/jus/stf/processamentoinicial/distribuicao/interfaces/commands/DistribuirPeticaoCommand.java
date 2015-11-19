package br.jus.stf.processamentoinicial.distribuicao.interfaces.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 10.11.2015
 */
@ApiModel(value = "Contém as informações necessárias para distribuir uma nova petição")
public class DistribuirPeticaoCommand {

	@NotNull
	@ApiModelProperty(value = "O código indicando o tipo de distribuição.", required=true)
	private String tipoDistribuicao;
	
	@NotNull
	@ApiModelProperty(value = "Id da petição distribuída.", required=true)
	private Long peticaoId;
	
	@ApiModelProperty(value = "Uma justificativa para distribuição.")
	private String justificativa;
	
	@ApiModelProperty(value = "Lista dos ministros candidatos à relatoria.")
	private Set<Long> ministrosCandidatos;
	
	@ApiModelProperty(value = "Lista dos ministros impedidos de relatar o processo.")
	private Set<Long> ministrosImpedidos;
	
	@ApiModelProperty(value = "Lista dos processos que embasam a prevenção.")
	private Set<Long> processosPreventos;

	public String getTipoDistribuicao() {
		return this.tipoDistribuicao;
	}
	
	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public String getJustificativa() {
		return this.justificativa;
	}
	
	public Set<Long> getMinistrosCandidatos() {
		return this.ministrosCandidatos; 
	}
	
	public Set<Long> getMinistrosImpedidos() {
		return this.ministrosImpedidos; 
	}
	
	public Set<Long> getProcessosPreventos() {
		return this.processosPreventos; 
	}
}
