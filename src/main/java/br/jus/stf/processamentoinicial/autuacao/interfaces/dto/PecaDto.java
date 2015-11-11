package br.jus.stf.processamentoinicial.autuacao.interfaces.dto;

/**
 * Representa a peça que será apresentada ao usuário.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M3
 * @since 02.10.2015
 */
public class PecaDto {

	private Long tipoId;
	
	private String tipoNome;
	
	private String descricao;
	
	private Long documentoId;
	
	public PecaDto(Long tipoId, String tipoNome, String descricao, Long documentoId) {
		this.tipoId = tipoId;
		this.tipoNome = tipoNome;
		this.descricao = descricao;
		this.documentoId = documentoId;
	}
	
	public Long getTipoId() {
		return tipoId;
	}
	
	public String getTipoNome() {
		return tipoNome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Long getDocumentoId() {
		return documentoId;
	}
	
}
