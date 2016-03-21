package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.model.UnidadeFederacao;

/**
 * Cria objetos UnidadeFederacaoDto.
 * 
 * @author Anderson.Araujo
 * @since 17.03.2016
 *
 */
@Component
public class UnidadeFederacaoDtoAssembler {
	public UnidadeFederacaoDto toDo(UnidadeFederacao unidadeFederacao){
		return new UnidadeFederacaoDto(unidadeFederacao.toLong(), unidadeFederacao.nome(), unidadeFederacao.sigla());
	}
}
