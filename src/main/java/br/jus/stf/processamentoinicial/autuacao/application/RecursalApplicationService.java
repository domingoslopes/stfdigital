package br.jus.stf.processamentoinicial.autuacao.application;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.ProcessoAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.MotivoInaptidaoProcesso;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ParteProcesso;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoFactory;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.suporte.domain.model.Classificacao;
import br.jus.stf.shared.AssuntoId;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
/**
 * @author Rafael.Alencar
 *
 */
@Component
@Transactional
public class RecursalApplicationService {
	
	@Autowired
	private ProcessoAdapter processoRest;
	
	/**
	 * Realiza a preautuação de recursais.
	 * 
	 * @param peticao Dados da petição física.
	 */
	public void preautuar(PeticaoFisica peticao) {
		// TODO: Verificar restante da história para implementação.
		processoRest.cadastrarRecursal((ProcessoRecursal)ProcessoFactory.criarProcesso(peticao.classeProcessual(), null, null, null, peticao.id(), peticao.tipoProcesso(), peticao.preferencias()));
	}

	/**
	 * Realiza a autuação de processo recursal.
	 * 
	 * @param processo Processo recursal a ser autuado
	 * @param assuntos Lista de assuntos do processo
	 * @param poloAtivo Lista de partes do polo ativo
	 * @param poloPassivo Lista de partes do polo passivo
	 */
	public void autuar(ProcessoRecursal processo, Set<AssuntoId> assuntos, Set<ParteProcesso> poloAtivo, Set<ParteProcesso> poloPassivo) {
		// TODO: Verificar possíveis chamadas a eventos e ao workflow.
		processo.autuar(assuntos, poloAtivo, poloPassivo);
	}
	
	/**
	 * Realiza análise de pressupostos formais para processo recursal.
	 * 
	 * @param processo Processo recursal a ser autuado
	 * @param classificacao Classificação (APTO/INAPTO)
	 * @param motivosInaptidao Lista de motivos de inaptidão do processo
	 * @param observacao Observação da análise
	 */
	public void analisarPressupostosFormais(ProcessoRecursal processo, Classificacao classificacao, Set<MotivoInaptidaoProcesso> motivosInaptidao, String observacao) {
		// TODO: Verificar possíveis chamadas a eventos e ao workflow.
		switch (classificacao) {
			case APTO:
				processo.analisar(observacao);
				break;
			case INAPTO:
				processo.analisarInapto(motivosInaptidao, observacao);
				break;
			default:
				throw new IllegalArgumentException("Classificacao inexistente: " + classificacao);
		}
	}
	
}
