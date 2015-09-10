package br.jus.stf.autuacao.interfaces.commands;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Rodrigo Barreiros
 * @author Anderson.Araujo
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
public class RegistrarPeticaoFisicaCommand {

	@NotBlank
	private String formaRecebimento;
	@NotBlank
	private int quantidadeVolumes;
	@NotBlank
	private int quantidadeApensos;
	@NotBlank
	private String numeroSedex;
	
	public RegistrarPeticaoFisicaCommand() {
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
