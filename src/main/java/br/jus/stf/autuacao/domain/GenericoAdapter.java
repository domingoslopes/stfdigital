package br.jus.stf.autuacao.domain;

import java.util.List;
import java.util.Set;

import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PessoaId;

/**
 * Interface para comunicação com o domínio genérico
 * 
 * @author Lucas.Rodrigues
 *
 */
public interface GenericoAdapter {

	public Set<DocumentoId> salvarDocumentos(List<String> documentosTemporarios);
	
	public Set<PessoaId> cadastrarPessoas(List<String> pessoas);
	
}
