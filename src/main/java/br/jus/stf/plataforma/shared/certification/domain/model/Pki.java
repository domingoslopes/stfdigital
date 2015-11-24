package br.jus.stf.plataforma.shared.certification.domain.model;

import java.security.cert.X509Certificate;

public interface Pki {

	boolean belongsToPki(X509Certificate certificate);

	X509Certificate[] certificateChainOf(X509Certificate certificate);

}
