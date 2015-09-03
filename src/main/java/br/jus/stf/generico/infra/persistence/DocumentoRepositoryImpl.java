package br.jus.stf.generico.infra.persistence;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.generico.domain.model.Documento;
import br.jus.stf.generico.domain.model.DocumentoRepository;
import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class DocumentoRepositoryImpl extends SimpleJpaRepository<Documento, Long> implements DocumentoRepository {

	@Autowired
	public DocumentoRepositoryImpl(EntityManager entityManager) {
		super(Documento.class, entityManager);
	}

	@Override
	public Documento find(DocumentoId documentoId) {
		return super.findOne(documentoId.toLong());
	}

	@Override
	public void store(Documento documento) {
		super.save(documento);
	}

}
