package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.TipoDocumentoId;

/**
 * Repositório de Tipo de Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
public interface TipoModeloRepository extends Repository<TipoModelo, TipoDocumentoId> {

	List<TipoModelo> findAll();

	TipoModelo findOne(TipoDocumentoId id);

}
