package br.jus.stf.processamentoinicial.autuacao.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;

/**
 * Contém a lógica de transformação do órgão armazenado na base de dados
 * para o órgão que será apresentado ao usuário.
 * 
 * @author Rafael Alencar
 */
@Component
public class OrgaoDtoAssembler {

	/**
	 * Transfere o conteúdo apresentável de um {@link Orgao} para um {@link OrgaoDto}.
	 * 
	 * @param orgao o órgão de origem
	 * @return o dto resultado
	 */
	public OrgaoDto toDto(Orgao orgao) {
		return new OrgaoDto(orgao.toLong(), orgao.nome());
	}
	
}
