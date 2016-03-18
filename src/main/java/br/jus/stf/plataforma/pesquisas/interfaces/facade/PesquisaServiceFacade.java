package br.jus.stf.plataforma.pesquisas.interfaces.facade;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Pesquisa;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancadaId;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancadaRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaRepository;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.CriterioDto;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.PesquisaAvancadaDto;
import br.jus.stf.plataforma.pesquisas.interfaces.dto.PesquisaAvancadaDtoAssembler;
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
	private PesquisaAvancadaRepository pesquisaAvancadaRepository;
	
	@Autowired
	private ResultadoDtoAssembler resultadoDtoAssembler;
	
	@Autowired
	private PagedResourcesAssembler<ResultadoDto> paginacaoAssembler;
	
	@Autowired
	private PesquisaAvancadaDtoAssembler pesquisaAvancadaDtoAssembler;
	
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
		Long total = pesquisaRepository.count(pesquisa);
		int tamanhoPagina = total < 10 ? 10 : total.intValue(); // O tamanho da página será no mínimo 10.
		Pageable paginacao = new PageRequest(0, tamanhoPagina); // Cria uma página com o tamanho total de itens
		return resultadoDtoAssembler.toDto(pesquisaRepository.pesquisar(pesquisa, paginacao));
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

	/**
	 * Realiza uma pesquisa avançada paginada
	 * 
	 * @param consulta
	 * @param indices
	 * @param page
	 * @param size
	 * @return
	 */
	public PagedResources<Resource<ResultadoDto>> pesquisarAvancado(String consulta, String[] indices, Integer page, Integer size) {
		Pageable paginacao = new PageRequest(page, size);
		Pesquisa pesquisa = new Pesquisa(consulta, indices);
		
		List<ResultadoDto> dtos = resultadoDtoAssembler.toDto(pesquisaAvancadaRepository.executar(pesquisa, paginacao));
		Page<ResultadoDto> dtosPaginados = new PageImpl<ResultadoDto>(dtos, paginacao, dtos.size());
		
	    return paginacaoAssembler.toResource(dtosPaginados);
    }

	/**
	 * Recupera os dtos das pesquisas avançadas salvas
	 * 
	 * @return
	 */
	public List<PesquisaAvancadaDto> recuperarMinhasPesquisas() {
	    return pesquisaAvancadaRepository.listarMinhas().stream()
	    		.map(p -> pesquisaAvancadaDtoAssembler.toDto(p))
	    		.collect(Collectors.toList());
    }
	
	/**
	 * Recupera os dtos das pesquisas avançadas salvas
	 * 
	 * @return
	 */
	public PesquisaAvancadaDto recuperarPesquisa(Long pesquisaId) {
		PesquisaAvancadaId id = new PesquisaAvancadaId(pesquisaId);
	    return Optional.ofNullable(pesquisaAvancadaRepository.findOne(id))
	    		.map(p -> pesquisaAvancadaDtoAssembler.toDto(p))
	    		.orElseThrow(IllegalArgumentException::new);
    }

	/**
	 * Recupera os dtos dos critérios de pesquisa
	 * 
	 * @param indices
	 * @return
	 */
	public List<CriterioDto> listarCriterios(String[] indices) {
	    return pesquisaAvancadaRepository.listarCriterios(indices).stream()
	    		.map(c -> pesquisaAvancadaDtoAssembler.toDto(c))
	    		.collect(Collectors.toList());
    }
	
}
