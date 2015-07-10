package br.jus.stf.autuacao.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class PeticaoService {

	@Autowired
	private ProcessoAdapter processoAdapter;

	@Autowired
	private TarefaAdapter tarefaAdapter;

	public String registrar(String tipoRecebimento) {
		return processoAdapter.iniciar("autuarOriginarios");
	}

	public void preautuar(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

	public void autuar(String idPeticao, String classificacao) {
		if (classificacao.equals("-1")) {
			tarefaAdapter.sinalizar("Petição Inválida");
		} else {
			tarefaAdapter.completar(idPeticao);
		}
	}

	public void distribuir(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

	public void devolver(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

}
