package br.jus.stf.plataforma.shared.certification.infra.pki;

import java.security.cert.X509Certificate;

import br.jus.stf.plataforma.shared.certification.domain.model.Pki;

public class PlataformaPki implements Pki {

	@Override
	public boolean belongsToPki(X509Certificate certificate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public X509Certificate[] certificateChainOf(X509Certificate certificate) {
		// TODO Auto-generated method stub
		return null;
	}

}
