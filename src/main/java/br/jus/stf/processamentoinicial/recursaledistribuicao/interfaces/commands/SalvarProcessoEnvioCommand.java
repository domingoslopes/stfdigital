package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para salvar um processo antes de enviá-lo para o STF.
 *  
 * @author Anderson.Araujo
 * @since 18.03.2016
 *
 */
@ApiModel("Comando usado para salvar um processo antes de enviá-lo para o STF.")
public class SalvarProcessoEnvioCommand {
	
	@ApiModelProperty(value = "Id da classe processual.", required = false)
	private String classeId;
	
	@ApiModelProperty(value = "Sigilo do processo.", required = false)
	private String sigilo;
	
	@ApiModelProperty(value = "Nº re recursos do processo.", required = false)
	private Long numeroRecursos;
	
	@ApiModelProperty(value = "Lista de preferências.", required = false)
	List<Long> preferencias;
	
	@ApiModelProperty(value = "Lista de origens do processo.", required = false)
	private List<OrigemProcesso> origens;
	
	@ApiModelProperty(value = "Id do assunto.", required = false)
	private String assuntoId;
	
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required = false)
	private List<String> partesPoloAtivo;
	
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required = false)
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

	public String getAssuntoId() {
		return assuntoId;
	}

	public void setAssuntoId(String assuntoId) {
		this.assuntoId = assuntoId;
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
