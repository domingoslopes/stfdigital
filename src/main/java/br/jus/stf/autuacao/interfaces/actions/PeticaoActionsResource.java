package br.jus.stf.autuacao.interfaces.actions;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import br.jus.stf.autuacao.application.PeticaoApplicationService;
import br.jus.stf.autuacao.domain.entity.Documento;
import br.jus.stf.autuacao.domain.entity.Parte;
import br.jus.stf.autuacao.domain.entity.Polo;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoCommand;
import br.jus.stf.autuacao.interfaces.commands.RegistrarPeticaoFisicaCommand;
import br.jus.stf.shared.infra.action.annotation.ActionController;
import br.jus.stf.shared.infra.action.annotation.ActionMapping;

@ActionController
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;

	@ActionMapping(id = "REGISTRAR_PETICAO_ELETRONICA", name = "RegistrarPeticaoEletronica", neededAuthorities = {"peticionador"})
	public String peticionar(RegistrarPeticaoCommand command) {
		
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
	
	@ActionMapping(id = "REGISTRAR_PETICAO_FISICA", name = "RegistrarPeticaoFisica", neededAuthorities = {"recebedor"})
	public String registrar(RegistrarPeticaoFisicaCommand command) {
		return peticaoApplicationService.registrar("autuarOriginarios", command.getQuantidadeVolumes(), command.getQuantidadeApensos(), command.getFormaRecebimento(), command.getNumeroSedex());
	}
	
	@ActionMapping(id = "PREAUTUAR_PETICAO_FISICA", name = "PreauturarPeticaoFisica", neededAuthorities = {"recebedor"})
	public void preautuar(@PathVariable String id) {
		peticaoApplicationService.preautuar(id);
	}
}
