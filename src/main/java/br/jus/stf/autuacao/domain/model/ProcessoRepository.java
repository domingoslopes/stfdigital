package br.jus.stf.autuacao.domain.model;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.ProcessoId;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public interface ProcessoRepository {

	/**
	 * 
	 * @param processoId
	 */
	public Processo findOne(ProcessoId processoId);
	
	/**
	 * @param specification
	 * @return a lista de processos
	 */
	public List<Processo> findAll(Specification<Processo> specification);

	/**
	 * 
	 * @param processo
	 */
	public Processo save(Processo processo);
	
	/**
	 * Gera o próximo id do processo
	 * 
	 * @return o sequencial da petição
	 */
	public ProcessoId nextId();
	
	/**
	 * Recupera o próximo número de processo de acordo com a classe
	 * 
	 * @param classe
	 * @return o número
	 */
	public Long nextNumero(ClasseId classe);

}