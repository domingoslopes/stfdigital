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
import br.jus.stf.jurisprudencia.controletese.interfaces.dto.TeseDto;
import br.jus.stf.jurisprudencia.controletese.interfaces.dto.TeseDtoAssembler;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDtoAssembler;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.AssuntoAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.TeseAdapter;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDtoAssembler;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoInaptidaoDtoAssembler;



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
	
	@Autowired
	AssuntoAdapter assuntoAdapter;
	
	@Autowired
	TeseAdapter teseAdapter;
		
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
		Long relator = Optional.ofNullable(processo.relator()).map(m -> m.toLong()).orElse(null);
		List<Long> partesPoloAtivo = new LinkedList<Long>();
		List<Long> partesPoloPassivo = new LinkedList<Long>();
		List<PecaDto> pecas = new LinkedList<PecaDto>();
		Map<String, List<Long>> partes = new HashMap<String, List<Long>>();
		String situacao = processo.situacao().descricao();
		List<Long> preferencias = new LinkedList<Long>();
		String identificacao = processo.identificacao();
		
		processo.partesPoloAtivo().forEach(parte -> partesPoloAtivo.add(parte.pessoaId().toLong()));
		processo.partesPoloPassivo().forEach(parte -> partesPoloPassivo.add(parte.pessoaId().toLong()));
		
		partes.put("PoloAtivo", partesPoloAtivo);
		partes.put("PoloPassivo", partesPoloPassivo);
		
		processo.pecas().forEach(peca -> pecas.add(pecaDtoAssembler.toDto(peca)));
		processo.preferencias().forEach(preferencia -> preferencias.add(preferencia.toLong()));
		
		processo.pecas().forEach(peca -> pecas.add(pecaDtoAssembler.toDto(peca)));
		processo.preferencias().forEach(preferencia -> preferencias.add(preferencia.toLong()));
		
		if (processo instanceof ProcessoRecursal) {
			ProcessoRecursal processoRecursal = (ProcessoRecursal) processo;
			List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
			List<TeseDto> teses = new ArrayList<TeseDto>();
			String observacao = processoRecursal.observacaoAnalise();
			
			processoRecursal.assuntos().stream().forEach(assunto -> assuntos.add(assuntoAdapter.consultar(assunto)));
			teses = teseAdapter.consultar(processoRecursal.teses());
			
			return new ProcessoRecursalDto(id, classe, numero, relator, partes, pecas, situacao, preferencias, identificacao, teses, assuntos, observacao);
		} else {
			return new ProcessoDto(id, classe, numero, relator, partes, pecas, situacao, preferencias, identificacao);
		}
	}

}
