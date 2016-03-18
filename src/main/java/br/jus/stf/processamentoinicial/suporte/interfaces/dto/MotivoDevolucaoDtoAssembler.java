package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;

/**
 * Cria objetos MotivoDevolucaoDto.
 * 
 * @author Anderson.Araujo
 * @since 15.03.2016
 *
 */
@Component
public class MotivoDevolucaoDtoAssembler {
	public MotivoDevolucaoDto toDto(MotivoDevolucao motivo) {
		Validate.notNull(motivo);
		return new MotivoDevolucaoDto(motivo.toLong(), motivo.descricao());
	}
}
