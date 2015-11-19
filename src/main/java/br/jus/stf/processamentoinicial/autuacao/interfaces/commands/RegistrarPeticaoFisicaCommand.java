package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * @author Anderson.Araujo
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@ApiModel(value = "Contém as informações para registro de uma nova petição física")
public class RegistrarPeticaoFisicaCommand {

	@NotBlank
	@ApiModelProperty(value = "Forma de recebimento da petição: SEDEX, BALCAO, FAX, MALOTE e EMAIL", required=true)
	private String formaRecebimento;
	
	@NotNull
	@ApiModelProperty(value = "Quantidade de volumes recebidos", required=true)
	private int quantidadeVolumes;
	
	@NotNull
	@ApiModelProperty(value = "Quantidade de apensos recebidos", required=true)
	private int quantidadeApensos;
	
	@ApiModelProperty(value = "Número do Sedex, caso a forma de recebimento seja Sedex")
	private String numeroSedex;
	
	@AssertTrue
	public boolean isValid() {
		if (FormaRecebimento.SEDEX.equals(FormaRecebimento.valueOf(formaRecebimento))) {
			return StringUtils.isNotBlank(numeroSedex);
		}
		return true;
	}

	public String getFormaRecebimento() {
		return formaRecebimento;
	}

	public void setFormaRecebimento(String formaRecebimento) {
		this.formaRecebimento = formaRecebimento;
	}

	public int getQuantidadeVolumes() {
		return quantidadeVolumes;
	}

	public void setQuantidadeVolumes(int quantidadeVolumes) {
		this.quantidadeVolumes = quantidadeVolumes;
	}

	public int getQuantidadeApensos() {
		return quantidadeApensos;
	}

	public void setQuantidadeApensos(int quantidadeApensos) {
		this.quantidadeApensos = quantidadeApensos;
	}

	public String getNumeroSedex() {
		return numeroSedex;
	}

	public void setNumeroSedex(String numeroSedex) {
		this.numeroSedex = numeroSedex;
	}
	
}
