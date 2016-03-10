package br.jus.stf.plataforma.pesquisas.application;

import java.util.Optional;

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
	
	public void salvar(String nome, String consulta, String[] indices) {
		
		PesquisaAvancadaId id = pesquisaAvancadaRepository.nextId();
		PesquisaAvancada pesquisa = new PesquisaAvancada(id, nome, consulta, indices);
		pesquisaAvancadaRepository.save(pesquisa);
	}
	
	public void alterar(Long pesquisaId, String nome, String consulta, String[] indices) {
		
		PesquisaAvancadaId id = new PesquisaAvancadaId(pesquisaId);
		Optional.ofNullable(pesquisaAvancadaRepository.findOne(id))
			.ifPresent(pesquisa -> {
				pesquisa.alterar(nome, consulta, indices);
				pesquisaAvancadaRepository.save(pesquisa);
			});
	}
	
}
