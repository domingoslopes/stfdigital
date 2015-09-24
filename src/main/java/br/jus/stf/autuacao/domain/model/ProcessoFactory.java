package br.jus.stf.autuacao.domain.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.ProcessoId;

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
	
	public static Processo criarProcesso(ClasseId classe, MinistroId relator, Peticao peticao) {
		Set<ParteProcesso> partesProcesso = new HashSet<ParteProcesso>();
		partesProcesso.addAll(coletarPartes(peticao.partesPoloAtivo()));
		partesProcesso.addAll(coletarPartes(peticao.partesPoloPassivo()));
		
		ProcessoId id = processoRepository.nextId();
		Long numero = processoRepository.nextNumero(classe);
		
		return new Processo(id, classe, numero, relator, peticao.id(), partesProcesso, peticao.documentos());
	}

	private static Set<ParteProcesso> coletarPartes(Set<Parte> partesPeticao) {
		return partesPeticao.stream()
			.map(parte -> new ParteProcesso(parte.pessoaId(), parte.polo()))
			.collect(Collectors.toSet());
	}
	
}
