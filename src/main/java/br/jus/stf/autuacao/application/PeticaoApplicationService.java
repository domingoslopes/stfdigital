package br.jus.stf.autuacao.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.domain.PeticaoService;
import br.jus.stf.autuacao.domain.entity.ClasseProcessual;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Peticao;
import br.jus.stf.autuacao.domain.entity.Polo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.06.2015
 */
@Component
@Transactional
public class PeticaoApplicationService {

	@Autowired
	private PeticaoService peticaoService;

	/**
	 * Registra uma nova petilçao.
	 * 
	 * @param tipoRecebimento
	 * @param classe
	 * @param poloAtivo
	 * @param poloPassivo
	 * @param documentos
	 * @return
	 */
	public String registrar(String tipoRecebimento, String classeSugerida, Polo poloAtivo, Polo poloPassivo, List<Documento> documentos) {
		Peticao peticao = null;
		
		if (tipoRecebimento == null || tipoRecebimento.isEmpty())
			throw new RuntimeException("O tipo de recebimento não foi informado.");
		
		if (classeSugerida == null || classeSugerida.isEmpty())
			throw new RuntimeException("A classe processual não foi informada.");
		
		if (poloAtivo == null || poloAtivo.getPartes() == null || poloAtivo.getPartes().size() == 0)
			throw new RuntimeException("O polo ativo não foi informado.");
		
		if (poloPassivo == null || poloPassivo.getPartes() == null || poloPassivo.getPartes().size() == 0)
			throw new RuntimeException("O polo passivo não foi informado.");
		
		peticao = new Peticao();
		peticao.setClasseSugerida(new ClasseProcessual(classeSugerida));
		peticao.setPoloAtivo(poloAtivo);
		peticao.setPoloPassivo(poloPassivo);
		peticao.setDocumentos(documentos);
		
		return peticaoService.registrar(tipoRecebimento, peticao);
	}

	public void preautuar(String idPeticao) {
		peticaoService.preautuar(idPeticao);
	}

	public void autuar(String idPeticao, String classe, boolean peticaoValida) {
		
		if (idPeticao == null || idPeticao.isEmpty())
			throw new RuntimeException("O identificador da petição não foi informado.");
		
		peticaoService.autuar(idPeticao, classe, peticaoValida);
	}

	public void distribuir(String idPeticao) {
		peticaoService.distribuir(idPeticao);
	}

	public void devolver(String idPeticao) {
		peticaoService.devolver(idPeticao);
	}
}
