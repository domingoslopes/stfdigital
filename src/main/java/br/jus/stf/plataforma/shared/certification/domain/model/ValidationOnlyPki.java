package br.jus.stf.plataforma.shared.certification.domain.model;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.x500.X500Principal;

public class ValidationOnlyPki implements Pki {

	private PkiId id;
	
	private List<X509Certificate> rootCertificates;
	
	private List<X509Certificate> intermediateCertificates;
	
	public ValidationOnlyPki(PkiId id, List<X509Certificate> rootCerts, List<X509Certificate> intermediateCerts) {
		this.id = id;
		this.rootCertificates = rootCerts;
		this.intermediateCertificates = intermediateCerts;
	}

	public boolean belongsToPki(X509Certificate certificate) {
		List<X509Certificate> chain = new ArrayList<>();
		chain.add(certificate);
		appendIssuersOf(certificate, chain);
		return rootCertificates.contains(chain.get(chain.size() - 1));
	}

	public X509Certificate[] certificateChainOf(X509Certificate certificate) {
		List<X509Certificate> chain = new ArrayList<>();
		chain.add(certificate);
		appendIssuersOf(certificate, chain);
		return chain.toArray(new X509Certificate[0]);
	}

	private void appendIssuersOf(X509Certificate certificate, List<X509Certificate> chain) {
		X500Principal issuer = certificate.getIssuerX500Principal();
		for (X509Certificate cert : intermediateCertificates) {
			if (issuer.equals(cert.getSubjectX500Principal())) {
				chain.add(cert);
				appendIssuersOf(cert, chain);
				return;
			}
		}
		for (X509Certificate cert : rootCertificates) {
			if (issuer.equals(cert.getSubjectX500Principal())) {
				chain.add(cert);
				return;
			}
		}
	}
	
	public PkiId id() {
		return id;
	}
	
}
