package br.jus.stf.plataforma.documentos.infra;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.documentos.domain.ControladorEdicaoDocumento;
import br.jus.stf.shared.DocumentoId;

@Component
public class ControladorEdicaoDocumentoImpl implements ControladorEdicaoDocumento {

	private Map<Long, String> edicoes = Collections.synchronizedMap(new HashMap<>());
	
	@Override
	public String gerarEdicao(DocumentoId id) {
		if (!edicoes.containsKey(id.toLong())) {
			edicoes.put(id.toLong(), UUID.randomUUID().toString());
		}
		return edicoes.get(id.toLong());
	}

	@Override
	public void excluirEdicao(String numeroEdicao) {
		edicoes.entrySet().removeIf(e -> e.equals(numeroEdicao));
	}

}
