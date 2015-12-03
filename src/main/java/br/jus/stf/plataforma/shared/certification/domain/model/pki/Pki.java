package br.jus.stf.plataforma.shared.certification.domain.model.pki;

import java.security.cert.X509Certificate;

public interface Pki {

	public boolean belongsToPki(X509Certificate certificate);

	public X509Certificate[] certificateChainOf(X509Certificate certificate);

}
