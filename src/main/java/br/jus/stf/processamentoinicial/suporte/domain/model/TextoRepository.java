package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.TextoId;

/**
 * Repositório da entidade Texto.
 * 
 * @author Tomas.Godoi
 *
 */
public interface TextoRepository extends Repository<Texto, TextoId> {

	TextoId nextId();

	<T extends Texto> T save(T texto);

	Texto findOne(TextoId id);
	
	List<Texto> findAll();
	
}
