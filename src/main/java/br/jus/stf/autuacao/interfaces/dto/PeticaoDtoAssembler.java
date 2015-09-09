package br.jus.stf.autuacao.interfaces.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PeticaoDtoAssembler {
	/*
	public PeticaoDto toDto(Peticao peticao){
		Map<String, List<String>> partes = new HashMap<String, List<String>>();
		List<String> documentos = new LinkedList<String>();
		
		// Recupera as partes do polo ativo.
		List<String> partesPoloAtivo = new LinkedList<String>();
		for(Parte parte : peticao.getPoloAtivo().getPartes()) {
			partesPoloAtivo.add(parte.getNome());
		}
	
		// Recupera as partes do polo passivo.
		List<String> partesPoloPassivo = new LinkedList<String>();
		for(Parte parte : peticao.getPoloPassivo().getPartes()) {
			partesPoloPassivo.add(parte.getNome());
		}
		
		partes.put("poloAtivo", partesPoloAtivo);
		partes.put("poloPassivo", partesPoloPassivo);
		
		// Documentos da petição.
		for(Documento documento : peticao.getDocumentos()){
			documentos.add(documento.getDescricao());
		}
		
		// Monta o DTO.
		// [TODO] Incluir lista de documentos
		PeticaoDto dto = new PeticaoDto(peticao.getClasse().getSigla(), partes);
		
		return dto;
	}
	*/
}
