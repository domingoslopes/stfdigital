package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.ProcessoId;

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
	public <T extends Processo> T save(Processo processo);
	
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
	
	/**
	 * Recupera a lista de processos cuja PessoaId atua como parte
	 * 
	 * @param pessoaId
	 * @return a lista de processos
	 */
	public List<Processo> findByPessoaInPartes(PessoaId pessoaId);
	
	/**
	 * @param id
	 * @return
	 */
	public MotivoInaptidao findOneMotivoInaptidao(Long id);
	
	/**
	 * Recupera a lista de motivos de inaptidão
	 * 
	 * @return a lista de motivos de inaptidão
	 */
	public List<MotivoInaptidao> findAllMotivoInaptidao();

}