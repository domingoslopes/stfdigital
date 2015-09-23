package br.jus.stf.autuacao.interfaces.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.shared.domain.model.ClasseId;

@Component
public class PeticaoDtoAssembler {
		
	/**
	 * Constrói um dto com as propriedades gerais da petição
	 * 
	 * @param peticao
	 * @return o dto
	 */
	public PeticaoDto toDto(PeticaoEletronica peticao) {
		return toDto(peticao, false);
	}
	
	/**
	 * Constrói um dto com as propriedades gerais e da petição física
	 * 
	 * @param peticao
	 * @return
	 */
	public PeticaoFisicaDto toDto(PeticaoFisica peticao) {
		PeticaoFisicaDto dto = (PeticaoFisicaDto) toDto(peticao, true);
		dto.setApensos(peticao.apensos());
		dto.setVolumes(peticao.volumes());
		dto.setFormaRecebimento(peticao.formaRecebimento().toString());
		dto.setNumeroSedex(peticao.numeroSedex());
		return dto;
	}
	
	private PeticaoDto toDto(Peticao peticao, boolean isFisica){
		Long id = peticao.id().toLong();
		Long numero = peticao.numero();
		Short ano = peticao.ano();
		Optional<ClasseId> classeId = Optional.ofNullable(Optional.ofNullable(peticao.classeProcessual()).orElse(peticao.classeSugerida()));
		String classe = (classeId.isPresent())? classeId.get().toString():null;
		List<Long> partesPoloAtivo = new LinkedList<Long>();
		List<Long> partesPoloPassivo = new LinkedList<Long>();
		List<Long> documentos = new LinkedList<Long>();
		Map<String, List<Long>> partes = new HashMap<String, List<Long>>();
		
		peticao.partesPoloAtivo().forEach(parte -> partesPoloAtivo.add(parte.pessoaId().toLong()));
		
		peticao.partesPoloPassivo().forEach(parte -> partesPoloPassivo.add(parte.pessoaId().toLong()));
		
		partes.put("Polo Ativo", partesPoloAtivo);
		partes.put("Polo Passivo", partesPoloPassivo);
		
		peticao.documentos().forEach(documento -> documentos.add(documento.toLong()));
		
		if (isFisica) {
			return new PeticaoFisicaDto(id, numero, ano, classe, partes, documentos);
		} else {
			return new PeticaoDto(id, numero, ano, classe, partes, documentos);
		}

	}

}
