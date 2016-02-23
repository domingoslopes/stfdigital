package br.jus.stf.processamentoinicial.recursaledistribuicao.application;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.TeseAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Distribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.MotivoInaptidao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.MotivoInaptidaoProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.PecaProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.PecaProcessual;
import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Situacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.Visibilidade;
import br.jus.stf.shared.AssuntoId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.TeseId;

/**
 * @author Lucas Rodrigues
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.09.2015
 */
@Component
@Transactional
public class ProcessoApplicationService {

	@Autowired
	@Qualifier("processoTarefaRestAdapter")
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	@Autowired
	private TeseAdapter teseAdapter;
	
	@Autowired
	DocumentoAdapter documentoAdapter;
	
	/**
	 * Cadastra um processo recursal.
	 * 
	 */
	public ProcessoRecursal cadastrarRecursal(PeticaoId peticaoId) {
		Processo processo = ProcessoFactory.criar(peticaoId);
		if(processo  instanceof ProcessoRecursal) {
			processoRepository.save(processo);
			//TODO Lançar evento de cadastro de recursal
			return (ProcessoRecursal) processo;
		}
		throw new IllegalArgumentException("Petição inválida para criação de processo recursal!");
	}
	
	/**
	 * Realiza a autuação de processo recursal.
	 * 
	 * @param idProcesso ID do processo recursal a ser autuado.
	 * @param assuntos Lista de assuntos do processo.
	 * @param poloAtivo Lista de partes do polo ativo.
	 * @param poloPassivo Lista de partes do polo passivo.
	 */
	public void autuar(Long idProcesso, List<String> assuntos, List<String> poloAtivo, List<String> poloPassivo) {
		
		ProcessoId processoId = new ProcessoId(idProcesso);
		Set<AssuntoId> assuntosProcesso = assuntos.stream().map(id -> new AssuntoId(id)).collect(Collectors.toSet());
		
		Set<ParteProcesso> partesPoloAtivo = carregarPartes(poloAtivo, TipoPolo.POLO_ATIVO);
		Set<ParteProcesso> partesPoloPassivo = carregarPartes(poloPassivo, TipoPolo.POLO_PASSIVO);
		
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(processoId);
		processo.autuar(assuntosProcesso, partesPoloAtivo, partesPoloPassivo);
		processoRepository.save(processo);
		tarefaAdapter.completarAutuacao(processo);
	}
	
	/**
	 * Realiza análise de pressupostos formais para processo recursal.
	 * 
	 * @param processoId Processo recursal a ser autuado
	 * @param classificacao Classificação (APTO/INAPTO)
	 * @param motivos Lista de motivos de inaptidão do processo
	 * @param observacao Observação da análise
	 */
	public void analisarPressupostosFormais(Long idProcesso, String classificacao, Map<Long, String> motivos, String observacao, boolean revisao) {
		ProcessoId processoId = new ProcessoId(idProcesso);
		Classificacao classif = Classificacao.valueOf(classificacao.toUpperCase());
		Set<MotivoInaptidaoProcesso> motivosInaptidao = new LinkedHashSet<MotivoInaptidaoProcesso>(); 
		motivos.forEach((k, v) -> motivosInaptidao.add(new MotivoInaptidaoProcesso(recuperarMotivoInaptidao(k), v)));
		
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(processoId);
		processo.analisarPressupostosFormais(classif, observacao, motivosInaptidao);
		processoRepository.save(processo);
		if (revisao) {
			tarefaAdapter.completarRevisaoProcessoInapto(processo, classif);
		} else {
			tarefaAdapter.completarAnalisePressupostosFormais(processo, classif);
		}
	}
	
	/**
	 * Realiza análise de pressupostos formais para processo recursal.
	 * 
	 * @param idProcesso Processo recursal a ser autuado
	 * @param assuntos Assuntos do processo
	 * @param teses Teses de repercussão geral
	 * @param observacao Observação da análise
	 */
	public void analisarRepercussaoGeral(Long idProcesso, List<String> assuntos, List<Long> teses, String obsAnalise, boolean revisao) {
		
		ProcessoId processoId = new ProcessoId(idProcesso);
		Set<AssuntoId> assuntosProcesso = assuntos.stream().map(id -> new AssuntoId(id)).collect(Collectors.toSet());
		Set<TeseId> tesesProcesso = teses.stream().map(id -> new TeseId(id)).collect(Collectors.toSet());
		
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(processoId);
		processo.analisarRepercussaoGeral(assuntosProcesso, tesesProcesso, obsAnalise);
		processoRepository.save(processo);
		
		if (revisao) {
			tarefaAdapter.completarRevisaoRepercussaoGeral(processo);
		} else {
			tarefaAdapter.completarAnaliseRepercussaoGeral(processo, teseAdapter.contemTeseDoTipoRepercursaoGeral(tesesProcesso));
		}
	}
	
