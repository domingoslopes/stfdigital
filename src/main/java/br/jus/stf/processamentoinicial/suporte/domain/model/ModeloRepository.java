package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import br.jus.stf.shared.ModeloId;
import br.jus.stf.shared.TipoDocumentoId;

/**
 * Repositório de Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
public interface ModeloRepository {

	ModeloId nextId();

	<T extends Modelo> T save(T modelo);

	Modelo findOne(ModeloId id);
	
	List<Modelo> findAll();
}
