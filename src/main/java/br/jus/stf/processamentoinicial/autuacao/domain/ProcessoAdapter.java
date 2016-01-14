package br.jus.stf.processamentoinicial.autuacao.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
@Component
public interface ProcessoAdapter {

	ProcessoDto cadastrarRecursal(ProcessoRecursal processoRecursal);

}
