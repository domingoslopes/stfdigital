package br.jus.stf.plataforma.workflow.interfaces.commands;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
public class IniciarProcessoCommand {

	private String idProcesso;
	private String classe;
	private String[] partesPoloAtivo;
	private String[] partesPoloPassivo;
	private String[] documentos;
	
	public void setIdProcesso(String idProcesso) {
		this.idProcesso = idProcesso;
	}

	public String getIdProcesso() {
		return idProcesso;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public String[] getPartesPoloAtivo() {
		return this.partesPoloAtivo;
	}
	
	public void setPartesPoloAtivo(String[] partesPoloAtivo) {
		this.partesPoloAtivo = partesPoloAtivo;
	}
	
	public String[] getPartesPoloPassivo() {
		return this.partesPoloPassivo;
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
