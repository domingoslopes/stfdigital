package br.jus.stf.plataforma.documentos.infra.persistence;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.documentos.domain.model.Documento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.shared.DocumentoId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * @author Lucas Rodrigues
 */
@Repository
public class DocumentoRepositoryImpl extends SimpleJpaRepository<Documento, DocumentoId> implements DocumentoRepository {

	private static Map<String, DocumentoTemporario> TEMP_FILES = new HashMap<String, DocumentoTemporario>();
	
	private EntityManager entityManager;
	
	@Autowired
	private GridFsOperations gridOperations;
	
	@Autowired
	public DocumentoRepositoryImpl(EntityManager entityManager) {
		super(Documento.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public InputStream loadStream(DocumentoId documentoId) {
		try {
			Documento documento = Optional.ofNullable(super.findOne(documentoId))
					.orElseThrow(IllegalArgumentException::new);

			GridFSDBFile gridFile = gridOperations.findOne(new Query()
					.addCriteria(Criteria.where("_id").is(new ObjectId(documento.numeroConteudo()))));
			
			return gridFile.getInputStream();
		} catch (Throwable t) {
			throw new RuntimeException("Não foi possível carregar o stream do arquivo ", t);
		}
	}

	@Override
	public DocumentoId save(String documentoTemporario) {
		DocumentoTemporario docTemp = TEMP_FILES.get(documentoTemporario);
		InputStream stream = docTemp.stream();
		DocumentoId id = nextId();
		DBObject metaData = new BasicDBObject();
		
		metaData.put("seq_documento", id.toLong());
		metaData.put("nom_arquivo", documentoTemporario);
		metaData.put("num_tamanho_bytes", docTemp.tamanho());
		
		String numeroConteudo = gridOperations.store(stream, documentoTemporario, docTemp.contentType(), metaData).getId().toString();
		Documento documento = super.save(new Documento(id, numeroConteudo));

		entityManager.flush();
		IOUtils.closeQuietly(stream);
		TEMP_FILES.remove(documentoTemporario);
		docTemp.delete();
		return documento.id();
	}
	
	@Override
	public void delete(Documento documento) {
		String numeroConteudo = documento.numeroConteudo();
		
		super.delete(documento);
		gridOperations.delete(new Query()
		.addCriteria(Criteria.where("_id").is(new ObjectId(numeroConteudo))));
	}
	
	@Override
	public String storeTemp(DocumentoTemporario documentoTemporario) {
		TEMP_FILES.put(documentoTemporario.tempId(), documentoTemporario);
		return documentoTemporario.tempId();
	}

	@Override
	public DocumentoId nextId() {
		javax.persistence.Query query = entityManager.createNativeQuery("SELECT CORPORATIVO.SEQ_DOCUMENTO.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new DocumentoId(sequencial);
	}

}
