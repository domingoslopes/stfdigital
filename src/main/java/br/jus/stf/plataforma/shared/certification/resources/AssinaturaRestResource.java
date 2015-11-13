package br.jus.stf.plataforma.shared.certification.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.ApiOperation;

import br.jus.stf.plataforma.shared.certification.resources.commands.AssinarCommand;
import br.jus.stf.plataforma.shared.certification.service.AssinaturaService;
import br.jus.stf.plataforma.shared.certification.support.PreAssinaturaDto;

@RestController
@RequestMapping("/api/certification")
public class AssinaturaRestResource {

	@Autowired
	private AssinaturaService assinaturaService;
	
	@ApiOperation("Gera o hash do documento a ser assinado.")
	@RequestMapping(value = "pre-sign", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public PreAssinaturaDto preSign(@RequestParam("file") MultipartFile file) throws IOException {
		return assinaturaService.preSign(file.getInputStream());
	}
	
	@ApiOperation("")
	@RequestMapping(value = "post-sign", method = RequestMethod.POST)
	public void postSign(@RequestBody AssinarCommand command) {
		assinaturaService.postSign(command.getContextId(), command.getSignature());
	}
	
}
