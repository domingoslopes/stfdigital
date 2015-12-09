package br.jus.stf.plataforma.dashboards.domain.model;

import br.jus.stf.plataforma.shared.security.stereotype.Resource;

/**
 * Entidade Dashlet. Representa um componente de exibição de informações para o
 * usuário.
 * 
 * @author Tomas.Godoi
 *
 */
public class Dashlet extends Resource<Dashlet> {

	private String nome;

	public Dashlet(String nome) {
		super(nome);
		
		this.nome = nome;
	}

	public String nome() {
		return nome;
	}

}
