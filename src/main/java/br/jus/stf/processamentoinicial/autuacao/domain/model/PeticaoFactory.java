package br.jus.stf.processamentoinicial.autuacao.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator.OfInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;
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
	 * @param pecas
	 * @param orgaoId o ID do órgão do representante
	 * @return a petição
	 */
	public PeticaoEletronica criarPeticaoEletronica(ClasseId classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<PecaTemporaria> pecasTemporarias, Optional<Long> orgaoId, TipoProcesso tipoProcesso) {
		
		Set<PartePeticao> partes = new HashSet<PartePeticao>();
		adicionarPartes(partes, poloAtivo, TipoPolo.POLO_ATIVO);
		adicionarPartes(partes, poloPassivo, TipoPolo.POLO_PASSIVO);
		
		Set<PecaPeticao> pecas = adicionarPecas(pecasTemporarias);
		
		PeticaoId id = peticaoRepository.nextId();
		Long numero = peticaoRepository.nextNumero();
		String usuarioCadastramento = SecurityContextUtil.getUser().getUsername();
		
		if (orgaoId.isPresent()) {
			Orgao orgao = peticaoRepository.findOneOrgao(orgaoId.get());
			
			return new PeticaoEletronica(id, numero, usuarioCadastramento, classeSugerida, partes, pecas, orgao, tipoProcesso);
		}
		
		return new PeticaoEletronica(id, numero, usuarioCadastramento, classeSugerida, partes, pecas, tipoProcesso);
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
	public PeticaoFisica criarPeticaoFisica(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex, TipoProcesso tipoProcesso) {
		PeticaoId id = peticaoRepository.nextId();
		Long numero = peticaoRepository.nextNumero();
		String usuarioCadastramento = SecurityContextUtil.getUser().getUsername();
		
		return new PeticaoFisica(id, numero, usuarioCadastramento, volumes, apensos, formaRecebimento, numeroSedex, tipoProcesso);
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
	 * Salva as peças temporárias e recupera os ids
	 * 
	 * @param pecas
	 * @return
	 */
	private Set<PecaPeticao> adicionarPecas(List<PecaTemporaria> pecas) {
		List<DocumentoTemporarioId> documentosTemporarios = pecas.stream()
				.map(peca -> peca.documentoTemporario())
				.collect(Collectors.toList());
		Set<DocumentoId> documentos = documentoAdapter.salvar(documentosTemporarios);
		OfInt linhas = IntStream.range(0, documentos.size()).iterator();

		return documentos.stream()
				.map(documento -> {
					int index = linhas.nextInt();
					TipoPeca tipo = pecas.get(index).tipo();
					String descricao = pecas.get(index).descricao();
					return new PecaPeticao(documento, tipo, descricao);
				}).collect(Collectors.toSet());
	}
	
}
