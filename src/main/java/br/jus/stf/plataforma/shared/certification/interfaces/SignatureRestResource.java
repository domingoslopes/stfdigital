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

import br.jus.stf.plataforma.shared.certification.application.SignatureApplicationService;
import br.jus.stf.plataforma.shared.certification.domain.PDFSigningSpecificationBuilder;
import br.jus.stf.plataforma.shared.certification.domain.model.HashType;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiIds;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiType;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.SignedDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningSpecification;
import br.jus.stf.plataforma.shared.certification.infra.PdfTempDocument;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.PostSignCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.PreSignCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.PrepareCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.commands.ProvideToSignCommand;
import br.jus.stf.plataforma.shared.certification.interfaces.dto.PreSignatureDto;
import br.jus.stf.plataforma.shared.certification.interfaces.dto.SignedDocumentDto;
import br.jus.stf.plataforma.shared.certification.interfaces.dto.SignerIdDto;
import br.jus.stf.plataforma.shared.certification.signature.DocumentSignerId;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.SigningException;
import br.jus.stf.plataforma.shared.certification.util.CertificationUtil;
import br.jus.stf.shared.DocumentoId;

@RestController
@RequestMapping("/api/certification/signature")
public class SignatureRestResource {

	private static final String SIGNING_REASON = "Assinatura de documentos STF Digital";

	@Autowired
	private SignatureApplicationService signatureApplicationService;

	@ApiOperation("Cria um novo contexto de assinatura com o certificado.")
	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	public SignerIdDto prepare(@RequestBody PrepareCommand command) throws DecoderException, SigningException {
		// Converte o certificado recebido para o objeto da classe
		// X509Certificate.
		X509Certificate certificate = CertificationUtil
				.bytesToCertificate(Hex.decodeHex(command.getCertificateAsHex().toCharArray()));
		// Constrói uma especificação de assinatura de PDF.
		SigningSpecification spec = new PDFSigningSpecificationBuilder().pkcs7Dettached().reason(SIGNING_REASON)
				.hashAlgorithm(HashType.SHA256).build();
		DocumentSignerId signerId = signatureApplicationService.prepareToSign(certificate, pkis(), spec);
		return new SignerIdDto(signerId.id());
	}

	private PkiIds pkis() {
		return new PkiIds(PkiType.ICP_BRASIL.id(), PkiType.ICP_PLATAFORMA.id());
	}

	@ApiOperation("Faz o upload do arquivo para assinatura.")
	@RequestMapping(value = "/upload-to-sign", method = RequestMethod.POST)
	public void uploadToSign(@RequestHeader("Signer-Id") String signerId, @RequestParam("file") MultipartFile file)
			throws IOException, SigningException {
		signatureApplicationService.attachToSign(new DocumentSignerId(signerId),
				new PdfTempDocument(file.getInputStream()));
	}

	@ApiOperation("Fornece um arquivo já existente no servidor para assinatura.")
	@RequestMapping(value = "/provide-to-sign", method = RequestMethod.POST)
	public void provideToSign(@RequestBody ProvideToSignCommand command) throws SigningException {
		signatureApplicationService.provideToSign(new DocumentSignerId(command.getSignerId()),
				command.getDocumentId());
	}

	@ApiOperation("Gera o hash do documento a ser assinado.")
	@RequestMapping(value = "/pre-sign", method = RequestMethod.POST)
	public PreSignatureDto preSign(@RequestBody PreSignCommand command) throws SigningException {
		PreSignature preSignature = signatureApplicationService.preSign(new DocumentSignerId(command.getSignerId()));
		return PreSignatureDto.from(preSignature);
	}

	@ApiOperation("Assina efetivamente o documento.")
	@RequestMapping(value = "/post-sign", method = RequestMethod.POST)
	public void postSign(@RequestBody PostSignCommand command) throws SigningException {
		signatureApplicationService.postSign(new DocumentSignerId(command.getSignerId()),
				new HashSignature(command.getSignatureAsHex()));
	}

	@ApiOperation("Recupera o documento assinado.")
	@RequestMapping(value = "/download-signed/{contextId}")
	public void downloadSigned(@PathVariable("contextId") String contextId, HttpServletResponse response)
			throws IOException {
		SignedDocument signedDocument = signatureApplicationService
				.recoverSignedDocument(new DocumentSignerId(contextId));
		InputStream is = signedDocument.stream();

		response.setHeader("Content-disposition", "attachment; filename=" + contextId + ".pdf");
		response.setContentType("application/pdf");
		response.setHeader("Content-Length", String.valueOf(is.available()));

		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
	}

	@ApiOperation("Salva o documento assinado no contexto de documentos.")
	@RequestMapping(value = "/save-signed/{contextId}")
	public SignedDocumentDto saveSigned(@PathVariable("contextId") String contextId) throws IOException {
		DocumentoId documentId = signatureApplicationService.saveSigned(new DocumentSignerId(contextId));
		return new SignedDocumentDto(documentId.toLong());
	}

}
