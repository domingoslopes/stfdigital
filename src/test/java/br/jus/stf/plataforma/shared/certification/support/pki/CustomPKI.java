package br.jus.stf.plataforma.shared.certification.support.pki;

import org.apache.commons.lang.Validate;

public class CustomPKI {

	private CustomPKIStore rootCA;
	private CustomPKIStore intermediateCA;
	private CustomPKIStore finalUser;

	public CustomPKI(CustomPKIStore rootCA, CustomPKIStore intermediateCA, CustomPKIStore finalUser) {
		Validate.notNull(rootCA, "RootCA é obrigatório");
		Validate.notNull(intermediateCA, "IntermediateCA é obrigatório");
		Validate.notNull(intermediateCA, "FinalUser é obrigatório");

		this.rootCA = rootCA;
		this.intermediateCA = intermediateCA;
		this.finalUser = finalUser;
	}

	public CustomPKIStore rootCA() {
		return rootCA;
	}

	public CustomPKIStore intermediateCA() {
		return intermediateCA;
	}

	public CustomPKIStore finalUser() {
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
