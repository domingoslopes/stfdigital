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
	public Processo find(ProcessoId processoId);
	
	public List<Processo> find(Specification<Processo> specification);

	public List<Processo> findAll();

	/**
	 * 
	 * @param processo
	 */
	public void store(Processo processo);

	public Long proximoNumero(final ClasseId classe);

}