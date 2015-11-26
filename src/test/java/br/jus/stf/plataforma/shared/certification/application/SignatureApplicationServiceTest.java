package br.jus.stf.plataforma.shared.certification.application;

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

import br.jus.stf.plataforma.shared.certification.domain.PDFSigningSpecificationBuilder;
import br.jus.stf.plataforma.shared.certification.domain.model.DocumentSignerId;
import br.jus.stf.plataforma.shared.certification.domain.model.HashSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.HashType;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiIds;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiType;
import br.jus.stf.plataforma.shared.certification.domain.model.PreSignature;
import br.jus.stf.plataforma.shared.certification.domain.model.SignedDocument;
import br.jus.stf.plataforma.shared.certification.domain.model.SigningSpecification;
import br.jus.stf.plataforma.shared.certification.infra.StreamedDocument;
import br.jus.stf.plataforma.shared.certification.infra.pki.CustomKeyStore;
import br.jus.stf.plataforma.shared.certification.infra.session.SessionDocumentSignerRepository;
import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiService;
import br.jus.stf.plataforma.shared.certification.support.util.SignatureTestUtil;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

public class SignatureApplicationServiceTest extends AbstractIntegrationTests {

	private static final String PDF_DE_TESTE = "pdf-de-teste-001.pdf";

	private static final String SIGNING_REASON = "Unit Test STF Digital";

	@Spy
	private SessionDocumentSignerRepository documentSignerRepository;

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
		Mockito.doReturn(session).when(documentSignerRepository).session();
	}

	@Test
	public void testSign() throws Exception {
		DocumentSignerId signerId;

		CustomKeyStore finalUser = customPKIService.customPKI().finalUser();

		// Passo 1 - Server-side: Criar um contexto de assinatura a partir do
		// SignatureService.
		X509Certificate certificate = finalUser.certificate();
		PkiIds ids = new PkiIds(PkiType.ICP_BRASIL.id());
		SigningSpecification spec = new PDFSigningSpecificationBuilder().pkcs7Dettached().reason(SIGNING_REASON)
				.hashAlgorithm(HashType.SHA256).build();
		signerId = signatureService.prepareToSign(certificate, ids, spec);

		// Passo 2 - Server-side: Receber o documento para assinatura.
		StreamedDocument document = new StreamedDocument(SignatureTestUtil.getDocumentToSign(PDF_DE_TESTE));
		signatureService.attachToSign(signerId, document);

		// Passo 3 - Server-side: Pré-assinar, gerando o hash
		PreSignature preSignature = signatureService.preSign(signerId);

		// Passo 4 - Client-side: Assinar o hash gerado
		HashSignature signature = sign(preSignature, finalUser.keyPair().getPrivate());

		// Passo 5 - Server-side: Pós-assinar, gerando o documento
		signatureService.postSign(signerId, signature);
		
		// Passo 6 - Server-side: Recuperar o documento assinado
		SignedDocument signedDocument = signatureService.recoverSignedDocument(signerId);
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
