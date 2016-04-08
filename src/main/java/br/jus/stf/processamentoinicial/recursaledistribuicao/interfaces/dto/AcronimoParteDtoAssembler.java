package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.recursaledistribuicao.infra.GeradorAcronimo;

/**
 * Objeto usado para transportar o acrônimo do nome da parte.
 * 
 * @author anderson.araujo
 * @since 07.04.2016
 *
 */
@Component
public class AcronimoParteDtoAssembler {
	
	@Autowired
	private GeradorAcronimo geradorAcronimo;
	
	public AcronimoParteDto toDto(String parte) {
		return new AcronimoParteDto(parte, geradorAcronimo.gerar(parte));
	}
}
