package br.jus.stf.plataforma.identidades.infra.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.jus.stf.plataforma.shared.indexacao.IndexadorRestAdapter;
import br.jus.stf.plataforma.shared.util.ResourceFileUtils;

/**
 * @author Lucas.Rodrigues
 *
 */
@Configuration
public class IdentidadesConfiguration {
	
	public static final String INDICE_PESSOA = "pessoa";
	public static final String INDICE_ADVOGADO = "advogado";
	public static final String INDICE_REPRESENTANTE = "representante";
	
	private static final String ADVOGADO_RESOURCE = "/indices/identidades/advogado.json";
	private static final String PESSOA_RESOURCE = "/indices/identidades/pessoa.json";
	private static final String REPRESENTANTE_RESOURCE = "/indices/identidades/representante.json";
	
	@Autowired
	private IndexadorRestAdapter indexadorRestAdapter;
	
	@PostConstruct
	private void configure() throws Exception {
		
		criarIndice(INDICE_PESSOA, PESSOA_RESOURCE);
		criarIndice(INDICE_ADVOGADO, ADVOGADO_RESOURCE);
		criarIndice(INDICE_REPRESENTANTE, REPRESENTANTE_RESOURCE);
	}
	
	private void criarIndice(String indice, String resource) throws Exception {
		String configuracao = ResourceFileUtils.read(resource);			
		indexadorRestAdapter.criarIndice(indice, configuracao);
	}
	
}