	/**
	 * Distribui um processo para um Ministro Relator.
	 * 
	 * @param parametroDistribuicao Parametrização para a distribuição
	 * @return processo
	 */
	public Processo distribuir(ParametroDistribuicao parametroDistribuicao) {
		Distribuicao distribuicao = Distribuicao.criar(parametroDistribuicao);
		Processo processo = distribuicao.executar();
		processo = processoRepository.saveAndFlush(processo);
		tarefaAdapter.completarDistribuicao(processo);
		processoApplicationEvent.processoDistribuido(processo);
		
		return processo;
	}

	private MotivoInaptidao recuperarMotivoInaptidao(Long id){
		return processoRepository.findOneMotivoInaptidao(id);
	}
	
	/**
	 * Cria as partes do processo recursal.
	 * 
	 * @param partes Conjunto de partes.
	 * @param polo Lista de partes.
	 * @param tipo Tipo de polo.
	 * 
	 */
	private Set<ParteProcesso> carregarPartes(List<String> polo, TipoPolo tipo) {
		Set<PessoaId> pessoas = pessoaAdapter.cadastrarPessoas(polo);
		return pessoas.stream().map(pessoa -> new ParteProcesso(pessoa, tipo)).collect(Collectors.toSet());
	}
	
	/**
	 * Insere as peças do processo
	 * @param processo Dados do processo.
	 * @param pecas Lista de peças.
	 */
	public void inserirPecas(Processo processo, List<PecaProcessual> pecas){		
		TipoPeca tipoPeca = null;
 		PecaProcesso peca = null;
		Visibilidade visibilidade = null;
		Situacao situacao = null;
 		
 		for (PecaProcessual pecaProcessual : pecas){
 			DocumentoId documentoId = documentoAdapter.salvar(new DocumentoTemporarioId(pecaProcessual.getDocumentoTemporarioId()));
 			tipoPeca = processoRepository.findOneTipoPeca(pecaProcessual.getTipoPecaId());

			visibilidade = Visibilidade.valueOf(pecaProcessual.getVisibilidade());
			situacao = Situacao.PENDENTE_JUNTADA;
			peca = new PecaProcesso(documentoId, tipoPeca, pecaProcessual.getDescricao(), visibilidade, situacao);
 
			processo.adicionarPeca(peca);
 		}
 		
 		processoRepository.save(processo);
	}
	
	/**
	 * Exclui as peças de um processo.
	 * @param processo Dados do processo.
	 * @param pecas Lista de peças para serem excluídas.
	 */
	public void excluirPecas(Processo processo, List<Peca> pecas){
		pecas.forEach(peca -> processo.removerPeca(peca));
		processoRepository.save(processo);
	}
	
	/**
	 * Atribui uma lista de peças com nova organização para um processo.
	 * 
	 * @param processo
	 * @param pecasOrganizadas
	 * @param concluirTarefa
	 * @return
	 */
	public void organizarPecas(Processo processo, List<Long> pecasOrganizadas, boolean concluirTarefa) {
		processo.organizarPecas(pecasOrganizadas);
		processoRepository.save(processo);
		
		if (concluirTarefa) {
			tarefaAdapter.completarOrganizarPecas(processo);
		}
	}
	
	/**
	 * Divide uma peça.
	 * @param processo Dados do processo.
	 * @param pecaOriginal Dados da peça original a ser dividida.
	 * @param intervalos Intervalos de páginas usados para a geração das novas peças.
	 * @param pecas Lista contendo os dados das novas peças a serem criadas.
	 */
	public void dividirPeca(Processo processo, Peca pecaOriginal, List<Range<Integer>> intervalos, List<PecaProcessual> pecas){
		List<Peca> novasPecas = new LinkedList<Peca>();
		List<DocumentoId> documentos = documentoAdapter.dividirDocumento(pecaOriginal.documento(), intervalos);
				
		for(int i = 0; i < documentos.size(); i++){
			novasPecas.add(new PecaProcesso(documentos.get(i), pecaOriginal.tipo(), pecas.get(i).getDescricao(), pecaOriginal.visibilidade(), pecaOriginal.situacao()));
		}
		
		processo.dividirPeca(pecaOriginal, novasPecas);
		processoRepository.save(processo);
	}
}
