package br.jus.stf.plataforma.shared.certification.support.pki;

import org.apache.commons.lang.Validate;

import br.jus.stf.plataforma.shared.certification.infra.pki.CustomKeyStore;

public class CustomPki {

	private CustomKeyStore rootCA;
	private CustomKeyStore intermediateCA;
	private CustomKeyStore finalUser;

	public CustomPki(CustomKeyStore rootCA, CustomKeyStore intermediateCA, CustomKeyStore finalUser) {
		Validate.notNull(rootCA, "RootCA é obrigatório");
		Validate.notNull(intermediateCA, "IntermediateCA é obrigatório");
		Validate.notNull(intermediateCA, "FinalUser é obrigatório");

		this.rootCA = rootCA;
		this.intermediateCA = intermediateCA;
		this.finalUser = finalUser;
	}

	public CustomKeyStore rootCA() {
		return rootCA;
	}

	public CustomKeyStore intermediateCA() {
		return intermediateCA;
	}

	public CustomKeyStore finalUser() {
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
