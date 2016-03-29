package br.jus.stf.plataforma.pesquisas.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancada;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancadaId;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancadaRepository;


/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class PesquisaApplicationService {
	
	@Autowired
	private PesquisaAvancadaRepository pesquisaAvancadaRepository;
	
	public void salvar(Long pesquisaId, String nome, String tipo, String consulta, String[] indices) {
		
		PesquisaAvancada pesquisa = null;
		
		if (pesquisaId != null) {
			pesquisa = alterar(pesquisaId, nome, tipo, consulta, indices);
		} else {
			pesquisa = criar(nome, tipo, consulta, indices);
		}
		pesquisaAvancadaRepository.save(pesquisa);
	}
	
	/**
	 * Exclui uma pesquisa avançada
	 * 
	 * @param pesquisaId
	 */
	public void excluir(Long pesquisaId) {
		PesquisaAvancadaId id = new PesquisaAvancadaId(pesquisaId);
		pesquisaAvancadaRepository.delete(id);
    }
	
	private PesquisaAvancada alterar(Long pesquisaId, String nome, String tipo, String consulta, String[] indices) {
		PesquisaAvancadaId id = new PesquisaAvancadaId(pesquisaId);
		PesquisaAvancada pesquisa = pesquisaAvancadaRepository.findOne(id);
		pesquisa.alterar(nome, tipo, consulta, indices);
		return pesquisa;
	}
	
	private PesquisaAvancada criar(String nome, String tipo, String consulta, String[] indices) {
		PesquisaAvancadaId id = pesquisaAvancadaRepository.nextId();
		return new PesquisaAvancada(id, nome, tipo, consulta, indices);
	}
	
}
