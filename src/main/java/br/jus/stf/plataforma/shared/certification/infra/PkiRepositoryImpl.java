package br.jus.stf.plataforma.shared.certification.infra;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.shared.certification.domain.CertificationUtil;
import br.jus.stf.plataforma.shared.certification.domain.model.Certificate;
import br.jus.stf.plataforma.shared.certification.domain.model.CertificateType;
import br.jus.stf.plataforma.shared.certification.domain.model.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiId;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiRepository;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiType;
import br.jus.stf.plataforma.shared.certification.domain.model.ValidationOnlyPki;
import br.jus.stf.plataforma.shared.certification.infra.pki.PlataformaPki;

@Repository
public class PkiRepositoryImpl implements PkiRepository {

	@Autowired
	private EntityManager entityManager;
	
	private PlataformaPki plataformaPkiInstance;
	
	@Override
	public Pki findOne(PkiId pkiId) {
		if (isPlataformaPki(pkiId)) {
			plataformaPkiInstance();
		}
		TypedQuery<Certificate> query = entityManager.createQuery("from Certificate c where c.pki = :pki", Certificate.class);
		query.setParameter("pki", PkiType.valueOf(pkiId.id()));
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
		ValidationOnlyPki pki = new ValidationOnlyPki(pkiId, rootCerts, intermediateCerts);
		return pki;
	}

	private PlataformaPki plataformaPkiInstance() {
		if (plataformaPkiInstance == null) {
			plataformaPkiInstance = new PlataformaPki();
		}
		return plataformaPkiInstance;
	}

	private boolean isPlataformaPki(PkiId pkiId) {
		return pkiId.equals(PkiType.ICP_PLATAFORMA.id());
	}

	@Override
	public Pki[] findAll(PkiId[] ids) {
		List<Pki> pkis = new ArrayList<>();
		for (PkiId id : ids) {
			pkis.add(findOne(id));
		}
		return pkis.toArray(new ValidationOnlyPki[0]);
	}

	
	
}
