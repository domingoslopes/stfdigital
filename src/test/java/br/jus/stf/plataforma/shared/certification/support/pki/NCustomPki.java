package br.jus.stf.plataforma.shared.certification.support.pki;

import org.apache.commons.lang.Validate;

public class NCustomPki {

	private CustomPkiStore rootCA;
	private CustomPkiStore[] intermediateCAs;

	public NCustomPki(CustomPkiStore rootCA, CustomPkiStore... intermediateCAs) {
		Validate.notNull(rootCA, "RootCA é obrigatório");
		Validate.notNull(intermediateCAs, "IntermediateCA é obrigatório");

		this.rootCA = rootCA;
		this.intermediateCAs = intermediateCAs;
	}

	public CustomPkiStore rootCA() {
		return rootCA;
	}

	public CustomPkiStore[] intermediateCAs() {
		return intermediateCAs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("### Root CA ###");
		sb.append(rootCA.certificate().toString()).append("\n\n");

		for (CustomPkiStore ca : intermediateCAs) {
			sb.append("### Intermediate CA ###");
			sb.append(ca.certificate().toString()).append("\n\n");
		}

		return sb.toString();
	}

}
