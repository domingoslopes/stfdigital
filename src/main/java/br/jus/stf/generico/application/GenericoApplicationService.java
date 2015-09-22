package br.jus.stf.generico.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public List<Pessoa> cadastrarPessoas(List<String> pessoas) {
		return pessoas.stream()
				.map(nome -> {
					PessoaId id = pessoaRepository.nextId();
					Pessoa pessoa = new Pessoa(id, nome);
					return pessoaRepository.save(pessoa);
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * Salva os documentos temporários no repositório
	 * 
	 * @param documentosTemporarios
	 * @return
	 */
	public Map<String, DocumentoId> salvarDocumentos(List<String> documentosTemporarios) {
		return documentosTemporarios.stream()
				.collect(Collectors.toMap(docTemp -> docTemp, docTemp -> documentoRepository.save(docTemp)));
	}
	
	/**
	 * @param documentoTemporario
	 * @return
	 */
	public String salvarDocumentoTemporario(DocumentoTemporario documentoTemporario) {
		return documentoRepository.storeTemp(documentoTemporario);
	}
	
}
