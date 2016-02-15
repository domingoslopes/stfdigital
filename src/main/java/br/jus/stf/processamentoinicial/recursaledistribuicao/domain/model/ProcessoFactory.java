package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.PeticaoAdapter;
import br.jus.stf.processamentoinicial.suporte.domain.model.Parte;
import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.PeticaoId;
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
	
	@Autowired
	public ProcessoFactory(ProcessoRepository processoRepository, PeticaoAdapter peticaoAdapter) {
		ProcessoFactory.processoRepository = processoRepository;
		ProcessoFactory.peticaoAdapter = peticaoAdapter;
	}
	
	public static Processo criar(PeticaoId peticaoId) {
		Peticao peticao = peticaoAdapter.consultar(peticaoId.toLong());
		Processo processo = null;
		if (TipoProcesso.ORIGINARIO.equals(peticao.tipoProcesso())) {
			processo = criarProcessoOriginario(peticao);
		} else if (TipoProcesso.RECURSAL.equals(peticao.tipoProcesso())) {
			processo = criarProcessoRecursal(peticao);
		}
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
		
		return new ProcessoOriginario(id, peticao.classeProcessual(), numero, peticao.id(), partesProcesso, pecasProcesso, peticao.preferencias());
	}
	
	/**
	 * Cria um processo recursal
	 * 
	 * @param classe
	 * @param peticaoId
	 * @param preferencias
	 * @return
	 */
	private static ProcessoRecursal criarProcessoRecursal(Peticao peticao) {
		ProcessoRecursal processo = (ProcessoRecursal) processoRepository.findByPeticao(peticao.id());
		
		if (processo == null){
			ProcessoId id = processoRepository.nextId();
			Long numero = processoRepository.nextNumero(peticao.classeProcessual());
			processo = new ProcessoRecursal(id, peticao.classeProcessual(), numero, peticao.id(), peticao.preferencias());
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
			.map(peca -> new PecaProcesso(peca.documento(), peca.tipo(), peca.descricao()))
			.collect(Collectors.toList());
	}
	
}
