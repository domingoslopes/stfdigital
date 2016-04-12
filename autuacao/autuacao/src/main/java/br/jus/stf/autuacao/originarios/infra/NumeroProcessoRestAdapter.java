package br.jus.stf.autuacao.originarios.infra;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
	
    @Autowired
    private DiscoveryClient discoveryClient;
    
	@Override
	public Identificacao novoNumeroProcesso(String classe) {
		URI servicesUri = discoveryClient.getInstances("services").get(0).getUri();
		
		URI uri = UriComponentsBuilder.fromUri(servicesUri).path("/api/identificadores").queryParam("categoria", classe).build().toUri();
		
		IdentificacaoDto identificacao = new RestTemplate().getForObject(uri, IdentificacaoDto.class);
    	
		return new Identificacao(identificacao.getCategoria(), identificacao.getNumero());
	}
	
}
