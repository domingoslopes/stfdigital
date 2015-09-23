package br.jus.stf.autuacao.interfaces.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDto;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDtoAssembler;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PeticaoId;


/**
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
	
	@Autowired
	private ProcessoDtoAssembler processoDtoAssembler;
	
	/**
	 * Inicia o processo de peticionamento de uma petição eletônica.
	 * @param classeSugerida Sugestão de classe processual.
	 * @param poloAtivo Lista contendo os ids das partes do polo ativo.
	 * @param poloPassivo Lista contendo os ids das partes do polo passivo.
	 * @param documentos Lista contendo os ids dos documentos da petição eletrônica.
	 * @return Id da petição gerado automaticamente.
	 */
	public Long peticionar(String classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<String> documentos) {
		ClasseId classe = new ClasseId(classeSugerida);

		PeticaoEletronica peticao = peticaoApplicationService.peticionar(classe, poloAtivo, poloPassivo, documentos);

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
	 * @param peticaoId Id da petição física.
	 * @param classeId Classe processual sugerida.
	 */
	public void preautuar(Long peticaoId, String classeId) {
		ClasseId classe = new ClasseId(classeId);
		PeticaoId id = new PeticaoId(peticaoId);
		PeticaoFisica peticao = Optional.ofNullable(peticaoRepository.findOne(id, PeticaoFisica.class))
									.orElseThrow(IllegalArgumentException::new);
		peticaoApplicationService.preautuar(peticao, classe);
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
	public void devolver(Long peticaoId) {
		Peticao peticao = carregarPeticao(peticaoId);
		peticaoApplicationService.devolver(peticao);
	}
	
	/**
	 * Distribui um processo para um ministro relator.
	 * @param peticaoId Id da petição.
	 * @param ministroId Id do Ministro Relator.
	 */
	public ProcessoDto distribuir(Long peticaoId, Long ministroId) {
		MinistroId ministro = new MinistroId(ministroId);
		Peticao peticao = carregarPeticao(peticaoId);

		Processo processo = peticaoApplicationService.distribuir(peticao, ministro);
		return processoDtoAssembler.toDto(processo);
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
	private Peticao carregarPeticao(Long peticaoId) {
		PeticaoId id = new PeticaoId(peticaoId);
		return Optional.ofNullable(peticaoRepository.findOne(id))
				.orElseThrow(IllegalArgumentException::new);
	}
	
}
