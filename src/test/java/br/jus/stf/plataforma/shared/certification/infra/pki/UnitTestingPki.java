package br.jus.stf.plataforma.shared.certification.infra.pki;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.jus.stf.plataforma.shared.certification.domain.model.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.PkiId;
import br.jus.stf.plataforma.shared.certification.domain.model.ValidationOnlyPki;
import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiGenerator;
import br.jus.stf.plataforma.shared.certification.support.pki.IcpBrasilDadosPessoaFisica;
import br.jus.stf.plataforma.shared.certification.support.pki.NCustomPki;

public class UnitTestingPki implements Pki {

	private static UnitTestingPki singleton;

	private CustomKeyStore rootStore;
	private List<CustomKeyStore> intermediateStores;
	private CustomKeyStore finalUserStore;

	private ValidationOnlyPki pki;

	private UnitTestingPki() {
		try {
			CustomPkiGenerator pkiGenerator = new CustomPkiGenerator();
			String rootCN = "AC Raiz Teste Unitario STF Digital v1";
			String[] intermediate = new String[] { "AC Intermediaria 1 Teste Unitario STF Digital v1",
					"AC Intermediaria 2 Teste Unitario STF Digital v1" };

			NCustomPki customPki = pkiGenerator.generateCustomPKI(rootCN, intermediate);
			rootStore = customPki.rootCA();
			intermediateStores = Arrays.asList(customPki.intermediateCAs());

			String cpf = "43848071207";
			IcpBrasilDadosPessoaFisica dadosPf = new IcpBrasilDadosPessoaFisica(null, "43848071207", null, null);

			finalUserStore = pkiGenerator.generateFinalUser(intermediateStores.get(intermediateStores.size() - 1), "TESTE UNITARIO" + ":" + cpf, 1, "teste.unitario@stfdigital.stf.jus.br", dadosPf);
			
			pki = new ValidationOnlyPki(new PkiId("ICP_PLATAFORMA"), Arrays.asList(rootStore.certificate()),
					intermediateStores.stream().map(s -> s.certificate()).collect(Collectors.toList()));
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar UnitTestingPki", e);
		}
	}

	public static UnitTestingPki instance() {
		if (singleton == null) {
			synchronized (UnitTestingPki.class) {
				singleton = new UnitTestingPki();
			}
		}
		return singleton;
	}

	@Override
	public boolean belongsToPki(X509Certificate certificate) {
		return pki.belongsToPki(certificate);
	}

	@Override
	public X509Certificate[] certificateChainOf(X509Certificate certificate) {
		return pki.certificateChainOf(certificate);
	}
	
	public CustomKeyStore finalUserStore() {
		return finalUserStore;
	}
	
}
