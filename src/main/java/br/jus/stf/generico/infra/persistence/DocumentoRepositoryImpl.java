package br.jus.stf.generico.infra.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
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

	private static Map<String, DocumentoTemporario> TEMP_FILES = new HashMap<String, DocumentoTemporario>();
	
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
	public DocumentoId save(String documentoTemporario) {
		Session session = (Session) entityManager.getDelegate();
		DocumentoTemporario docTemp = TEMP_FILES.get(documentoTemporario);
		InputStream stream = docTemp.stream();
		long tamanho = docTemp.tamanho();
		
		Blob conteudo = session.getLobHelper().createBlob(stream, tamanho);
				
		Documento documento = super.save(new Documento(conteudo));
		TEMP_FILES.remove(documentoTemporario);
		docTemp.delete();
		return documento.id();
	}
	
	@Override
	public String storeTemp(DocumentoTemporario documentoTemporario) {
		TEMP_FILES.put(documentoTemporario.tempId(), documentoTemporario);
		return documentoTemporario.tempId();
	}

}
