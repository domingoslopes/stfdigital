package br.jus.stf.autuacao.infra;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.GenericoAdapter;
import br.jus.stf.generico.interfaces.DocumentoRestResource;
import br.jus.stf.generico.interfaces.PessoaRestResource;
import br.jus.stf.generico.interfaces.commands.CadastrarPessoasCommand;
import br.jus.stf.generico.interfaces.commands.SalvarDocumentosCommand;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PessoaId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class GenericoRestAdapter implements GenericoAdapter {
	
	@Autowired
	private DocumentoRestResource documentoRestResource;
	
	@Autowired
	private PessoaRestResource pessoaRestResource;

	@Override
	public Set<DocumentoId> salvarDocumentos(List<String> documentosTemporarios) {
		SalvarDocumentosCommand command = new SalvarDocumentosCommand();
		command.setDocumentos(documentosTemporarios);
		return documentoRestResource.salvar(command).stream()
				.map(dto -> new DocumentoId(dto.getDocumentoId()))
				.collect(Collectors.toCollection(
						() -> new LinkedHashSet<DocumentoId>()));
	}

	@Override
	public Set<PessoaId> cadastrarPessoas(List<String> pessoas) {
		CadastrarPessoasCommand command = new CadastrarPessoasCommand();
		command.setNomes(pessoas);
		return pessoaRestResource.cadastrar(command).stream()
				.map(dto -> new PessoaId(dto.getId()))
				.collect(Collectors.toCollection(
						() -> new LinkedHashSet<PessoaId>()));
	}

}
