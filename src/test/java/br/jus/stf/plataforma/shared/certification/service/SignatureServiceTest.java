package br.jus.stf.plataforma.shared.certification.service;

import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

import br.jus.stf.plataforma.shared.certification.AbstractCertificationTest;
import br.jus.stf.plataforma.shared.certification.application.SignatureApplicationService;
import br.jus.stf.plataforma.shared.certification.application.SignatureContextManager;
import br.jus.stf.plataforma.shared.certification.signature.PreSignature;
import br.jus.stf.plataforma.shared.certification.signature.SignatureContextId;
import br.jus.stf.plataforma.shared.certification.signature.SignedDocument;
import br.jus.stf.plataforma.shared.certification.signature.StreamedDocument;
import br.jus.stf.plataforma.shared.certification.support.HashSignature;
import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiService;
import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiStore;
import br.jus.stf.plataforma.shared.certification.support.util.SignatureTestUtil;

public class SignatureServiceTest extends AbstractCertificationTest {

	private static final String PDF_DE_TESTE = "pdf-de-teste-001.pdf";

	@Spy
	private SignatureContextManager signatureConextManager;

	@Autowired
	private CustomPkiService customPKIService;

	@InjectMocks
	@Autowired
	private SignatureApplicationService signatureService;

	private HttpSession session;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		session = new MockHttpSession();
		Mockito.doReturn(session).when(signatureConextManager).session();
	}

	@Test
	public void testSign() throws Exception {
		SignatureContextId contextId;

		CustomPkiStore finalUser = customPKIService.customPKI().finalUser();

		// Passo 1 - Server-side: Criar um contexto de assinatura a partir do
		// SignatureService.
		X509Certificate certificate = finalUser.certificate();
		contextId = signatureService.prepareToSign(certificate, customPKIService.customPKI());

		// Passo 2 - Server-side: Receber o documento para assinatura.
		StreamedDocument document = new StreamedDocument(SignatureTestUtil.getDocumentToSign(PDF_DE_TESTE));
		signatureService.attachToSign(contextId, document);

		// Passo 3 - Server-side: Pré-assinar, gerando o hash
		PreSignature preSignature = signatureService.preSign(contextId);

		// Passo 4 - Client-side: Assinar o hash gerado
		HashSignature signature = sign(preSignature, finalUser.keyPair().getPrivate());

		// Passo 5 - Server-side: Pós-assinar, gerando o documento
		signatureService.postSign(contextId, signature);
		
		// Passo 6 - Server-side: Recuperar o documento assinado
		SignedDocument signedDocument = signatureService.recoverSignedDocument(contextId);
		Assert.assertNotNull(signedDocument);
	}

	private HashSignature sign(PreSignature preSignature, PrivateKey key) throws Exception {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(key);
		signature.update(preSignature.auth().authAsBytes());

		byte[] signed = signature.sign();

		return new HashSignature(signed);
	}

}
