package br.jus.stf.plataforma.shared.certification.interfaces;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.ApiOperation;

import br.jus.stf.plataforma.shared.certification.PkiType;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.PostSignCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.PreSignCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.PrepareCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.dto.ContextIdDto;
import br.jus.stf.plataforma.shared.certification.interfaces.dto.PreSignatureDto;
import br.jus.stf.plataforma.shared.certification.service.SignatureService;
import br.jus.stf.plataforma.shared.certification.signature.PreSignature;
import br.jus.stf.plataforma.shared.certification.signature.SignatureContextId;
import br.jus.stf.plataforma.shared.certification.signature.SignedDocument;
import br.jus.stf.plataforma.shared.certification.signature.StreamedDocument;
import br.jus.stf.plataforma.shared.certification.support.AssinaturaExternaException;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.util.CertificationUtil;

@RestController
@RequestMapping("/api/certification/signature")
public class SignatureRestResource {

	@Autowired
	private SignatureService signatureService;

	@ApiOperation("Cria um novo contexto de assinatura com o certificado.")
	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	public ContextIdDto prepare(@RequestBody PrepareCommand command) throws DecoderException {
		X509Certificate certificate = CertificationUtil
				.bytesToCertificate(Hex.decodeHex(command.getCertificateAsHex().toCharArray()));
		SignatureContextId contextId = signatureService.prepareToSign(certificate, PkiType.ICP_BRASIL.instance());
		return new ContextIdDto(contextId.id());
	}

	@ApiOperation("Faz o upload do arquivo para assinatura.")
	@RequestMapping(value = "/upload-to-sign", method = RequestMethod.POST)
	public void uploadToSign(@RequestHeader("Signature-Context-Id") String contextId, @RequestParam("file") MultipartFile file) throws IOException {
		signatureService.attachToSign(new SignatureContextId(contextId), new StreamedDocument(file.getInputStream()));
	}

	@ApiOperation("Gera o hash do documento a ser assinado.")
	@RequestMapping(value = "/pre-sign", method = RequestMethod.POST)
	public PreSignatureDto preSign(@RequestBody PreSignCommand command) throws AssinaturaExternaException {
		PreSignature preSignature = signatureService.preSign(new SignatureContextId(command.getContextId()));
		return PreSignatureDto.from(preSignature);
	}

	@ApiOperation("Assina efetivamente o documento.")
	@RequestMapping(value = "/post-sign", method = RequestMethod.POST)
	public void postSign(@RequestBody PostSignCommand command) throws AssinaturaExternaException {
		signatureService.postSign(new SignatureContextId(command.getContextId()), new HashSignature(command.getSignatureAsHex()));
	}

	@ApiOperation("Recupera o documento assinado.")
	@RequestMapping(value = "/download-signed/{contextId}")
	public void downloadSigned(@PathVariable("contextId") String contextId, HttpServletResponse response) throws IOException {
		SignedDocument signedDocument = signatureService.recoverSignedDocument(new SignatureContextId(contextId));
		InputStream is = new ByteArrayInputStream(signedDocument.asBytes());
		
		response.setHeader("Content-disposition", "attachment; filename=" + contextId + ".pdf");
		response.setContentType("application/pdf");
		response.setHeader("Content-Length", String.valueOf(is.available()));
		
		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
	}
	
}
