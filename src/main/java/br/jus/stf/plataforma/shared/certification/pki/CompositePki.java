package br.jus.stf.plataforma.shared.certification.pki;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import br.jus.stf.plataforma.shared.certification.domain.model.Pki;

public class CompositePki implements Pki {

	private List<Pki> pkis;

	public CompositePki(Pki... pkis) {
		this.pkis = Arrays.asList(pkis);
	}

	@Override
	public boolean belongsToPki(X509Certificate certificate) {
		return pkis.stream().anyMatch(pki -> pki.belongsToPki(certificate));
	}

	@Override
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
