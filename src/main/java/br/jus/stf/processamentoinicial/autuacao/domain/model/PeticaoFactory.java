package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;

/**
 * Fábrica de petições
 * 
 * @author Lucas Rodrigues
 */
@Component
public class PeticaoFactory {
	
	@Autowired
	private DocumentoAdapter documentoAdapter;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	@Autowired
	private PeticaoRepository peticaoRepository;
	
	/**
	 * Cria uma petição eletrônica
	 * 
	 * @param classeSugerida
	 * @param poloAtivo
	 * @param poloPassivo
	 * @param documentos
	 * @return a petição
	 */
	public PeticaoEletronica criarPeticaoEletronica(ClasseId classeSugerida, 
			List<String> poloAtivo, List<String> poloPassivo, List<String> documentosTemporarios) {
		
		Set<PartePeticao> partes = new HashSet<PartePeticao>();
		adicionarPartes(partes, poloAtivo, TipoPolo.POLO_ATIVO);
		adicionarPartes(partes, poloPassivo, TipoPolo.POLO_PASSIVO);
		
		Set<DocumentoId> documentos = adicionarDocumentos(documentosTemporarios);
		
		PeticaoId id = peticaoRepository.nextId();
		Long numero = peticaoRepository.nextNumero();
		
		return new PeticaoEletronica(id, numero, classeSugerida, partes, documentos);
	}

	/**
	 * Cria uma petição física
	 * 
	 * @param volumes
	 * @param apensos
	 * @param formaRecebimento
	 * @param numeroSedex
	 * @return a petição
	 */
	public PeticaoFisica criarPeticaoFisica(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex) {
		PeticaoId id = peticaoRepository.nextId();
		Long numero = peticaoRepository.nextNumero();
		
		return new PeticaoFisica(id, numero, volumes, apensos, formaRecebimento, numeroSedex);
	}
	
	/**
	 * Salva as pessoa, recupera os ids e adiciona as partes da petiçao
	 * 
	 * @param partes
	 * @param polo
	 * @param tipo
	 */
	private void adicionarPartes(Set<PartePeticao> partes, List<String> polo, TipoPolo tipo) {
		Set<PessoaId> pessoas = pessoaAdapter.cadastrarPessoas(polo);
		pessoas.forEach(pessoa -> partes.add(new PartePeticao(pessoa, tipo)));
	}
	
	/**
	 * Salva os documentos temporários e recupera os ids
	 * 
	 * @param documentos
	 * @return
	 */
	private Set<DocumentoId> adicionarDocumentos(List<String> documentos){
		return documentoAdapter.salvarDocumentos(documentos);
	}
	
}
