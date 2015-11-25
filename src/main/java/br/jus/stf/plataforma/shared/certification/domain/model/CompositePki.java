package br.jus.stf.plataforma.shared.certification.domain.model;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

public class CompositePki {

	private List<Pki> pkis;

	public CompositePki(Pki... pkis) {
		this.pkis = Arrays.asList(pkis);
	}

	public boolean belongsToPki(X509Certificate certificate) {
		return pkis.stream().anyMatch(pki -> pki.belongsToPki(certificate));
	}

	public X509Certificate[] certificateChainOf(X509Certificate certificate) {
		X509Certificate[] chain = null;
		for (Pki pki : pkis) {
			chain = pki.certificateChainOf(certificate);
			if (chain != null)
				break;
		}
		return chain;
	}

}
