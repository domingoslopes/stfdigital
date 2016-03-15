package br.jus.stf.processamentoinicial.autuacao.domain;

import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;

public interface PecaDevolucaoFromHtmlBuilder {

	byte[] build(String html, String identificacao, MotivoDevolucao motivoDevolucao, Long numero);
}
