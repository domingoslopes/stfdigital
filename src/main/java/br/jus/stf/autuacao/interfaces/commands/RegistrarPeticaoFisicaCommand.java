package br.jus.stf.autuacao.interfaces.commands;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
public class RegistrarPeticaoFisicaCommand {

	@NotBlank
	private String tipoRecebimento;
	@NotBlank
	private String siglaClasse;
	@NotBlank
	private String[] partesPoloAtivo;
	@NotBlank
	private String[] partesPoloPassivo;
	@NotBlank
	private String[] documentos;

	public RegistrarPeticaoFisicaCommand() {
	}

	public RegistrarPeticaoFisicaCommand(String tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public void setTipoRecebimento(String tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	public String getTipoRecebimento() {
		return tipoRecebimento;
	}

	public String getSiglaClasse() {
		return siglaClasse;
	}

	public void setSiglaClasse(String siglaClasse) {
		this.siglaClasse = siglaClasse;
	}

	public String[] getPartesPoloAtivo() {
		return partesPoloAtivo;
	}

	public void setPartesPoloAtivo(String[] partesPoloAtivo) {
		this.partesPoloAtivo = partesPoloAtivo;
	}

	public String[] getPartesPoloPassivo() {
		return partesPoloPassivo;
	}

	public void setPartesPoloPassivo(String[] partesPoloPassivo) {
		this.partesPoloPassivo = partesPoloPassivo;
	}

	public String[] getDocumentos() {
		return documentos;
	}

	public void setDocumentos(String[] documentos) {
		this.documentos = documentos;
	}

}
