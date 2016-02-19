package br.jus.stf.processamentoinicial.autuacao.interfaces.facade;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.application.PeticaoApplicationService;
import br.jus.stf.processamentoinicial.autuacao.domain.DevolucaoTemplateService;
import br.jus.stf.processamentoinicial.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaTemporaria;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoDtoAssembler;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PeticaoStatusDto;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoWorkflow;


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
	
	@Autowired
	private DevolucaoTemplateService devolucaoTemplateService;
	
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
				.map(peca -> criarPecaTemporaria(peca))
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
	 * @param tipoProcesso Define se o processo físico é recursal ou originário
	 * @return Id da petição gerado automaticamente.
	 */
	public Long registrar(Integer volumes, Integer apensos, String formaRecebimento, String numeroSedex, String tipoProcesso) {
		FormaRecebimento forma = FormaRecebimento.valueOf(formaRecebimento.toUpperCase());
		TipoProcesso tipo = TipoProcesso.valueOf(tipoProcesso.toUpperCase());
		PeticaoFisica peticao = peticaoApplicationService.registrar(volumes, apensos, forma, numeroSedex, tipo);
		return peticao.id().toLong();
	}
	
	/**
	 * Realiza a preautuação de uma petição física.
	 * 
	 * @param peticaoId Id da petição física.
	 * @param classeId Classe processual sugerida.
	 * @param peticaoValida indica se a petição está correta ou indevida
	 * @param motivoDevolucao o motivo da devolução, no caso de petições indevidas
	 * @param preferenciasId Preferências.
	 */
	public void preautuar(Long peticaoId, String classeId, boolean peticaoValida, String motivoDevolucao, List<Long> preferenciasId) {
		ClasseId classe = new ClasseId(classeId);
		PeticaoFisica peticao = carregarPeticao(peticaoId);
		Set<PreferenciaId> preferencias = criarPreferencias(preferenciasId);
		peticaoApplicationService.preautuar(peticao, classe, peticaoValida, motivoDevolucao, preferencias);
	}
	
	/**
	 * Realiza a autuação de uma petição.
	 * @param peticaoId Id da petição.
	 * @param classeId Classe processual atribuída à petição.
	 * @param peticaoValida Indica se uma petição é valida ou inválida.
	 * @param motivoRejeicao Descrição do motivo da rejeição da petição.
	 * @param partesPoloAtivo Partes do polo ativo
	 * @param partesPoloPassivo Partes do polo passivo
	 */
	public void autuar(Long peticaoId, String classeId, boolean peticaoValida, String motivoRejeicao, List<String> partesPoloAtivo, List<String> partesPoloPassivo) {
		ClasseId classe = new ClasseId(classeId);
		Peticao peticao = carregarPeticao(peticaoId);
		
		peticaoApplicationService.autuar(peticao, classe, peticaoValida, motivoRejeicao, partesPoloAtivo, partesPoloPassivo);
	}

	/**
	 * Devolve uma petição.
	 * @param peticaoId Id da petição.
	 */
	public void devolver(Long peticaoId, String documento, TipoDevolucao tipoDevolucao, Long numero) {
		Peticao peticao = carregarPeticao(peticaoId);
		peticaoApplicationService.prepararDevolucao(peticao, documento, tipoDevolucao, numero);
	}
	
	public void assinarDevolucao(Long peticaoId, String documentoId) {
		Peticao peticao = carregarPeticao(peticaoId);
		peticaoApplicationService.assinarDevolucao(peticao, new DocumentoTemporarioId(documentoId));
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
	 * Consulta uma lista de petições
	 * 
	 * @param peticaoIds
	 * @return dto
	 */
	public List<PeticaoDto> consultar(List<Long> peticaoIds){
		return Optional.ofNullable(peticaoIds)
				.map(ids -> ids.stream().map(id -> consultar(id)).collect(Collectors.toList()))
				.orElse(null);
	}
	
	/**
	 * Retorna os possíveis status que podem ser atribuídos a uma petição.
	 * 
	 * @return Lista de status.
	 */
	public List<PeticaoStatusDto> consultarStatusPeticao() {
		
		List<PeticaoStatusDto> statusPeticao = new ArrayList<PeticaoStatusDto>();
		
		for (PeticaoStatus p : PeticaoStatus.values()) {
			statusPeticao.add(new PeticaoStatusDto(p.name(), p.descricao()));
		}
		
		return statusPeticao.stream().sorted((s1, s2) -> s1.getNome().compareTo(s2.getNome())).collect(Collectors.toList());
	}
	
	/**
	 * Retorna os processo de workflow associado a uma petição
	 * 
	 * @param peticaoId
	 * @return
	 */
	public Long consultarProcessoWorkflow(Long peticaoId) {
		Peticao peticao = carregarPeticao(peticaoId);
		Optional<ProcessoWorkflow> processoWorkflow = peticao.processosWorkflow().stream().findFirst();
		if (processoWorkflow.isPresent()) {
			return processoWorkflow.get().id().toLong();
		}
		return null;
	}
	
	public InputStream consultarTemplateDevolucao(String tipoDevolucao, String extensao) throws Exception {
		TipoDevolucao tipo = TipoDevolucao.valueOf(tipoDevolucao.toUpperCase());
	    return devolucaoTemplateService.carregarTemplate(tipo, extensao);
    }
	
	/**
	 * Retorna o tipo de processo da petição
	 * 
	 * @param peticaoId
	 * @return ORIGINARIO ou RECURSAL
	 */
	public String tipoProcesso(String peticaoId) {
		Peticao peticao = carregarPeticao(Long.valueOf(peticaoId));
		return peticao.tipoProcesso().name();
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
	
	/**
	 * Carrega as preferências
	 * 
     * @param preferencias
     * @return 
     */
    private Set<PreferenciaId> criarPreferencias(List<Long> preferenciasId) {
		return Optional.ofNullable(preferenciasId)
		        .map(ids -> ids.stream().map(id -> new PreferenciaId(id)).collect(Collectors.toSet()))
		        .orElse(Collections.emptySet());
    }
    
	/**
	 * Cria uma peça temporária
	 * 
     * @param peca
     * @return
     */
    private PecaTemporaria criarPecaTemporaria(Map<String, String> peca) {
	    DocumentoTemporarioId documentoTemporario = new DocumentoTemporarioId(peca.get("documentoTemporario"));
	    TipoPeca tipo = peticaoRepository.findOneTipoPeca(Long.valueOf(peca.get("tipo")));
	    Visibilidade visibilidade = Visibilidade.valueOf(Optional.ofNullable(peca.get("visibilidade")).orElse(Visibilidade.PUBLICO.toString()));
	    Situacao situacao = Situacao.valueOf(Optional.ofNullable(peca.get("situacao")).orElse(Situacao.JUNTADA.toString()));
	    return new PecaTemporaria(documentoTemporario, tipo, tipo.nome(), visibilidade, situacao);
    }
	
}
