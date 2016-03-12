package br.jus.stf.plataforma.pesquisas.interfaces.dto;

/**
 * @author Lucas.Rodrigues
 *
 */
public class FonteDadosDto {

	private String api;
	private String valor;
	private String descricao;

	public FonteDadosDto(String api, String valor, String descricao) {
		this.api = api;
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getApi() {
		return api;
	}

	public String getvalor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}
}
