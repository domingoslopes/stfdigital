/**
 * 
 */
package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

/**
 * Objeto usado para enviar os dados das origens de um processo.
 * 
 * @author Anderson.Araujo
 * @since 18.03.2016
 *
 */
public class OrigemProcesso {
	private Long unidadeFederacaoId;
	private Long codigoJuizoOrigem;
	private Long numeroProcesso;
	private Long numeroOrdem;
	private Boolean isPrincipal;
		
	public OrigemProcesso(){
		
	}
	
	public OrigemProcesso (Long unidadeFederacaoId, long codigoJuizoOrigem, Long numeroProcesso, Long numeroOrdem, Boolean isPrincipal){
		this.unidadeFederacaoId = unidadeFederacaoId;
		this.codigoJuizoOrigem = codigoJuizoOrigem;
		this.numeroProcesso = numeroProcesso;
		this.numeroOrdem = numeroOrdem;
		this.isPrincipal = isPrincipal;
	}
	
	public Long getUnidadeFederacaoId() {
		return unidadeFederacaoId;
	}
	
	public void setUnidadeFederacaoId(Long unidadeFederacaoId) {
		this.unidadeFederacaoId = unidadeFederacaoId;
	}
	
	public Long getCodigoJuizoOrigem() {
		return codigoJuizoOrigem;
	}
	
	public void setCodigoJuizoOrigem(Long codigoJuizoOrigem) {
		this.codigoJuizoOrigem = codigoJuizoOrigem;
	}
	
	public Long getNumeroProcesso() {
		return numeroProcesso;
	}
	
	public void setNumeroProcesso(Long numero) {
		this.numeroProcesso = numero;
	}
	
	public Long getNumeroOrdem() {
		return numeroOrdem;
	}
	
	public void setNumeroOrdem(Long numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}
	
	public Boolean getIsPrincipal() {
		return isPrincipal;
	}
	
	public void setIsPrincipal(Boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
	}
}
