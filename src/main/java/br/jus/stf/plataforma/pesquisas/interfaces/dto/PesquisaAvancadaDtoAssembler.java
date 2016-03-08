package br.jus.stf.plataforma.pesquisas.interfaces.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

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
	
	public PesquisaAvancadaDto toDto(PesquisaAvancada pesquisa) {
		JsonNode json = null;
        try {
	        json = objectReader.readTree(pesquisa.consulta());
        } catch (Exception e) {
        	throw new IllegalArgumentException(e);
        }
		return new PesquisaAvancadaDto(pesquisa.id().toLong(), pesquisa.nome(), json, pesquisa.indices());
	}
	
	public List<PesquisaAvancadaDto> toDto(List<PesquisaAvancada> pesquisas) {
		return pesquisas.stream().map(p -> toDto(p)).collect(Collectors.toList());
	}

	@Override
    public void afterPropertiesSet() throws Exception {
		objectReader = new ObjectMapper().reader();
    }
	
}
