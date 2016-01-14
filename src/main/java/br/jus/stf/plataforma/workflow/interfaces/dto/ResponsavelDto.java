package br.jus.stf.plataforma.workflow.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ResponsavelDto {

	private Long usuarioId;
	private String login;
	
	public ResponsavelDto(Long usuarioId, String login) {
		this.usuarioId = usuarioId;
		this.login = login;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	
	public String getLogin() {
		return login; 
	}
	
}
