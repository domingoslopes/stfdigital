package br.jus.stf.processamentoinicial.recursaledistribuicao.application;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.TarefaAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Distribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.MotivoInaptidaoProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParametroDistribuicao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;
import br.jus.stf.shared.AssuntoId;
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
public class ProcessoApplicationService {

	@Autowired
	@Qualifier("processoTarefaRestAdapter")
	private TarefaAdapter tarefaAdapter;
	
	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private ProcessoApplicationEvent processoApplicationEvent;
	
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
	 * @param processo Processo recursal a ser autuado
	 * @param assuntos Lista de assuntos do processo
	 * @param poloAtivo Lista de partes do polo ativo
	 * @param poloPassivo Lista de partes do polo passivo
	 */
	public void autuar(ProcessoId processoId, Set<AssuntoId> assuntos, Set<ParteProcesso> poloAtivo, Set<ParteProcesso> poloPassivo) {
		// TODO: Verificar possíveis chamadas a eventos e ao workflow.
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(processoId);
		processo.autuar(assuntos, poloAtivo, poloPassivo);
		processoRepository.save(processo);
		tarefaAdapter.completarAutuacao(processo);
	}
	
	/**
	 * Realiza análise de pressupostos formais para processo recursal.
	 * 
	 * @param processoId Processo recursal a ser autuado
	 * @param classificacao Classificação (APTO/INAPTO)
	 * @param motivosInaptidao Lista de motivos de inaptidão do processo
	 * @param observacao Observação da análise
	 */
	public void analisarPressupostosFormais(ProcessoId processoId, Classificacao classificacao, Set<MotivoInaptidaoProcesso> motivosInaptidao, String observacao, boolean revisao) {
		// TODO: Verificar possíveis chamadas a eventos e ao workflow.
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(processoId);
		processo.analisarPressupostosFormais(classificacao, observacao, motivosInaptidao);
		processoRepository.save(processo);
		if (revisao) {
			tarefaAdapter.completarRevisaoProcessoInapto(processo, classificacao);
		} else {
			tarefaAdapter.completarAnalisePressupostosFormais(processo, classificacao);
		}
	}
	
	/**
	 * Realiza análise de pressupostos formais para processo recursal.
	 * 
	 * @param processoId Processo recursal a ser autuado
	 * @param assuntos Assuntos do processo
	 * @param teses Teses de repercussão geral
	 * @param observacao Observação da análise
	 */
	public void analisarRepercussaoGeral(ProcessoId processoId, Set<AssuntoId> assuntos, Set<TeseId> teses, boolean revisao) {
		// TODO: Verificar possíveis chamadas a eventos e ao workflow.
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findOne(processoId);
		processo.analisarRepercussaoGeral(assuntos, teses);
		processoRepository.save(processo);
		if (revisao) {
			tarefaAdapter.completarRevisaoRepercussaoGeral(processo);
		} else {
			tarefaAdapter.completarAnaliseRepercussaoGeral(processo, !teses.isEmpty());
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
		processoRepository.save(processo);
		tarefaAdapter.completarDistribuicao(processo);
		processoApplicationEvent.processoDistribuido(processo);
		
		return processo;
	}

}
