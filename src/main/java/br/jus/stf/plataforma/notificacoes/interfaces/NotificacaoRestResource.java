package br.jus.stf.plataforma.notificacoes.interfaces;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.notificacoes.interfaces.command.MarcarLidasCommand;
import br.jus.stf.plataforma.notificacoes.interfaces.command.NotificarCommand;
import br.jus.stf.plataforma.notificacoes.interfaces.facade.NotificacaoServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Lucas.Rodrigues
 *
 */
@RestController
@RequestMapping(value = "/api/notificacoes")
public class NotificacaoRestResource {
	
	@Autowired
	private NotificacaoServiceFacade notificacaoServiceFacade;
	
	@ApiOperation("Permite notificar outros usuários através dos tipos de interfaces disponíveis.")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void notificar(@RequestBody @Valid NotificarCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
		notificacaoServiceFacade.notificar(command.getTipo(), command.getMensagem(), command.getNotificados());
	}
	
	@ApiOperation("Permite marcar uma notificação como lida.")
	@RequestMapping(value = "", method = RequestMethod.PUT)
    public void marcarLidas(@RequestBody @Valid MarcarLidasCommand command, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getAllErrors().toString());
		}
        notificacaoServiceFacade.marcarLidas(command.getNotificacoes());
    }
	
}
