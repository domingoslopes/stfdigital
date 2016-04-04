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
	@ApiModelProperty(value = "Assuntos do processo.", required = true)
	private List<String> assuntos;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required = true)
	private List<String> partesPoloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required = true)
	private List<String> partesPoloPassivo;
	
	public String getClasseId() {
		return classeId;
	}

	public void setClasseId(String classeId) {
		this.classeId = classeId;
	}

	public String getSigilo() {
		return sigilo;
	}

	public void setSigilo(String sigilo) {
		this.sigilo = sigilo;
	}

	public Long getNumeroRecursos() {
		return numeroRecursos;
	}

	public void setNumeroRecursos(Long numeroRecursos) {
		this.numeroRecursos = numeroRecursos;
	}

	public List<Long> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<Long> preferencias) {
		this.preferencias = preferencias;
	}

	public List<OrigemProcesso> getOrigens() {
		return origens;
	}

	public void setOrigens(List<OrigemProcesso> origens) {
		this.origens = origens;
	}

	public List<String> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(List<String> assuntos) {
		this.assuntos = assuntos;
	}

	public List<String> getPartesPoloAtivo() {
		return partesPoloAtivo;
	}

	public void setPartesPoloAtivo(List<String> partesPoloAtivo) {
		this.partesPoloAtivo = partesPoloAtivo;
	}

	public List<String> getPartesPoloPassivo() {
		return partesPoloPassivo;
	}

	public void setPartesPoloPassivo(List<String> partesPoloPassivo) {
		this.partesPoloPassivo = partesPoloPassivo;
	}
}
