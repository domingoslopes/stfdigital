package br.jus.stf.plataforma.shared.certification.support.generators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.io.FileUtils;

import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiGenerator;
import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiStore;
import br.jus.stf.plataforma.shared.certification.support.pki.IcpBrasilDadosPessoaFisica;

public class PlataformaCertificateGenerator {

	private static final String RESOURCES_DIR = "src/main/resources";
	private static final String PKIS_DIR = "/certification/pkis/";

	/**
	 * Os parâmetros para a main são:
	 * 
	 * <pki-name> <person-name> <person-cpf> <person-email>
	 * 
	 * Por exemplo:
	 * 
	 * "icp-plataforma" "JOAO DA SILVA" "57153200380"
	 * "joao.silva@stfdigital.stf.jus.br"
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 4) {
			System.err.println("Informe exatamente três parâmetros: <pki-name> <person-name> <person-cpf>");
			System.exit(1);
		}
		String pkiName = args[0];
		String personName = args[1];
		String personCPF = args[2];
		String personEmail = args[3];

		String pkiPath = RESOURCES_DIR + PKIS_DIR + "/" + pkiName;
		if (new File(pkiPath).exists()) {
			String pkiPrivatePath = pkiPath + "/private/";
			String keystorePath = pkiPrivatePath + "/keystore.p12";

			CustomPkiStore ca = getPkiStore(keystorePath);
			CustomPkiGenerator generator = new CustomPkiGenerator();

			IcpBrasilDadosPessoaFisica dadosPf = new IcpBrasilDadosPessoaFisica(null, personCPF, null, null);

			CustomPkiStore finalUser = generator.generateFinalUser(ca, personName + ":" + personCPF, personEmail, dadosPf);

			String certificatePath = pkiPath + "/certs";
			File dirPath = new File(certificatePath);
			if (!dirPath.exists()) {
				if (!dirPath.mkdir()) {
					System.err.println("Não foi possível criar diretório de certificados.");
					System.exit(1);
				}
			}
			GeneratorUtil.storeKeystoreOnDisk(finalUser, certificatePath + "/" + personCPF + ".p12");
			GeneratorUtil.storeCertificateOnDisk(finalUser, certificatePath + "/" + personCPF + ".cer");
			incrementeSerial(pkiPrivatePath + "/nextSerial");
		} else {
			System.err.println("Pki " + pkiName + " não encontrada.");
			System.exit(1);
		}

	}

	private static CustomPkiStore getPkiStore(String keystorePath) throws Exception {
		KeyStore pkcs12Store = KeyStore.getInstance("PKCS12");
		FileInputStream fis = new FileInputStream(keystorePath);
		pkcs12Store.load(fis, "changeit".toCharArray());
		Enumeration<String> aliases = pkcs12Store.aliases();
		int lastCaNumber = 0;
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (alias.startsWith("ca")) {
				int caNumber = Integer.parseInt(alias.substring(2));
				if (caNumber > lastCaNumber) {
					lastCaNumber = caNumber;
				}
			}
		}
		if (lastCaNumber == 0) {
			throw new Exception("CA final não encontrada.");
		}
		String caAlias = "ca" + lastCaNumber;
		PrivateKey key = (PrivateKey) pkcs12Store.getKey(caAlias, "changeit".toCharArray());
		X509Certificate certificate = (X509Certificate) pkcs12Store.getCertificate(caAlias);
		CustomPkiStore store = new CustomPkiStore(new KeyPair(certificate.getPublicKey(), key), certificate);
		return store;
	}

	private static void incrementeSerial(String path) throws IOException {
		File serialFile = new File(path);
		String serial = FileUtils.readFileToString(serialFile);
		int serialInt = Integer.parseInt(serial) + 1;
		FileUtils.writeStringToFile(serialFile, String.valueOf(serialInt));
	}

}
