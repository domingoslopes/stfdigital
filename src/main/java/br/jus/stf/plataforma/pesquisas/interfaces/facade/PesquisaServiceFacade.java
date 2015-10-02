package br.jus.stf.plataforma.pesquisas.interfaces.facade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.pesquisas.domain.model.PesquisaRepository;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.PesquisaDtoAssembler;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.ResultadoPesquisaDto;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PesquisaServiceFacade {

	@Autowired
	private PesquisaRepository pesquisaRepository;
	
	@Autowired
	private PesquisaDtoAssembler pesquisaDtoAssembler;
	
	/**
	 * Pesquisa objetos indexados
	 * 
	 * @param indices
	 * @param filtros
	 * @param ordenador
	 * @return uma lista de dtos com o resultado
	 */
	public List<ResultadoPesquisaDto> pesquisar(String[] indices, Map<String, String> filtros, String[] campos, String ordenador) {
		return pesquisaDtoAssembler.toDto(pesquisaRepository.pesquisar(indices, filtros, campos, ordenador));
	}

	
	
}
