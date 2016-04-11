package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.workflow.domain.model.ProcessoWokflowRepository;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.PeticaoAdapter;
import br.jus.stf.processamentoinicial.suporte.domain.model.MeioTramitacao;
import br.jus.stf.processamentoinicial.suporte.domain.model.Parte;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.Sigilo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoId;

/**
 * Fábrica de processo com chamada estática para diminuir o acoplamento com {@link Peticao}
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ProcessoFactory {
	
	private static ProcessoRepository processoRepository;
	private static PeticaoAdapter peticaoAdapter;
	private static ProcessoWokflowRepository workflowRepository;
	
	@Autowired
	public ProcessoFactory(ProcessoRepository processoRepository, PeticaoAdapter peticaoAdapter, ProcessoWokflowRepository workflowRepository) {
		ProcessoFactory.processoRepository = processoRepository;
		ProcessoFactory.peticaoAdapter = peticaoAdapter;
		ProcessoFactory.workflowRepository = workflowRepository;
	}
	
	public static Processo criar(PeticaoId peticaoId) {
		Peticao peticao = peticaoAdapter.consultar(peticaoId.toLong());
		Processo processo = null;
		
		if (TipoProcesso.ORIGINARIO.equals(peticao.tipoProcesso())) {
			processo = criarProcessoOriginario(peticao);
		} else if (TipoProcesso.RECURSAL.equals(peticao.tipoProcesso())) {
			processo = criarProcessoRecursal(peticao);
			((ProcessoRecursal)processo).associarProcessoWorkflow(workflowRepository.findOne(peticao.processoWorkflowId()));
		}
	    return processo;
    }
	
	/**
	 * Cria um processo recursal para envio 
	 * 
	 * @param classeProcessual
	 * @param preferencias
	 * @param meioTramitacao
	 * @param sigilo
	 * @param quantidadeRecursos
	 * @return
	 */
	public static ProcessoRecursal criarProcessoRecursal(ClasseId classeProcessual, Set<PreferenciaId> preferencias, MeioTramitacao meioTramitacao, Sigilo sigilo, Long quantidadeRecursos) {
		ProcessoId id = processoRepository.nextId();
		Long numero = processoRepository.nextNumero(classeProcessual);
		ProcessoRecursal processo = new ProcessoRecursal(id, classeProcessual, numero, preferencias, new Date(),
				meioTramitacao, sigilo, quantidadeRecursos);
		return processo;
    }
		
	/**
	 * Cria um processo originário
	 * 
	 * @param peticao
	 * @return processo originário
	 */
	private static ProcessoOriginario criarProcessoOriginario(Peticao peticao) {
		Set<ParteProcesso> partesProcesso = new HashSet<ParteProcesso>();
		partesProcesso.addAll(coletarPartes(peticao.partes()));
		
		Set<PecaProcesso> pecasProcesso = new HashSet<PecaProcesso>();
		pecasProcesso.addAll(coletarPecas(peticao.pecas()));
		
		ProcessoId id = processoRepository.nextId();
		Long numero = processoRepository.nextNumero(peticao.classeProcessual());
		
		ProcessoOriginario processo = new ProcessoOriginario(id, peticao.classeProcessual(), numero, peticao.id(),
				partesProcesso, pecasProcesso, peticao.preferencias(), peticao.dataCadastramento(), peticao.meioTramitacao(),
				peticao.sigilo());
		
		processo.atribuirDataAutuacao(peticao.dataAutuacao());
		return processo;
	}
	
	/**
	 * Cria um processo recursal a partir de uma petição
	 * 
	 * @param peticao
	 * @return processo recursal
	 */
	private static ProcessoRecursal criarProcessoRecursal(Peticao peticao) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findByPeticao(peticao.id());
		
		if (processo == null){
			ProcessoId id = processoRepository.nextId();
			Long numero = processoRepository.nextNumero(peticao.classeProcessual());
			processo = new ProcessoRecursal(id, peticao.classeProcessual(), numero, peticao.id(),
					peticao.preferencias(), peticao.dataCadastramento(), peticao.meioTramitacao(),
					peticao.sigilo());
		}
		
		return processo;
	}

	private static Set<ParteProcesso> coletarPartes(Set<Parte> partesPeticao) {
		return partesPeticao.stream()
			.map(parte -> new ParteProcesso(parte.pessoaId(), parte.polo()))
			.collect(Collectors.toSet());
	}
	
	private static List<PecaProcesso> coletarPecas(List<Peca> pecasPeticao) {
		return pecasPeticao.stream()
			.map(peca -> new PecaProcesso(peca.documento(), peca.tipo(), peca.descricao(), peca.visibilidade(), peca.situacao()))
			.collect(Collectors.toList());
	}
	
}
