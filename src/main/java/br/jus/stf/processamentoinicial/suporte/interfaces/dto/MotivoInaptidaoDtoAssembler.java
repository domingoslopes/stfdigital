package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.MotivoInaptidao;

/**
 * Conversor de entidades MotivoInaptidao em DTOs.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.01.2016
 *
 */
@Component
public class MotivoInaptidaoDtoAssembler {
	public MotivoInaptidaoDto toDto(MotivoInaptidao motivo) {
		Validate.notNull(motivo);
		return new MotivoInaptidaoDto(motivo.toLong().longValue( ), motivo.descricao());
	}
}
