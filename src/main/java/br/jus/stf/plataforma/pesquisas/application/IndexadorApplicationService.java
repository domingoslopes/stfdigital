package br.jus.stf.plataforma.pesquisas.application;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.pesquisas.domain.model.command.Documento;
import br.jus.stf.plataforma.pesquisas.domain.model.command.DocumentoRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.command.Indice;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class IndexadorApplicationService {
	
	@Autowired
	private DocumentoRepository indexadorRepository;

	/**
	 * Cria um índice
	 * 
	 * @param nome
	 * @param configuracao
	 * @param mapeamentos
	 */
	public void criarIndice(String nome, String configuracao, Map<String, String> mapeamentos) {
		Indice indice = new Indice(nome, configuracao, mapeamentos);
		indexadorRepository.criar(indice);
	}
	
	/**
	 * Indexa um documento
	 * 
	 * @param id
	 * @param tipo
	 * @param indice
	 * @param objeto
	 */
	public void indexar(String id, String tipo, Indice indice, String objeto) {
		Documento documento = new Documento(id, tipo, indice, objeto);
		indexadorRepository.salvar(documento);
	}

}
