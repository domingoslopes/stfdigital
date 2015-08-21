package br.jus.stf.generico.domain.model;

import java.util.List;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:34:02
 */
public interface ClasseRepository {

	public List<Classe> findAll();

}