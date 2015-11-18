package br.jus.stf.processamentoinicial.autuacao.interfaces.facade;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationService;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaTemporaria;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDtoAssembler;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.PeticaoId;


/**
 * Classe responsável pela criação de objetos relacionados ao peticionamento.
 * 
 * @author Anderson.Araujo
 *
 * @version 1.0.0
 * @since 04.09.2015
 */
@Component
public class PeticaoServiceFacade {

	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private PeticaoDtoAssembler peticaoDtoAssembler;
	
	/**
	 * Inicia o processo de peticionamento de uma petição eletônica.
	 * @param classeSugerida Sugestão de classe processual.
	 * @param poloAtivo Lista contendo os ids das partes do polo ativo.
	 * @param poloPassivo Lista contendo os ids das partes do polo passivo.
	 * @param pecas Lista contendo os ids das pecas da petição eletrônica.
	 * @param orgaoId o identificador do órgão para o qual o seu representante está peticionando 
	 * @return Id da petição gerado automaticamente.
	 */
	public Long peticionar(String classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<Map<String, String>> pecas, Long orgaoId) {
		ClasseId classe = new ClasseId(classeSugerida);
		List<PecaTemporaria> pecasTemporarias = pecas.stream()
				.map(peca -> {
					DocumentoTemporarioId documentoTemporario = new DocumentoTemporarioId(peca.get("documentoTemporario"));
					TipoPeca tipo = peticaoRepository.findOneTipoPeca(Long.valueOf(peca.get("tipo")));
					return new PecaTemporaria(documentoTemporario, tipo, tipo.nome());
				})
				.collect(Collectors.toList());
		
		PeticaoEletronica peticao = peticaoApplicationService.peticionar(classe, poloAtivo, poloPassivo, pecasTemporarias, Optional.ofNullable(orgaoId));
		return peticao.id().toLong();
	}
	
	/**
	 * Inicia o processo de peticionamento de uma petição física.
	 * @param volumes Quantidade de volumes recebidos.
	 * @param apensos Quantidades de apensos recebidos.
	 * @param formaRecebimento Forma de recebimento da petição física.
	 * @param numeroSedex Nº do Sedex, caso a forma de recebimento seja Sedex.
	 * @return Id da petição gerado automaticamente.
	 */
	public Long registrar(Integer volumes, Integer apensos, String formaRecebimento, String numeroSedex) {
		FormaRecebimento forma = FormaRecebimento.valueOf(formaRecebimento.toUpperCase());
		PeticaoFisica peticao = peticaoApplicationService.registrar(volumes, apensos, forma, numeroSedex);
		return peticao.id().toLong();
	}
	
	/**
	 * Realiza a preautuação de uma petição física.
	 * 
	 * @param peticaoId Id da petição física.
	 * @param classeId Classe processual sugerida.
	 * @param peticaoValida indica se a petição está correta ou indevida 
	 * @param motivoDevolucao o motivo da devolução, no caso de petições indevidas 
	 */
	public void preautuar(Long peticaoId, String classeId, boolean peticaoValida) {
		ClasseId classe = new ClasseId(classeId);
		PeticaoFisica peticao = carregarPeticao(peticaoId);
		peticaoApplicationService.preautuar(peticao, classe, peticaoValida);
	}
	
	/**
	 * Realiza a autuação de uma petição.
	 * @param peticaoId Id da petição.
	 * @param classe Classe processual atribuída à petição.
	 * @param peticaoValida Indica se uma petição é valida ou inválida.
	 * @param motivoRejeicao Descrição do motivo da rejeição da petição.
	 */
	public void autuar(Long peticaoId, String classeId, boolean peticaoValida, String motivoRejeicao) {
		ClasseId classe = new ClasseId(classeId);
		Peticao peticao = carregarPeticao(peticaoId);
		peticaoApplicationService.autuar(peticao, classe, peticaoValida, motivoRejeicao);
	}

	/**
	 * Devolve uma petição.
	 * @param peticaoId Id da petição.
	 */
	public void devolver(Long peticaoId, TipoDevolucao tipoDevolucao, Long numero) {
		Peticao peticao = carregarPeticao(peticaoId);
		peticaoApplicationService.devolver(peticao, tipoDevolucao, numero);
	}
	
	/**
	 * Consulta uma petição
	 * 
	 * @param peticaoId
	 * @return dto
	 */
	public PeticaoDto consultar(Long peticaoId){
		Peticao peticao = carregarPeticao(peticaoId);
		if (peticao.isEletronica()) {
			return peticaoDtoAssembler.toDto((PeticaoEletronica) peticao);
		} else {
			return peticaoDtoAssembler.toDto((PeticaoFisica) peticao);
		}
		
	}

	/**
	 * Carrega um petição pelo id, ou lança um exceção se não existir
	 * 
	 * @param peticaoId
	 * @return a petição
	 */
	@SuppressWarnings("unchecked")
	private <T> T carregarPeticao(Long peticaoId) {
		PeticaoId id = new PeticaoId(peticaoId);
		return (T) Optional.ofNullable(peticaoRepository.findOne(id))
					.orElseThrow(IllegalArgumentException::new);
	}
}
