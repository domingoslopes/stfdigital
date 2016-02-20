package br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
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
	
	/**
	 * Recupera o processo criado a partir da petição informada
	 * 
	 * @param peticaoId
	 * @return processo
	 */
	public Processo findByPeticao(PeticaoId peticaoId);
	
	/**
	 * Recupera um tipo de peça de acordo com o id informado.
	 * 
	 * @param id Id do tipo de peça.
	 * @return Dados do tipo de peça.
	 */
	public TipoPeca findOneTipoPeca(Long id);
	
	/**
	 * Recupera uma peça.
	 * @param id Id da peça.
	 * @return Dados da peça.
	 */
	public Peca findOnePeca(Long id);

	/**
	 * Salva e força o envio para o banco.
	 * 
	 * @param processo
	 * @return
	 */
	public <T extends Processo> T saveAndFlush(Processo processo);

}