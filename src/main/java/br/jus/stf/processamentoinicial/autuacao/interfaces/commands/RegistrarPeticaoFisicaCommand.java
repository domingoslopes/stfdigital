package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;

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
	private Integer quantidadeVolumes;
	
	@NotNull
	@ApiModelProperty(value = "Quantidade de apensos recebidos", required=true)
	private Integer quantidadeApensos;
	
	@ApiModelProperty(value = "Número do Sedex, caso a forma de recebimento seja Sedex")
	private String numeroSedex;
	
	@ApiModelProperty(value = "Tipo de processo (Origiário ou Recursal)")
	private String tipoProcesso;
	
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

	public int getQuantidadeVolumes() {
		return quantidadeVolumes;
	}

	public int getQuantidadeApensos() {
		return quantidadeApensos;
	}

	public String getNumeroSedex() {
		return numeroSedex;
	}
	
	public String getTipoProcesso(){
		return this.tipoProcesso;
	}
}
