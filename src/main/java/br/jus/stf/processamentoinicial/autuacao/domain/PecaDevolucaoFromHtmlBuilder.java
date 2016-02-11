package br.jus.stf.processamentoinicial.autuacao.domain;

import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;

public interface PecaDevolucaoFromHtmlBuilder {

	byte[] build(String html, String identificacao, TipoDevolucao tipoDevolucao, Long numero);
}
