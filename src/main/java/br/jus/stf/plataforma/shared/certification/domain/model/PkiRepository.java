package br.jus.stf.plataforma.shared.certification.domain.model;

public interface PkiRepository {

	Pki findOne(PkiId id);
	
}
