package br.jus.stf.autuacao.interfaces.facade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.domain.model.PartePeticao;
import br.jus.stf.autuacao.domain.model.PeticaoEletronica;
import br.jus.stf.autuacao.domain.model.PeticaoFactory;
import br.jus.stf.autuacao.domain.model.PeticaoFisica;
import br.jus.stf.autuacao.domain.model.TipoPolo;
import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.PessoaId;


/**
 * @author anderson.araujo
 *
 * @version 1.0.0
 * @since 04.09.2015
 */
@Component
public class PeticaoServiceFacade {

	@Autowired
	private PeticaoApplicationService peticaoApplicationService;
	
	@Autowired
	private PeticaoFactory peticaoFactory;
	
	/**
	 * Inicia o processo de peticionamento de uma petição eletônica.
	 * @param classeSugerida Sugestão de classe processual.
	 * @param poloAtivo Lista contendo os ids das partes do polo ativo.
	 * @param poloPassivo Lista contendo os ids das partes do polo passivo.
	 * @param documentos Lista contendo os ids dos documentos da petição eletrônica.
	 * @return Id da petição gerado automaticamente.
	 */
	public String peticionar(String classeSugerida, List<String> poloAtivo, List<String> poloPassivo, List<String> documentos) {
		
		Set<PartePeticao> partes = new HashSet<PartePeticao>();
		Set<DocumentoId> idsDocumentos = this.adicionarDocumentos(documentos);
		ClasseId classe = new ClasseId(classeSugerida);
		
		this.adicionarPartes(poloAtivo, partes, TipoPolo.POLO_ATIVO);
		this.adicionarPartes(poloPassivo, partes, TipoPolo.POLO_PASSIVO);
		
		PeticaoEletronica peticao = this.peticaoFactory.criarPeticaoEletronica(classe, poloAtivo, poloPassivo, idsDocumentos);
				
		return peticaoApplicationService.peticionar(peticao);
	}
	
	/**
	 * Inicia o processo de peticionamento de uma petição física.
	 * @param volumes Quantidade de volumes recebidos.
	 * @param apensos Quantidades de apensos recebidos.
	 * @param formaRecebimento Forma de recebimento da petição física.
	 * @param numeroSedex Nº do Sedex, caso a forma de recebimento seja Sedex.
	 * @return Id da petição gerado automaticamente.
	 */
	public String registrar(Integer volumes, Integer apensos, FormaRecebimento formaRecebimento, String numeroSedex) {
		
		PeticaoFisica peticaoFisica = this.peticaoFactory.criarPeticaoFisica(volumes, apensos, formaRecebimento, numeroSedex);
		
		return peticaoApplicationService.registrar(peticaoFisica);
	}
	
	private void adicionarPartes(List<String> polo, Set<PartePeticao> partes, TipoPolo tipoPolo) {
		for(String p : polo){
			PessoaId id = new PessoaId(Long.parseLong(p));
			PartePeticao partePeticao = new PartePeticao(id, tipoPolo);
			partes.add(partePeticao);
		}
	}
	
	private Set<DocumentoId> adicionarDocumentos(List<String> documentos){
		Set<DocumentoId> idsDocumentos = new HashSet<DocumentoId>(); 
		
		for(String doc : documentos){
			idsDocumentos.add(new DocumentoId(Long.parseLong(doc)));
		}
		
		return idsDocumentos;
	}
}
