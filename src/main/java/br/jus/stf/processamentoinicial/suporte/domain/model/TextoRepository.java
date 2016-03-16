package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import br.jus.stf.shared.TextoId;

/**
 * Repositório da entidade Texto.
 * 
 * @author Tomas.Godoi
 *
 */
public interface TextoRepository {

	TextoId nextId();

	<T extends Texto> T save(T texto);

	Texto findOne(TextoId id);
	
	List<Texto> findAll();
	
}
