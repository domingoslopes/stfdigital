package br.jus.stf.autuacao.domain.model;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PeticaoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ProcessoFactory {
	
	private static ProcessoRepository processoRepository;
	
	@Autowired
	public ProcessoFactory(ProcessoRepository repository) {
		Validate.notNull(repository);
		
		processoRepository = repository;
	}
	
	public static Processo criarProcesso(ClasseId classe, MinistroId relator, PeticaoId peticao,
			Set<ParteProcesso> partes, Set<DocumentoId> documentos) {
		
		return new Processo(classe, relator, peticao, partes, documentos);
	}
	
}
