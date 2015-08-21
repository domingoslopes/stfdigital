package br.jus.stf.autuacao.domain;

import java.util.List;

import br.jus.stf.autuacao.domain.entity.Ministro;

/**
 * @author Anderson.Araujo
 *
 */
public interface MinistroRepository {
	public List<Ministro> listar();
}
