package br.jus.stf.generico.application;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.generico.domain.model.DocumentoRepository;
import br.jus.stf.generico.domain.model.DocumentoTemporario;
import br.jus.stf.generico.domain.model.Pessoa;
import br.jus.stf.generico.domain.model.PessoaRepository;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PessoaId;

/**
 * Aplicação genérica que deverá ser desmembrada
 * 
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class GenericoApplicationService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	/**
	 * Cadastra pessoas
	 * 
	 * @param pessoas
	 * @return
	 */
	public Set<PessoaId> cadastrarPessoas(List<Pessoa> pessoas) {
		Set<PessoaId> pessoasCadastradas = new LinkedHashSet<PessoaId>();
		pessoas.stream().forEach(pessoa -> pessoasCadastradas.add(pessoaRepository.save(pessoa)));
		return pessoasCadastradas;
	}
	
	/**
	 * Salva os documentos temporários no repositório
	 * 
	 * @param documentosTemporarios
	 * @return
	 */
	public Set<DocumentoId> salvarDocumentos(List<String> documentosTemporarios) {
		Set<DocumentoId> documentosSalvos = new LinkedHashSet<DocumentoId>();
		documentosTemporarios.forEach(docTemp -> documentosSalvos.add(documentoRepository.save(docTemp)));
		return documentosSalvos;
	}
	
	/**
	 * @param documentoTemporario
	 * @return
	 */
	public String salvarDocumentoTemporario(DocumentoTemporario documentoTemporario) {
		return documentoRepository.storeTemp(documentoTemporario);
	}
	
}
