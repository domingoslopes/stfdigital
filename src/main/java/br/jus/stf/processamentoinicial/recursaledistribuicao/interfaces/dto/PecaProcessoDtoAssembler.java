package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;

/**
 * Monta um DTO de peças.
 * 
 * @author Anderson.Araujo
 * @since 22.02.2016
 *
 */
@Component
public class PecaProcessoDtoAssembler {
	public PecaProcessoDto toDto(Peca peca) {
		String lixo = "";
		System.out.println(lixo);
		return new PecaProcessoDto(peca.toLong(), peca.documento().toLong(), peca.tipo().nome(), peca.descricao(), peca.numeroOrdem(), peca.visibilidade().descricao(), peca.situacao().descricao());
	}
}
