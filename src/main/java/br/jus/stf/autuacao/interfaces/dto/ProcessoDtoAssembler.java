package br.jus.stf.autuacao.interfaces.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Processo;

@Component
public class ProcessoDtoAssembler {
	
	/**
	 * Constrói um dto de processo
	 * 
	 * @param processo
	 * @return
	 */
	public ProcessoDto toDto(Processo processo){
		
		Long id = processo.id().toLong();
		String classe = processo.classe().toString();
		Long numero = processo.numero();
		Long relator = processo.relator().toLong();
		List<Long> partesPoloAtivo = new LinkedList<Long>();
		List<Long> partesPoloPassivo = new LinkedList<Long>();
		List<Long> pecas = new LinkedList<Long>();
		Map<String, List<Long>> partes = new HashMap<String, List<Long>>();
		
		processo.partesPoloAtivo()
			.forEach(parte -> partesPoloAtivo.add(parte.pessoaId().toLong()));
		
		processo.partesPoloPassivo()
			.forEach(parte -> partesPoloPassivo.add(parte.pessoaId().toLong()));
		
		partes.put("Polo Ativo", partesPoloAtivo);
		partes.put("Polo Passivo", partesPoloPassivo);
		
		processo.pecas()
			.forEach(peca -> pecas.add(peca.toLong()));
		
		return new ProcessoDto(id, classe, numero, relator, partes, pecas);
	}

}
