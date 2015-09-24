package br.jus.stf.pesquisa.application;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.pesquisa.domain.model.IndexadorRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class IndexadorApplicationService {
	
	@Autowired
	private IndexadorRepository indexadorRepository;

	public void criarIndice(String indice, String configuracao, Map<String, String> mapeamentos) {
		indexadorRepository.criar(indice, configuracao, mapeamentos);
	}
	
	public void indexar(String indice, String tipo, String id, String objeto) {
		indexadorRepository.salvar(indice, tipo, id, objeto);
	}

}
