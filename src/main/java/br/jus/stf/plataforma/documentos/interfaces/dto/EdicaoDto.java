package br.jus.stf.plataforma.documentos.interfaces.dto;

/**
 * Dto para Edição de um documento.
 * 
 * @author Tomas.Godoi
 *
 */
public class EdicaoDto {

	private String numero;

	public EdicaoDto(final String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
