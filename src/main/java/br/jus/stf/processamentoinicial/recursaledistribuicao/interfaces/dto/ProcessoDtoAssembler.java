package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.jurisprudencia.controletese.domain.model.TeseRepository;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDtoAssembler;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDtoAssembler;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoInaptidaoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoInaptidaoDtoAssembler;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TeseDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TeseDtoAssembler;

@Component
public class ProcessoDtoAssembler {
	
	@Autowired
	private PecaDtoAssembler pecaDtoAssembler;
	
	@Autowired
	AssuntoRepository assuntoRepository;
	
	@Autowired
	AssuntoDtoAssembler assuntoDtoAssembler;
	
	@Autowired
	TeseRepository teseRepository;
	
	@Autowired
	TeseDtoAssembler teseDtoAssembler;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	MotivoInaptidaoDtoAssembler motivoInaptidaoDtoAssembler;
		
	/**
	 * Constrói um dto de processo
	 * 
	 * @param processo
	 * @return
	 */
	public ProcessoDto toDto(Processo processo){
		ProcessoRecursal processoRecursal = (ProcessoRecursal)processo;
		Long id = processoRecursal.id().toLong();
		String classe = processoRecursal.classe().toString();
		Long numero = processoRecursal.numero();
		Long relator = Optional.ofNullable(processoRecursal.relator()).map(m -> m.toLong()).orElse(null);
		List<Long> partesPoloAtivo = new LinkedList<Long>();
		List<Long> partesPoloPassivo = new LinkedList<Long>();
		List<PecaDto> pecas = new LinkedList<PecaDto>();
		Map<String, List<Long>> partes = new HashMap<String, List<Long>>();
		String situacao = processoRecursal.situacao().descricao();
		List<Long> preferencias = new LinkedList<Long>();
		String identificacao = processoRecursal.identificacao();
		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
		List<TeseDto> teses = new ArrayList<TeseDto>();
		List<MotivoInaptidaoDto> motivos = new ArrayList<MotivoInaptidaoDto>();
		
		processoRecursal.partesPoloAtivo().forEach(parte -> partesPoloAtivo.add(parte.pessoaId().toLong()));
		processoRecursal.partesPoloPassivo().forEach(parte -> partesPoloPassivo.add(parte.pessoaId().toLong()));
		
		partes.put("PoloAtivo", partesPoloAtivo);
		partes.put("PoloPassivo", partesPoloPassivo);
		
		processoRecursal.pecas().forEach(peca -> pecas.add(pecaDtoAssembler.toDto(peca)));
		processoRecursal.preferencias().forEach(preferencia -> preferencias.add(preferencia.toLong()));
		
		processoRecursal.assuntos().forEach(assunto -> assuntos.add(assuntoDtoAssembler.toDto(assuntoRepository.findOne(assunto))));
		processoRecursal.teses().forEach(tese -> teses.add(teseDtoAssembler.toDto(teseRepository.findOne(tese))));
				
		return new ProcessoDto(id, classe, numero, relator, partes, pecas, situacao, preferencias, identificacao, assuntos, teses);
	}

}
