package br.jus.stf.generico.infra.persistence;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.generico.domain.model.Documento;
import br.jus.stf.generico.domain.model.DocumentoRepository;
import br.jus.stf.generico.domain.model.DocumentoTemporario;
import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class DocumentoRepositoryImpl extends SimpleJpaRepository<Documento, DocumentoId> implements DocumentoRepository {

	private EntityManager entityManager;
	
	@Autowired
	public DocumentoRepositoryImpl(EntityManager entityManager) {
		super(Documento.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public InputStream loadStream(DocumentoId documentoId) {
		try {
			return Optional.ofNullable(super.findOne(documentoId))
				.orElseThrow(IllegalArgumentException::new)
				.conteudo().getBinaryStream();
		} catch(Throwable t) {
			throw new RuntimeException("Não foi possível carregar o stream do arquivo ", t);
		}
	}

	@Override
	public DocumentoId save(DocumentoTemporario documentoTemporario) {
		Session session = (Session) entityManager.getDelegate();
		InputStream stream = documentoTemporario.stream();
		long tamanho = documentoTemporario.tamanho();
		
		Blob conteudo = session.getLobHelper().createBlob(stream, tamanho);
				
		Documento documento = super.save(new Documento(conteudo));
		return documento.id();
	}

}
