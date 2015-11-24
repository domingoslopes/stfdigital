package br.jus.stf.plataforma.shared.certification.infra.persistence;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.certification.domain.model.Certificate;
import br.jus.stf.plataforma.shared.certification.domain.model.CertificateType;
import br.jus.stf.plataforma.shared.certification.domain.model.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiId;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiRepository;
import br.jus.stf.plataforma.shared.certification.util.CertificationUtil;

public class PkiRepositoryImpl implements PkiRepository {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Pki findOne(PkiId pkiId) {
		TypedQuery<Certificate> query = entityManager.createQuery("from Certificate c where c.pki = :pki", Certificate.class);
		query.setParameter("pki", pkiId.id());
		List<Certificate> certificates = query.getResultList();
		List<X509Certificate> rootCerts = new ArrayList<>();
		List<X509Certificate> intermediateCerts = new ArrayList<>();
		for (Certificate c : certificates) {
			if (c.certificateType() == CertificateType.R) {
				rootCerts.add(CertificationUtil.bytesToCertificate(c.content()));
			} else if (c.certificateType() == CertificateType.A) {
				intermediateCerts.add(CertificationUtil.bytesToCertificate(c.content()));
			}
		}
		Pki pki = new Pki(pkiId, rootCerts, intermediateCerts);
		return pki;
	}

	
	
}
