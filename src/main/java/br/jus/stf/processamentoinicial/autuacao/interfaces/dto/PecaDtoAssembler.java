package br.jus.stf.processamentoinicial.autuacao.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Peca;

/**
 * Contém a lógica de transformação da peça armazenada na base de dados
 * para a peça que será apresentada ao usuário.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M3
 * @since 02.10.2015
 */
@Component
public class PecaDtoAssembler {

	/**
	 * Transfere o conteúdo apresentável de uma {@link Peca} para uma {@link PecaDto}.
	 * 
	 * @param peca a peça de origem
	 * @return o dto resultado
	 */
	public PecaDto toDto(Peca peca) {
		return new PecaDto(peca.tipo().toLong(), peca.tipo().nome(), peca.descricao(), peca.documento().toLong());
	}
	
}
