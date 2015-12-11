package br.jus.stf.plataforma.identidades.interfaces.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.identidades.application.AssociadoApplicationService;

/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 10.12.2015
 */
@Component
public class AssociadoServiceFacade {
	
	@Autowired
	private AssociadoApplicationService associadoApplicationService;	
	
	public void cadastrar(Long idOrgao, String cpf, String nome, String tipoAssociacao, String cargo){
		associadoApplicationService.cadastrar(idOrgao, cpf, nome, tipoAssociacao, cargo);
	}
}
