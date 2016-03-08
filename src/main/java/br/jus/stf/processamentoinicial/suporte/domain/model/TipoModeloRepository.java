package br.jus.stf.processamentoinicial.suporte.domain.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.jus.stf.shared.TipoModeloId;

/**
 * Repositório de Tipo de Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
public interface TipoModeloRepository extends Repository<TipoModelo, TipoModeloId> {

	List<TipoModelo> findAll();

	TipoModelo findOne(TipoModeloId id);

}
