package br.jus.stf.plataforma.documentos.domain;

import br.jus.stf.plataforma.documentos.domain.model.Edicao;
import br.jus.stf.shared.DocumentoId;

public interface ControladorEdicaoDocumento {

	Edicao gerarEdicao(DocumentoId id);
	
	void excluirEdicao(String numeroEdicao);
	
	Edicao recuperarEdicao(DocumentoId id);
	
	void ativarEdicao(String numeroEdicao);

}
