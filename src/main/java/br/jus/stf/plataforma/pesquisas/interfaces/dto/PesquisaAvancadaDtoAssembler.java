package br.jus.stf.plataforma.pesquisas.interfaces.dto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Criterio;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.FonteDados;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancada;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;


/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PesquisaAvancadaDtoAssembler implements InitializingBean {
	
	private ObjectReader objectReader;
	
	/**
	 * Cria um dto da pesquisa avançada
	 * 
	 * @param pesquisa
	 * @return
	 */
	public PesquisaAvancadaDto toDto(PesquisaAvancada pesquisa) {
		JsonNode json = null;
		if (!StringUtils.isBlank(pesquisa.consulta())) {
	        try {
		        json = objectReader.readTree(pesquisa.consulta());
	        } catch (Exception e) {
	        	throw new IllegalArgumentException(e);
	        }
		}
		return new PesquisaAvancadaDto(pesquisa.id().toLong(), pesquisa.nome(), json, pesquisa.indices());
	}
	
	/**
	 * Cria um dto do critério de pesquisa avançada 
	 * 
	 * @param criterio
	 * @return
	 */
	public CriterioDto toDto(Criterio criterio) {
		return new CriterioDto(criterio.campo(), criterio.descricao(), criterio.indice(), criterio.tipo().name(),
		        toDto(criterio.fonteDados()));
	}

	@Override
    public void afterPropertiesSet() throws Exception {
		objectReader = new ObjectMapper().reader();
    }
	
	/**
	 * Cria um dto de fonte de dados de critério
	 * 
	 * @param fonteDados
	 * @return
	 */
	private FonteDadosDto toDto(FonteDados fonteDados) {
		if (fonteDados == null) {
			return null; 
		}
		return new FonteDadosDto(fonteDados.api(), fonteDados.valor(), fonteDados.descricao());
	}
	
}
