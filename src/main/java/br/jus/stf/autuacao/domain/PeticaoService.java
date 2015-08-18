package br.jus.stf.autuacao.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.entity.Peticao;

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

	public String registrar(String tipoRecebimento, Peticao peticao) {
		return processoAdapter.iniciar(tipoRecebimento, peticao);
	}

	public void preautuar(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

	public void autuar(String idPeticao, String classe, boolean peticaoValida) {
		if (peticaoValida) {
			tarefaAdapter.completar(idPeticao);
			
			//Atualiza a classe da petição.
			processoAdapter.alterar(idPeticao, classe);
		} else {
			tarefaAdapter.sinalizar("Petição Inválida");
		}
	}

	public String distribuir(String idPeticao) {
		String idProcesso = "";
		
		tarefaAdapter.completar(idPeticao);
		
		return idProcesso;
	}

	public void devolver(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

}
