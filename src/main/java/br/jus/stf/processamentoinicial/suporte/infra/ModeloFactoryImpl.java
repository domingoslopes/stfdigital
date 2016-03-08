package br.jus.stf.processamentoinicial.suporte.infra;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.suporte.domain.ModeloFactory;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.TipoModeloId;

/**
 * Implementação do Modelo Factory
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class ModeloFactoryImpl implements ModeloFactory {

	@Autowired
	private DocumentoAdapter documentoAdapter;

	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public DocumentoId criarDocumentoModeloPadrao(TipoModeloId tipoModelo, String nome) {
		Resource resource = resourceLoader.getResource("classpath:templates/modelo/padrao-brasao.docx");
		DocumentoTemporarioId documentoTemporarioId;
		try {
			documentoTemporarioId = documentoAdapter.upload(tipoModelo.toLong() + nome + ".docx",
			        IOUtils.toByteArray(resource.getInputStream()));
			return documentoAdapter.salvar(documentoTemporarioId);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gerar documento do modelo", e);
		}
	}

}
