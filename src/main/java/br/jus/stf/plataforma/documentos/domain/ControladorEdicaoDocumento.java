package br.jus.stf.plataforma.documentos.domain;

import br.jus.stf.shared.DocumentoId;

public interface ControladorEdicaoDocumento {

	String gerarEdicao(DocumentoId id);
	
	void excluirEdicao(String numeroEdicao);
	
	String recuperarEdicao(DocumentoId id);

}
