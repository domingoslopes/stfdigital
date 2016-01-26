package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDtoAssembler;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;

@Component
public class ProcessoDtoAssembler {
	
	@Autowired
	private PecaDtoAssembler pecaDtoAssembler;
		
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
		Long relator = Optional.ofNullable(processo.relator()).map(m -> m.toLong()).get();
		List<Long> partesPoloAtivo = new LinkedList<Long>();
		List<Long> partesPoloPassivo = new LinkedList<Long>();
		List<PecaDto> pecas = new LinkedList<PecaDto>();
		Map<String, List<Long>> partes = new HashMap<String, List<Long>>();
		String situacao = processo.situacao().descricao();
		List<Long> preferencias = new LinkedList<Long>();
		
		processo.partesPoloAtivo().forEach(parte -> partesPoloAtivo.add(parte.pessoaId().toLong()));
		
		processo.partesPoloPassivo().forEach(parte -> partesPoloPassivo.add(parte.pessoaId().toLong()));
		
		partes.put("Polo Ativo", partesPoloAtivo);
		partes.put("Polo Passivo", partesPoloPassivo);
		
		processo.pecas().forEach(peca -> pecas.add(pecaDtoAssembler.toDto(peca)));
		processo.preferencias().forEach(preferencia -> preferencias.add(preferencia.toLong()));
		
		return new ProcessoDto(id, classe, numero, relator, partes, pecas, situacao, preferencias);
	}

}
