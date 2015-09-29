package br.jus.stf.processamentoinicial.autuacao.infra;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.documentos.interfaces.DocumentoRestResource;
import br.jus.stf.plataforma.documentos.interfaces.commands.SalvarDocumentosCommand;
import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * @author Lucas Rodrigues
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
public class DocumentoRestAdapter implements DocumentoAdapter {

	@Autowired
	private DocumentoRestResource documentoRestResource;

	@Override
	public Set<DocumentoId> salvarDocumentos(List<DocumentoTemporarioId> documentosTemporarios) {
		SalvarDocumentosCommand command = new SalvarDocumentosCommand();
		command.setDocumentos(documentosTemporarios);
		return documentoRestResource.salvar(command).stream()
				.map(dto -> new DocumentoId(dto.getDocumentoId()))
				.collect(Collectors.toCollection(
						() -> new LinkedHashSet<DocumentoId>()));
	}

}
