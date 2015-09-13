package br.jus.stf.autuacao.interfaces.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.domain.model.Peticao;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDto;
import br.jus.stf.autuacao.interfaces.dto.PeticaoDtoAssembler;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDistribuidoDto;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.PeticaoId;


/**
 * @author Anderson.Araujo
 *
 * @version 1.0.0
 * @since 04.09.2015
 */
@Component
public class PeticaoServiceFacade {

	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

	@Autowired
	private PeticaoRepository peticaoRepository;
	
	@Autowired
	private PeticaoDtoAssembler peticaoDtoAssembler;
	
	/**
	 * Inicia o processo de peticionamento de uma petição eletônica.
	 * @param classeSugerida Sugestão de classe processual.
	 * @param poloAtivo Lista contendo os ids das partes do polo ativo.
	 * @param poloPassivo Lista contendo os ids das partes do polo passivo.
	 * @param documentos Lista contendo os ids dos documentos da petição eletrônica.
	 * @return Id da petição gerado automaticamente.
	 */
	public Long peticionar(String classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<String> documentos) {
		ClasseId classe = new ClasseId(classeSugerida);
				
		Peticao peticao = peticaoApplicationService.peticionar(classe, poloAtivo, poloPassivo, documentos);
		return peticao.id().toLong();
	}
	
	/**
	 * Inicia o processo de peticionamento de uma petição física.
	 * @param volumes Quantidade de volumes recebidos.
	 * @param apensos Quantidades de apensos recebidos.
	 * @param formaRecebimento Forma de recebimento da petição física.
	 * @param numeroSedex Nº do Sedex, caso a forma de recebimento seja Sedex.
	 * @return Id da petição gerado automaticamente.
	 */
	public Long registrar(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex) {
		
		Peticao peticao = peticaoApplicationService.registrar(volumes, apensos, formaRecebimento, numeroSedex);
		return peticao.id().toLong();
	}
	
	/**
	 * Realiza a preautuação de uma petição física.
	 * @param idPeticaoFisica Id da petição física.
	 * @param classeSugerida Classe processual sugerida.
	 */
	public void preautuar(Long idPeticaoFisica, String classeSugerida) {
		PeticaoFisica peticaoFisica = (PeticaoFisica) this.peticaoRepository.findOne(new PeticaoId(idPeticaoFisica));
		peticaoFisica.preautuar(new ClasseId(classeSugerida));
		
		this.peticaoApplicationService.preautuar(peticaoFisica);
	}
	
	/**
	 * Realiza a autuação de uma petição.
	 * @param idPeticao Id da petição.
	 * @param classe Classe processual atribuída à petição.
	 * @param peticaoValida Indica se uma petição é valida ou inválida.
	 * @param motivoRejeicao Descrição do motivo da rejeição da petição.
	 */
	public void autuar(Long idPeticao, String classe, boolean peticaoValida, String motivoRejeicao) {
		
		Peticao peticao = this.peticaoRepository.findOne(new PeticaoId(idPeticao));
		
		if (peticao == null){
			throw new IllegalArgumentException("Petição não encontrada.");
		}
		
		if (peticaoValida){
			peticao.aceitar(new ClasseId(classe));
		} else {
			peticao.rejeitar(motivoRejeicao);
		}
		
		this.peticaoApplicationService.autuar(peticao, peticaoValida);
	}

	/**
	 * Devolve uma petição.
	 * @param idPeticao Id da petição.
	 */
	public void devolver(Long idPeticao) {
		Peticao peticao = this.peticaoRepository.findOne(new PeticaoId(idPeticao));
		
		if (peticao == null){
			throw new IllegalArgumentException("Petição não encontrada.");
		}
		
		this.peticaoApplicationService.devolver(peticao);
	}
	
	/**
	 * Distribui um processo para um ministro relator.
	 * @param idPeticao Id da petição.
	 * @param idMinistroRelator Id do Ministro Relator.
	 */
	public ProcessoDistribuidoDto distribuir(Long idPeticao, Long idMinistroRelator) {
		
		Peticao peticao = peticaoRepository.findOne(new PeticaoId(idPeticao));
		
		if (peticao == null){
			throw new IllegalArgumentException("Petição não encontrada.");
		}
		Processo processo = peticaoApplicationService.distribuir(peticao, new MinistroId(idMinistroRelator));
		
		return new ProcessoDistribuidoDto(processo.classe().toString(), processo.numero(), idMinistroRelator);
	}
	
	public PeticaoDto consultar(Long idPeticao){
		
		Peticao peticao = this.peticaoRepository.findOne(new PeticaoId(idPeticao));
		
		if (peticao == null){
			throw new IllegalArgumentException("Petição não encontrada.");
		}
		
		return this.peticaoDtoAssembler.toDto(peticao);
	}
	
}
