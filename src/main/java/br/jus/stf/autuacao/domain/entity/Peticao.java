package br.jus.stf.autuacao.domain.entity;

import java.util.List;

/**
 * Representa uma petição.
 * 
 * @author anderson.araujo
 * @version 1.0.0.0
 * @since 23.07.2015
 */
public class Peticao {
	private String id;
	private ClasseProcessual classe;
	private ClasseProcessual classeSugerida;
	private Polo poloAtivo;
	private Polo poloPassivo;
	private List<Documento> documentos;
	private Ministro ministroRelator;
	private String formaRecebimento;
	private int quantidadeVolumes;
	private int quantidadeApensos;
	private String numeroSedex;
	
	public Peticao(){
		
	}
	
	public Peticao(ClasseProcessual classeSugerida, ClasseProcessual classeDefinitiva, Polo poloAtivo, Polo poloPassivo, List<Documento> documentos) {
		this.classeSugerida = classeSugerida;
		this.classe = classeDefinitiva;
		this.poloAtivo = poloAtivo;
		this.poloPassivo = poloPassivo;
		this.documentos = documentos;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public ClasseProcessual getClasse() {
		return classe;
	}
	
	public void setClasse(ClasseProcessual classe) {
		this.classe = classe;
	}
	
	public ClasseProcessual getClasseSugerida() {
		return this.classeSugerida;
	}
	
	public void setClasseSugerida(ClasseProcessual classeSugerida) {
		this.classeSugerida = classeSugerida;
	}
	
	public Polo getPoloAtivo() {
		return poloAtivo;
	}
	
	public void setPoloAtivo(Polo poloAtivo) {
		this.poloAtivo = poloAtivo;
	}
	
	public Polo getPoloPassivo() {
		return poloPassivo;
	}
	
	public void setPoloPassivo(Polo poloPassivo) {
		this.poloPassivo = poloPassivo;
	}
	
	public List<Documento> getDocumentos() {
		return documentos;
	}
	
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Ministro getMinistroRelator() {
		return ministroRelator;
	}

	public void setMinistroRelator(Ministro ministroRelator) {
		this.ministroRelator = ministroRelator;
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
