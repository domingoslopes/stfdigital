package br.jus.stf.plataforma.shared.certification.domain.model.pki;

public interface PkiRepository {

	Pki findOne(PkiId id);

	Pki[] findAll(PkiId[] ids);
	
}
