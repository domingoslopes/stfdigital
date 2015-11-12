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
	
	@NotNull
	@ApiModelProperty(value = "A sigla indicando a classe processual.", required=true)
	private String classeProcessual;
	
	@NotNull
	@ApiModelProperty(value = "Uma justificativa para distribuição.", required=true)
	private String justificativa;
	
	@NotNull
	@ApiModelProperty(value = "Lista dos ministros candidatos à relatoria.", required=true)
	private Set<Long> ministrosCandidatos;
	
	@NotNull
	@ApiModelProperty(value = "Lista dos ministros impedidos de relatar o processo.", required=true)
	private Set<Long> ministrosImpedidos;
	
	@NotNull
	@ApiModelProperty(value = "Lista dos processos que embasam a prevenção.", required=true)
	private Set<Long> processosPreventos;

	public String getTipoDistribuicao() {
		return this.tipoDistribuicao;
	}
	
	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public String getClasseProcessual() {
		return this.classeProcessual;
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
