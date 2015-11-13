package br.jus.stf.plataforma.shared.certification.interfaces;

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

import br.jus.stf.plataforma.shared.certification.interfaces.commands.AssinarCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.PreSignCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.dto.PreSignatureDto;
import br.jus.stf.plataforma.shared.certification.service.SignatureService;

@RestController
@RequestMapping("/api/certification")
public class SignatureRestResource {

	@Autowired
	private SignatureService signatureService;
	
	@ApiOperation("Faz o upload do arquivo para assinatura.")
	@RequestMapping(value = "/upload-to-sign", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadToSign(@RequestParam("file") MultipartFile file) throws IOException {
		return signatureService.storeToSign(file.getInputStream());
	}
	
	@ApiOperation("Gera o hash do documento a ser assinado.")
	@RequestMapping(value = "/pre-sign", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public PreSignatureDto preSign(@RequestBody PreSignCommand command) throws IOException {
		return signatureService.preSign(command.getTempDocId(), command.getCertificate());
	}
	
	@ApiOperation("Assina efetivamente o documento.")
	@RequestMapping(value = "/post-sign", method = RequestMethod.POST)
	public void postSign(@RequestBody AssinarCommand command) {
		signatureService.postSign(command.getContextId(), command.getSignature());
	}
	
}
