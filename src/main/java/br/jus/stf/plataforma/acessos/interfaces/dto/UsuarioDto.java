/**
 * 
 */
package br.jus.stf.plataforma.acessos.interfaces.dto;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe responsável por transportar os dados de usuário do back-end para o front-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.11.2015
 *
 */
@ApiModel(value = "Classe responsável por transportar os dados de usuário do back-end para o front-end.")
public class UsuarioDto {

	@ApiModelProperty(value="Nome do usuário.")
	private String nome;
	
	@ApiModelProperty(value="Setor de lotação do usuário.")
	private String setorLotacao;
	
	@ApiModelProperty(value="Lista de papéis associados ao usuário.")
	private List<PapelDto> papeis;
	
	public UsuarioDto(String nome, String setorLotacao, List<PapelDto> papeis) {
		this.nome = nome;
		this.setorLotacao = setorLotacao;
		this.papeis = papeis;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSetorLotacao() {
		return this.setorLotacao;
	}

	public void setSetorLotacao(String setorLotacao) {
		this.setorLotacao = setorLotacao;
	}

	public List<PapelDto> getPapeis() {
		return this.papeis;
	}

	public void setPapeis(List<PapelDto> papeis) {
		this.papeis = papeis;
	}
}
