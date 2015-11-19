package br.jus.stf.plataforma.dashboards.domain.model;

/**
 * Entidade Dashlet. Representa um componente de exibição de informações para o
 * usuário.
 * 
 * @author Tomas.Godoi
 *
 */
public class Dashlet {

	private String nome;

	public Dashlet(String nome) {
		this.nome = nome;
	}

	public String nome() {
		return nome;
	}

}
