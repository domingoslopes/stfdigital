package br.jus.stf.plataforma.shared.certification.infra.pki;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.jus.stf.plataforma.shared.certification.domain.model.certificate.CertificateUtils;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidation;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidationException;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.CertificateValidator;
import br.jus.stf.plataforma.shared.certification.infra.configuration.CryptoProvider;

public class BouncyCastlePkiCertificateValidator implements CertificateValidator {

	private Pki pki;

	public BouncyCastlePkiCertificateValidator(Pki pki) {
		this.pki = pki;
	}

	@Override
	public CertificateValidation validate(X509Certificate certificate) {
		CertificateValidation validation = new CertificateValidation();
		try {
			verifyNotExpired(certificate);
			if (!pki.belongsToPki(certificate)) {
				validation.associateChain(new X509Certificate[] { certificate });
				throw new CertificateValidationException(
						"Certificado não pertence a nehuma infraestrutura de chaves públicas confiável.");
			}
			X509Certificate[] chain = pki.certificateChainOf(certificate);
			validation.associateChain(chain);

			validateCertificateSignatureEnabled(certificate);

			validateCertificateChain(Arrays.asList(chain));

		} catch (CertificateValidationException e) {
			validation.appendValidationError(e.getMessage());
		}
		return validation;
	}

	private void verifyNotExpired(X509Certificate certificate) throws CertificateValidationException {
		try {
			certificate.checkValidity();
		} catch (CertificateExpiredException e) {
			throw new CertificateValidationException("O certificado se encontra expirado.", e);
		} catch (CertificateNotYetValidException e) {
			throw new CertificateValidationException("O certificado ainda não é válido.", e);
		}
	}

	private void validateCertificateSignatureEnabled(X509Certificate certificate)
			throws CertificateValidationException {
		// Verifica se o certificado está habilitado para assinatura digital.
		if (certificate.getKeyUsage() == null || (!certificate.getKeyUsage()[0] && !certificate.getKeyUsage()[1])) {
			throw new CertificateValidationException("Certificado não é habilitado para assinatura: "
					+ CertificateUtils.subjectNameAsString(certificate));
		}
	}

	private void validateCertificateChain(List<X509Certificate> chain) throws CertificateValidationException {
		try {
			// constroi os parametros de validacao
			PKIXParameters pkixParameters = buildPkixParameters(chain);

			CertPath certPath = CertificateFactory.getInstance("X.509", CryptoProvider.provider())
					.generateCertPath(chain);
			CertPathValidator validador = CertPathValidator.getInstance("PKIX", CryptoProvider.provider());
			// executa a validacao com os parametros informados
			validador.validate(certPath, pkixParameters);
		} catch (CertificateException e) {
			throw new CertificateValidationException("Erro do certificado.");
		} catch (NoSuchProviderException e) {
			throw new RuntimeException("Provider não encontrado ao validar o certificado.", e);
		} catch (CertPathValidatorException e) {
			throw new CertificateValidationException("Falha na validação da cadeia do certificado.");
		} catch (InvalidAlgorithmParameterException e) {
			throw new RuntimeException("Erro algoritmo invalido ao validar o certificado.");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro algoritmo invalido ao validar o certificado.");
		}
	}

	/**
	 * Constrói um objeto PKIXParameters, utilizado na validação do certificado.
	 * Indica que será utilizada a lista de revogação. Insere o trustedAnchors,
	 * com as autoridades certificadoras confiáveis.
	 * 
	 * @param crl
	 * @return
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateValidationException
	 * @throws RaizNaoEncontradaException
	 * @throws CRLInvalidaException
	 */
	private PKIXParameters buildPkixParameters(List<X509Certificate> chain)
			throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, CertificateValidationException {

		Set<TrustAnchor> trustedAnchors = new HashSet<TrustAnchor>();
		// recupera a lista de ACs confiaveis
		List<X509Certificate> confiaveis = pki.getTrustedAnchors();

		if (confiaveis == null || confiaveis.size() == 0) {
			throw new CertificateValidationException("Nenhuma raiz confiável foi encontrada.");
		}

		for (X509Certificate c : confiaveis) {
			trustedAnchors.add(new TrustAnchor(c, null));
		}
		// cria o pkixParameters
		PKIXParameters pkixParameters = new PKIXParameters(trustedAnchors);

		// TODO Implementar a validação de CRL.
		pkixParameters.setRevocationEnabled(false);

		// TODO Permitir validar em uma data.

		// TODO Permitir CertPathCheckers customizados.
		// pkixParameters.addCertPathChecker(new
		// IcpBrasilCriticalExtensionsChecker());
		return pkixParameters;
	}

}
