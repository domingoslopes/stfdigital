package br.jus.stf.autuacao.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.GenericoAdapter;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PessoaId;
import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * Fábrica de petições
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PeticaoFactory {
	
	@Autowired
	private GenericoAdapter genericoAdapter;
	
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
			List<String> poloAtivo, List<String> poloPassivo, Set<DocumentoId> documentos) {
		
		Set<PartePeticao> partes = new HashSet<PartePeticao>();
		adicionarPartes(partes, poloAtivo, TipoPolo.POLO_ATIVO);
		adicionarPartes(partes, poloPassivo, TipoPolo.POLO_PASSIVO);
		
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
	public PeticaoFisica criarPeticaoFisica(Integer volumes, Integer apensos, 
			FormaRecebimento formaRecebimento, String numeroSedex) {
		PeticaoId id = peticaoRepository.nextId();
		Long numero = peticaoRepository.nextNumero();
		
		return new PeticaoFisica(id, numero, volumes, apensos, formaRecebimento, numeroSedex);
	}
	
	/**
	 * Adiciona as partes da petiçao
	 * 
	 * @param partes
	 * @param polo
	 * @param tipo
	 */
	private void adicionarPartes(Set<PartePeticao> partes, List<String> polo, TipoPolo tipo) {
		Set<PessoaId> pessoas = genericoAdapter.cadastrarPessoas(polo);
		pessoas.forEach(pessoa -> partes.add(new PartePeticao(pessoa, tipo)));
	}
	
}
