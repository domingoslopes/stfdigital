package br.jus.stf.autuacao.interfaces.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Peticao;

@Component
public class PeticaoDtoAssembler {
	
	public PeticaoDto toDto(Peticao peticao){
		
		List<Long> partesPoloAtivo = new LinkedList<Long>();
		List<Long> partesPoloPassivo = new LinkedList<Long>();
		List<Long> documentos = new LinkedList<Long>();
		Map<String, List<Long>> partes = new HashMap<String, List<Long>>();
		
		peticao.partesPoloAtivo()
			.forEach(parte -> partesPoloAtivo.add(parte.pessoaId().toLong()));
		
		peticao.partesPoloPassivo()
			.forEach(parte -> partesPoloPassivo.add(parte.pessoaId().toLong()));
		
		partes.put("Polo Ativo", partesPoloAtivo);
		partes.put("Polo Passivo", partesPoloPassivo);
		
		peticao.documentos()
			.forEach(documento -> documentos.add(documento.toLong()));
				
		return new PeticaoDto(peticao.classeProcessual().toString(), partes, documentos);
	}
}
