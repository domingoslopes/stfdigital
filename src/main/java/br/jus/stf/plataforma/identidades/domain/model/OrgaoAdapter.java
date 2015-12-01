/**
 * 
 */
package br.jus.stf.plataforma.identidades.domain.model;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;

/**
 * Realiza as operações relacionadas a órgãos do domínio de autuação.
 * 
 * @author Anderson.Araujo
 * 
 * @since 30.11.2015
 *
 */
public interface OrgaoAdapter {

	/**
	 * Recupera os dados de um órgão de acordo com o id informado.
	 * 
	 * @param id Id do órgão.
	 * 
	 * @return Dados do órgão.
	 */
	public Orgao consultar(Long id);
}
