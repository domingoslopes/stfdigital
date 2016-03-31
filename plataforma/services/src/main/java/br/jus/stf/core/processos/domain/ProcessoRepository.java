package br.jus.stf.core.processos.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.02.2016
 */
public interface ProcessoRepository extends ElasticsearchRepository<Processo, String> {
	
	Processo findByProtocoloId(Long protocoloId);
	
	Processo findByProcessoId(String processoId);

}