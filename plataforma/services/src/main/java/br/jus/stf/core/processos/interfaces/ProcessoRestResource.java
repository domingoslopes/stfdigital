package br.jus.stf.core.processos.interfaces;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.core.processos.application.PesquisarProcessosQuery;
import br.jus.stf.core.processos.application.ProcessoFinder;
import br.jus.stf.core.processos.domain.Processo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.03.2016
 */
@RestController
@RequestMapping("/api/processos")
public class ProcessoRestResource {
	
	@Autowired
	private ProcessoFinder finder;

	@RequestMapping(method = RequestMethod.POST)
	public List<Processo> pesquisar(@RequestBody @Valid PesquisarProcessosQuery query, BindingResult result) {
		if (isBlank(query.getProtocolo()) && isBlank(query.getParte())) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		
		return finder.execute(query);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<ProcessoDto> pesquisar() {
		List<ProcessoDto> dtos = new LinkedList<>();
		
		List<Processo> processos = finder.execute(new PesquisarProcessosQuery());
		
		for (Processo processo : processos) {
			dtos.add(new ProcessoDto(processo.getProtocolo(), "Publicado", "Joaquim Barbosa", new Date()));
		}
		
		return dtos;
	}
	
}
