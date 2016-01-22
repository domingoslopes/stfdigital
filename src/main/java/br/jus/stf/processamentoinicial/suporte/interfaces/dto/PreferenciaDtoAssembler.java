/**
 * 
 */
package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.suporte.domain.model.Preferencia;

/**
 * @author Anderson.Araujo
 *
 */
@Component
public class PreferenciaDtoAssembler {
	public PreferenciaDto toDto(Preferencia preferencia) {
		Validate.notNull(preferencia);
		return new PreferenciaDto(preferencia.toLong().toLong().longValue( ), preferencia.descricao());
	}
}
