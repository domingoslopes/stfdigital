package br.jus.stf.plataforma.dashboards.domain.model;

import br.jus.stf.plataforma.shared.security.resource.Resource;
import br.jus.stf.plataforma.shared.security.resource.ResourceType;

/**
 * Entidade Dashlet. Representa um componente de exibição de informações para o
 * usuário.
 * 
 * @author Tomas.Godoi
 *
 */
public class Dashlet extends Resource {

	private String nome;

	public Dashlet(String nome) {
		super(nome, ResourceType.DASHLET);
		
		this.nome = nome;
	}

	public String nome() {
		return nome;
	}

}
