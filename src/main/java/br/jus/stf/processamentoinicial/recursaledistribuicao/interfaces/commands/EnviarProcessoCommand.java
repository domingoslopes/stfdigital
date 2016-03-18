package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Commando usado para enviar um processo.
 * 
 * @author Anderson.Araujo
 * @since 18.03.2016
 *
 */
@ApiModel("Commando usado para enviar um processo.")
public class EnviarProcessoCommand {
	@NotBlank
	@ApiModelProperty(value = "Id da classe processual.", required=true)
	private String classeId;
	
	@NotBlank
	@ApiModelProperty(value = "Sigilo do processo.", required = true)
	private String sigilo;
	
	@NotNull
	@ApiModelProperty(value = "Nº re recursos do processo.", required = true)
	private Long numeroRecursos;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista de preferências.", required = true)
	List<Long> preferencias;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista de origens do processo.", required = true)
	private List<OrigemProcesso> origens;
	
	@NotEmpty
	@ApiModelProperty(value = "Id do assunto.", required = true)
	private String assuntoId;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required = true)
	private List<String> partesPoloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required = true)
	private List<String> partesPoloPassivo;
}
