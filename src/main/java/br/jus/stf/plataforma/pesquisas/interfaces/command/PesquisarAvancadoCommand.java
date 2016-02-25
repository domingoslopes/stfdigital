package br.jus.stf.plataforma.pesquisas.interfaces.command;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * @author Lucas.Rodrigues
 *
 */
@ApiModel("Comando para executar uma consulta")
public class PesquisarAvancadoCommand {
	
	@ApiModelProperty("Consulta a ser executada")
	@NotBlank
	private String consulta;
	
	@ApiModelProperty("Página do resultado")
	@NotNull
	private Integer pagina = 0;
	
	@ApiModelProperty("Tamanho máximo do resultado")
	@NotNull
	private Integer tamanho = 15;
	
	public String getConsulta() {
		return consulta;
	}

	public Integer getPagina() {
	    return pagina;
    }

	public Integer getTamanho() {
	    return tamanho;
    }
	
}
