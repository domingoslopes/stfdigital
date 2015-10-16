package br.jus.stf.plataforma.pesquisas.interfaces.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.pesquisas.domain.model.query.Resultado;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class ResultadoDtoAssembler {

	/**
	 * Converte um resultado de pesquisa em dto
	 * 
	 * @param resultado
	 * @return uma lista de resultados
	 */
	public List<ResultadoDto> toDto(final List<Resultado> resultados) {
		return resultados.stream()
				.map(resultado -> new ResultadoDto(resultado.id(), resultado.tipo(), resultado.objeto()))
				.collect(Collectors.toList());
	}
	
}
