package br.jus.stf.plataforma.documentos.interfaces.dto;

public class StatusEdicaoDto {

	private boolean completa;

	public StatusEdicaoDto(final boolean completa) {
		this.completa = completa;
	}

	public boolean isCompleta() {
		return completa;
	}

	public void setCompleta(boolean completa) {
		this.completa = completa;
	}
}