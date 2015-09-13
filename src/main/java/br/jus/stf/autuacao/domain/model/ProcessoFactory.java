package br.jus.stf.autuacao.domain.model;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PeticaoId;
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
	
	public static Processo criarProcesso(ClasseId classe, MinistroId relator, PeticaoId peticao,
			Set<ParteProcesso> partes, Set<DocumentoId> documentos) {
		ProcessoId id = processoRepository.nextId();
		Long numero = processoRepository.proximoNumero(classe);
		
		return new Processo(id, classe, numero, relator, peticao, partes, documentos);
	}
	
}
