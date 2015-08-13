package br.jus.stf.autuacao.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.autuacao.domain.PeticaoService;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@Service
@Transactional
public class PeticaoApplicationService {

	@Autowired
	private PeticaoService peticaoService;

	public String registrar(String tipoRecebimento) {
		return peticaoService.registrar(tipoRecebimento);
	}

	public void preautuar(String idPeticao) {
		peticaoService.preautuar(idPeticao);
	}

	public void autuar(String idPeticao, boolean peticaoValida) {
		peticaoService.autuar(idPeticao, peticaoValida);
	}

	public void distribuir(String idPeticao) {
		peticaoService.distribuir(idPeticao);
	}

	public void devolver(String idPeticao) {
		peticaoService.devolver(idPeticao);
	}

}
