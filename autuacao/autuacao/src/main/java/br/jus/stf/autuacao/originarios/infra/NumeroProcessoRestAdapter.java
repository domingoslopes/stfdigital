package br.jus.stf.autuacao.originarios.infra;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.jus.stf.autuacao.originarios.domain.NumeroProcessoAdapter;
import br.jus.stf.core.shared.processo.Identificacao;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 27.02.2016
 */
@Component
public class NumeroProcessoRestAdapter implements NumeroProcessoAdapter {
	
	@Override
	public Identificacao novoNumeroProcesso(String classe) {
		URI uri = UriComponentsBuilder.fromUriString("http://localhost:8081/api").path("/identificadores").queryParam("categoria", classe).build().toUri();
		
		IdentificacaoDto identificacao = new RestTemplate().getForObject(uri, IdentificacaoDto.class);
    	
		return new Identificacao(identificacao.getCategoria(), identificacao.getNumero());
	}
	
}
