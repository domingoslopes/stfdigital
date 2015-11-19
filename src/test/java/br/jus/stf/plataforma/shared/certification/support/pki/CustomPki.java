package br.jus.stf.plataforma.shared.certification.support.pki;

import org.apache.commons.lang.Validate;

import br.jus.stf.plataforma.shared.certification.Pki;

public class CustomPki implements Pki {

	private CustomPkiStore rootCA;
	private CustomPkiStore intermediateCA;
	private CustomPkiStore finalUser;

	public CustomPki(CustomPkiStore rootCA, CustomPkiStore intermediateCA, CustomPkiStore finalUser) {
		Validate.notNull(rootCA, "RootCA é obrigatório");
		Validate.notNull(intermediateCA, "IntermediateCA é obrigatório");
		Validate.notNull(intermediateCA, "FinalUser é obrigatório");

		this.rootCA = rootCA;
		this.intermediateCA = intermediateCA;
		this.finalUser = finalUser;
	}

	public CustomPkiStore rootCA() {
		return rootCA;
	}

	public CustomPkiStore intermediateCA() {
		return intermediateCA;
	}

	public CustomPkiStore finalUser() {
		return finalUser;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("### Root CA ###");
		sb.append(rootCA.certificate().toString()).append("\n\n");
		
		sb.append("### Intermediate CA ###");
		sb.append(intermediateCA.certificate().toString()).append("\n\n");
		
		sb.append("### Final User ###");
		sb.append(finalUser.certificate().toString()).append("\n\n");
		
		return sb.toString();
	}
	
}
