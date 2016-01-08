package br.jus.stf.plataforma.documentos.infra.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.documentos.domain.model.Documento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoDownload;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.settings.Profiles;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * Implementação do repositório de documentos para armazenamento como blob no Oracle.
 * 
 * @author Tomas.Godoi
 */
@Repository
@Profile(Profiles.DOCUMENTO_ORACLE)
public class OracleDocumentoRepositoryImpl implements DocumentoRepository {

	@Autowired
	private DocumentoTempRepository documentoTempRepository;
	
	@Override
	public Documento findOne(DocumentoId documentoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentoDownload download(DocumentoId documentoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentoId save(DocumentoTemporarioId documentoTemporario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Documento documento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String storeTemp(DocumentoTemporario documentoTemporario) {
		return documentoTempRepository.storeTemp(documentoTemporario);
	}

	@Override
	public void removeTemp(String tempId) {
		documentoTempRepository.removeTemp(tempId);
	}

	@Override
	public DocumentoId nextId() {
		// TODO Auto-generated method stub
		return null;
	}


}
