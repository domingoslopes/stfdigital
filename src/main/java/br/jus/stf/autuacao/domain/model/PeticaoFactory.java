package br.jus.stf.autuacao.domain.model;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class PeticaoFactory {
	
	private static PeticaoRepository peticaoRepository;
	
	@Autowired
	public PeticaoFactory(PeticaoRepository repository) {
		peticaoRepository = repository;
	}
	
	public static PeticaoEletronica criarPeticaoEletronica(ClasseId classeSugerida, 
			Set<Parte> partes, Set<DocumentoId> documentos) {
		Long numero = peticaoRepository.proximoNumero();
		return new PeticaoEletronica(numero, classeSugerida, partes, documentos);
	}
	
	public static PeticaoFisica criarPeticaoFisica(Integer volumes, Integer apensos, 
			FormaRecebimento formaRecebimento, String numeroSedex) {
		Long numero = peticaoRepository.proximoNumero();
		return new PeticaoFisica(numero, volumes, apensos, formaRecebimento, numeroSedex);
	}
	
}
