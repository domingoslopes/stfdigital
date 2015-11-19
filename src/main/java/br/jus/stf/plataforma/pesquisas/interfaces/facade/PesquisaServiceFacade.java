package br.jus.stf.plataforma.pesquisas.interfaces.facade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.pesquisas.domain.model.query.Pesquisa;
import br.jus.stf.plataforma.pesquisas.domain.model.query.PesquisaRepository;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.ResultadoDto;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.ResultadoDtoAssembler;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PesquisaServiceFacade {

	@Autowired
	private PesquisaRepository pesquisaRepository;
	
	@Autowired
	private ResultadoDtoAssembler resultadoDtoAssembler;
	
	@Autowired
	private PagedResourcesAssembler<ResultadoDto> paginacaoAssembler;
	
	/**
	 * Pesquisa objetos indexados
	 * 
	 * @param campos
	 * @param tipos
	 * @param indices
	 * @param filtros
	 * @param ordenadores
	 * @return uma lista de dtos com o resultado
	 */
	public List<ResultadoDto> pesquisar(String[] campos, String[] tipos, String[] indices, 
			Map<String, String[]> filtros, Map<String, String> ordenadores, String campoAgregacao) {
		Pesquisa pesquisa = new Pesquisa(indices, filtros)
			.comCampos(campos).comTipos(tipos).comOrdenadores(ordenadores).agregadoPeloCampo(campoAgregacao);
		return resultadoDtoAssembler.toDto(pesquisaRepository.pesquisar(pesquisa, null));
	}
	
	/**
	 * Pesquisa objetos indexados
	 * 
	 * @param campos
	 * @param tipos
	 * @param indices
	 * @param filtros
	 * @param ordenadores
	 * @param pagina
	 * @param tamanho
	 * @return uma lista de dtos com o resultado e informações de paginação
	 */
	public PagedResources<Resource<ResultadoDto>> pesquisarPaginado(String[] campos, String[] tipos, String[] indices, 
			Map<String, String[]> filtros, Map<String, String> ordenadores, Integer pagina, Integer tamanho) {
		Pageable paginacao = new PageRequest(pagina, tamanho);
		Pesquisa pesquisa = new Pesquisa(indices, filtros)
			.comCampos(campos).comTipos(tipos).comOrdenadores(ordenadores);
		List<ResultadoDto> dtos = resultadoDtoAssembler.toDto(pesquisaRepository.pesquisar(pesquisa, paginacao));
		Page<ResultadoDto> dtosPaginados = new PageImpl<ResultadoDto>(dtos, paginacao, dtos.size());
		return paginacaoAssembler.toResource(dtosPaginados);
	}

	/**
	 * Sugerir objetos indexados
	 * 
	 * @param campos
	 * @param tipos
	 * @param indices
	 * @param filtros
	 * @param ordenadores
	 * @return uma lista de dtos com o resultado
	 */
	public List<ResultadoDto> sugerir(String[] campos, String[] tipos, String[] indices, 
			Map<String, String[]> filtros, Map<String, String> ordenadores) {
		Pesquisa pesquisa = new Pesquisa(indices, filtros)
			.comCampos(campos).comTipos(tipos).comOrdenadores(ordenadores);
		return resultadoDtoAssembler.toDto(pesquisaRepository.sugerir(pesquisa));
	}
	
}
