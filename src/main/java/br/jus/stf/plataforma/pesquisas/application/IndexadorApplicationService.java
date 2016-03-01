package br.jus.stf.plataforma.pesquisas.application;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.Documento;
import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.DocumentoRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.Indice;
import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.SubDocumento;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class IndexadorApplicationService {
	
	@Autowired
	private DocumentoRepository documentoRepository;

	/**
	 * Cria um índice
	 * 
	 * @param nome
	 * @param configuracao
	 * @param mapeamentos
	 */
	public void criarIndice(String nome, String configuracao, Map<String, String> mapeamentos) {
		Indice indice = new Indice(nome, configuracao, mapeamentos);
		documentoRepository.criar(indice);
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
		documentoRepository.salvar(documento);
	}

	/**
	 * Atualiza parcialmente um documento
	 * 
	 * @param id
	 * @param indice
	 * @param objeto
	 */
	public void atualizar(String id, String tipo, Indice indice, String objeto) {
		Documento documento = new Documento(id, tipo, indice, objeto);
		documentoRepository.atualizar(documento);
	}

	public void atualizarColecao(String id, String tipo, Indice indice, String campoColecao, String expressaoId,
			Object idItem, String objeto) {
		SubDocumento subDocumento = new SubDocumento(id, tipo, indice, campoColecao, expressaoId, idItem, objeto);
		documentoRepository.atualizarColecao(subDocumento);
	}

}
