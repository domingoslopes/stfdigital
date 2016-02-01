package br.jus.stf.processamentoinicial.recursaledistribuicao.infra;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.jurisprudencia.controletese.interfaces.TeseRestResource;
import br.jus.stf.jurisprudencia.controletese.interfaces.dto.TeseDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.TeseAdapter;
import br.jus.stf.shared.TeseId;

@Component
public class TeseRestAdapter implements TeseAdapter {

	private static final String TIPO_TESE_REPERCURSSAO_GERAL = "REPERCUSSAO_GERAL";
	@Autowired
	private TeseRestResource teseRestResource;

	/**
	 * Verifica se a lista de ids contém alguma tese do tipo repercurssão geral
	 * utilizando o endpoint Rest.
	 * 
	 * @param tesesIds
	 * @return
	 */
	@Override
	public boolean contemTeseDoTipoRepercursaoGeral(Set<TeseId> tesesIds) {
		List<TeseDto> teses = teseRestResource
		        .consultar(tesesIds.stream().map(tid -> tid.toLong()).collect(Collectors.toList()));
		return teses.stream().anyMatch(tDto -> tDto.getTipoTese().equals(TIPO_TESE_REPERCURSSAO_GERAL));
	}
}
