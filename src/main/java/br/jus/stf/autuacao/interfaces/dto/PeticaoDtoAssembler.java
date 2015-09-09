package br.jus.stf.autuacao.interfaces.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.shared.domain.model.ClasseId;

public class PeticaoDtoAssembler {
	
	public PeticaoDto toDto(Peticao peticao){
		Map<String, List<Long>> partes = new HashMap<String, List<Long>>();
		List<Long> documentos = new LinkedList<Long>();
		
		// Recupera as partes do polo ativo.
		List<Long> partesPoloAtivo = new LinkedList<Long>();
		peticao.partesPoloAtivo()
			.forEach(parte -> partesPoloAtivo.add(parte.pessoaId().toLong()));
	
		// Recupera as partes do polo passivo.
		List<Long> partesPoloPassivo = new LinkedList<Long>();
		peticao.partesPoloPassivo()
			.forEach(parte -> partesPoloPassivo.add(parte.pessoaId().toLong()));
		
		partes.put("poloAtivo", partesPoloAtivo);
		partes.put("poloPassivo", partesPoloPassivo);
		
		// Documentos da petição.
		if (peticao.getClass().equals(PeticaoEletronica.class)) {
			((PeticaoEletronica) peticao).documentos()
			.forEach(documento -> documentos.add(documento.toLong()));	
		}
		ClasseId classe = (peticao.classeProcessual() != null) ? peticao.classeProcessual() : peticao.classeSugerida();
		
		// Monta o DTO.
		return new PeticaoDto(classe.toString(), partes, documentos);
	}
}
