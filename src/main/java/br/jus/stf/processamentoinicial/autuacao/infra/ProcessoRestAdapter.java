package br.jus.stf.processamentoinicial.autuacao.infra;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.processamentoinicial.autuacao.domain.ProcessoAdapter;
import br.jus.stf.processamentoinicial.distribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.ProcessoRestResource;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.commands.CadastrarRecursalCommand;
import br.jus.stf.processamentoinicial.distribuicao.interfaces.dto.ProcessoDto;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
@Component
public class ProcessoRestAdapter implements ProcessoAdapter {
	
	@Autowired
	private ProcessoRestResource processoRestResource;

	@Override
	public ProcessoDto cadastrarRecursal(ProcessoRecursal processoRecursal) {
		CadastrarRecursalCommand command = new CadastrarRecursalCommand();
		
		command.setProcessoId(processoRecursal.id().toLong());
		command.setClasseId(processoRecursal.classe().toString());
		command.setNumero(processoRecursal.numero());
		command.setPeticaoId(processoRecursal.peticao().toLong());
		command.setSituacao(processoRecursal.situacao().toString());
		
		Optional.ofNullable(processoRecursal.preferencias()).ifPresent(prefs -> {
			command.setPreferencias(prefs.stream().map(preferenciaId -> preferenciaId.toLong())
					.collect(Collectors.toSet()));
		});
		
		return processoRestResource.cadastrarRecursal(command);
	}

}
