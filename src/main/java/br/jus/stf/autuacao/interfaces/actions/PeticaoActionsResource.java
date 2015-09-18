package br.jus.stf.autuacao.interfaces.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.autuacao.domain.model.FormaRecebimento;
import br.jus.stf.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.autuacao.interfaces.dto.ProcessoDistribuidoDto;
import br.jus.stf.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.shared.infra.action.annotation.ActionController;
import br.jus.stf.shared.infra.action.annotation.ActionMapping;

@ActionController
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoServiceFacade peticaoSerivceFacade;
	
	@Autowired
	private Validator validator;

	@ActionMapping(id = "REGISTRAR_PETICAO_ELETRONICA", name = "RegistrarPeticaoEletronica", neededAuthorities = {"peticionador"})
	public Long peticionar(RegistrarPeticaoCommand command) {
		
		String classeSugerida = command.getClasse();
		List<String> poloAtivo = new LinkedList<String>();
		List<String> poloPassivo = new LinkedList<String>();
		List<String> documentos = new LinkedList<String>();
		
		Set<ConstraintViolation<RegistrarPeticaoCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException("Petição Inválida: " + result.toString());
		}
		
		for(int i = 0; i < command.getPartesPoloAtivo().size(); i++) {
			poloAtivo.add(command.getPartesPoloAtivo().get(i));
		}
				
		for(int i = 0; i < command.getPartesPoloPassivo().size(); i++){
			poloPassivo.add(command.getPartesPoloPassivo().get(i));
		}
		
		for(int i = 0; i < command.getDocumentos().size(); i++){
			documentos.add(command.getDocumentos().get(i));
		}
				
		return this.peticaoSerivceFacade.peticionar(classeSugerida, poloAtivo, poloPassivo, documentos);
	}
	
	@ActionMapping(id = "REGISTRAR_PETICAO_FISICA", name = "RegistrarPeticaoFisica", neededAuthorities = {"recebedor"})
	public Long registrar(RegistrarPeticaoFisicaCommand command) {
		
		Integer volumes = command.getQuantidadeVolumes();
    	Integer apensos = command.getQuantidadeApensos();
    	String numeroSedex = command.getNumeroSedex();
    	FormaRecebimento formaRecebimento = null;
    	
    	Set<ConstraintViolation<RegistrarPeticaoFisicaCommand>> result = validator.validate(command);
		
    	if (!result.isEmpty()) {
			throw new IllegalArgumentException("Petição Inválida: " + result.toString());
		}
    	
    	switch(command.getFormaRecebimento()){
    	case "1": //Balcão.
    		formaRecebimento = FormaRecebimento.BALCAO;
    		break;
    	case "2": //Sedex
    		formaRecebimento = FormaRecebimento.SEDEX;
    		break;
    	case "3": //Malote
    		formaRecebimento = FormaRecebimento.MALOTE;
    		break;
    	case "4": //Fax
    		formaRecebimento = FormaRecebimento.FAX;
    		break;
    	case "5": //Email
    		formaRecebimento = FormaRecebimento.EMAIL;
    		break;
    	}
    	
    	return this.peticaoSerivceFacade.registrar(volumes, apensos, formaRecebimento, numeroSedex);
	}
	
	@ActionMapping(id = "PREAUTUAR_PETICAO_FISICA", name = "PreautuarPeticaoFisica", neededAuthorities = {"preautuador"})
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		this.peticaoSerivceFacade.preautuar(command.getIdPeticao(), command.getClasseSugerida()); 
	}
	
	@ActionMapping(id = "AUTUAR_PETICAO", name = "AutuarPeticao", neededAuthorities = {"autuador"})
	public void autuar(AutuarPeticaoCommand command) {
		this.peticaoSerivceFacade.autuar(command.getIdPeticao(), command.getClasse(), command.isValida(), command.getMotivo()); 
	}
	
	@ActionMapping(id = "DISTRIBUIR_PETICAO", name = "DistribuirPeticao", neededAuthorities = {"distribuidor"})
	public ProcessoDistribuidoDto distribuir(DistribuirPeticaoCommand command) {
    	return this.peticaoSerivceFacade.distribuir(command.getIdPeticao(), command.getIdRelator());
    }
}
