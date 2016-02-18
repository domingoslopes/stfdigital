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

	private Long sequencial;
	
	private Long tipoId;
	
	private String tipoNome;
	
	private String descricao;
	
	private Long documentoId;
	
	private Long numeroOrdem;
	
	private String visibilidade;
	
	private String situacao;
	
	public PecaDto(Long sequencial, Long tipoId, String tipoNome, String descricao, Long documentoId,
			Long numeroOrdem, String visibilidade, String situacao) {
		this.sequencial = sequencial;
		this.tipoId = tipoId;
		this.tipoNome = tipoNome;
		this.descricao = descricao;
		this.documentoId = documentoId;
		this.numeroOrdem = numeroOrdem;
		this.visibilidade = visibilidade;
		this.situacao = situacao;
	}
	
	public Long getSequencial() {
		return sequencial;
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
	
	public String getVisibilidade() {
		return visibilidade;
	}
	
	public String getSituacao() {
		return situacao;
	}
	
	public Long getNumeroOrdem() {
		return numeroOrdem;
	}
	
}
