package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Parte;
import br.jus.stf.processamentoinicial.autuacao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peca;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PecaProcesso;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.MinistroId;
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
	
	@Autowired
	public ProcessoFactory(ProcessoRepository repository) {
		processoRepository = repository;
	}
	
	public static Processo criarProcesso(ClasseId classe, MinistroId relator, Set<Parte> partes, Set<Peca> pecas, PeticaoId peticaoId) {
		Set<ParteProcesso> partesProcesso = new HashSet<ParteProcesso>();
		partesProcesso.addAll(coletarPartes(partes));
		
		Set<PecaProcesso> pecasProcesso = new HashSet<PecaProcesso>();
		pecasProcesso.addAll(coletarPecas(pecas));
		
		ProcessoId id = processoRepository.nextId();
		Long numero = processoRepository.nextNumero(classe);
		
		return new Processo(id, classe, numero, relator, peticaoId, partesProcesso, pecasProcesso, ProcessoSituacao.DISTRIBUIDO);
	}

	private static Set<ParteProcesso> coletarPartes(Set<Parte> partesPeticao) {
		return partesPeticao.stream()
			.map(parte -> new ParteProcesso(parte.pessoaId(), parte.polo()))
			.collect(Collectors.toSet());
	}
	
	private static Set<PecaProcesso> coletarPecas(Set<Peca> pecasPeticao) {
		return pecasPeticao.stream()
			.map(peca -> new PecaProcesso(peca.documento(), peca.tipo(), peca.descricao()))
			.collect(Collectors.toSet());
	}
	
}
