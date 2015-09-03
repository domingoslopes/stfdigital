package br.jus.stf.autuacao.interfaces.actions;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordnik.swagger.annotations.ApiOperation;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Parte;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.plataforma.component.action.annotation.ActionController;
import br.jus.stf.plataforma.component.action.annotation.ActionMapping;
import br.jus.stf.plataforma.component.action.annotation.ActionMapping.ResourcesMode;

@ActionController
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

	@ActionMapping(id = "REGISTRAR_PETICAO_ELETRONICA", name = "RegistrarPeticaoEletronica", resourceClass = RegistrarPeticaoCommand.class,
			resourcesMode = ResourcesMode.One, neededAuthorities = {"peticionador"})
	public String peticionar(List<RegistrarPeticaoCommand> commands) {
		
		RegistrarPeticaoCommand command = commands.get(0);
		
		List<Parte> partesPoloAtivo = new LinkedList<Parte>();
		List<Parte> partesPoloPassivo = new LinkedList<Parte>();
		List<Documento> documentos = new LinkedList<Documento>();
		Polo poloAtivo = new Polo();
		Polo poloPassivo = new Polo();
		
		for(int i = 0; i < command.getPartesPoloAtivo().size(); i++) {
			partesPoloAtivo.add(new Parte(command.getPartesPoloAtivo().get(i)));
		}
		poloAtivo.setPartes(partesPoloAtivo);
		
		for(int i = 0; i < command.getPartesPoloPassivo().size(); i++){
			partesPoloPassivo.add(new Parte(command.getPartesPoloPassivo().get(i)));
		}
		poloPassivo.setPartes(partesPoloPassivo);
		
		for(int i = 0; i < command.getDocumentos().size(); i++){
			documentos.add(new Documento(command.getDocumentos().get(i)));
		}
		
		return peticaoApplicationService.peticionar("autuarOriginarios", command.getClasse(), poloAtivo, poloPassivo, documentos);
	}
	
	@ActionMapping(id = "REGISTRAR_PETICAO_FISICA", name = "RegistrarPeticaoFisica", resourceClass = RegistrarPeticaoFisicaCommand.class,
			resourcesMode = ResourcesMode.One, neededAuthorities = {"recebedor"})
	public String registrar(List<RegistrarPeticaoFisicaCommand> commands) {
		RegistrarPeticaoFisicaCommand command = commands.get(0);
		return peticaoApplicationService.registrar("autuarOriginarios", command.getQuantidadeVolumes(), command.getQuantidadeApensos(), command.getFormaRecebimento(), command.getNumeroSedex());
	}
	
	@ActionMapping(id = "PREAUTUAR_PETICAO_FISICA", name = "PreauturarPeticaoFisica", resourceClass = String.class,
			resourcesMode = ResourcesMode.One, neededAuthorities = {"recebedor"})
	public void preautuar(@PathVariable String id) {
		peticaoApplicationService.preautuar(id);
	}
}
