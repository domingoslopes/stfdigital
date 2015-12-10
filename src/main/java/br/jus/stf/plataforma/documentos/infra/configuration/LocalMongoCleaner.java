package br.jus.stf.plataforma.documentos.infra.configuration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.jus.stf.plataforma.shared.persistence.LocalData;

/**
 * Classe utilitária para matar processos remanescentes do mongo no ambiente
 * local.
 * 
 * @author Tomas.Godoi
 *
 */
public final class LocalMongoCleaner {

	private LocalMongoCleaner() {

	}

	public static void killExtracted() throws IOException {
		Runtime rt = Runtime.getRuntime();
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
			rt.exec("taskkill /F /IM extract*");
		} else {
			// rt.exec("kill -9 extract*"); TODO Implementar para Linux e Mac
		}
	}

	/**
	 * Realiza até 30 tentativas de apagar o arquivo de lock do mongo a cada 100
	 * milissegundos.
	 * 
	 */
	public static void clearLock() {
		File lockFile = new File(LocalData.instance().dataDirAbsolutePath() + "/mongodb/mongod.lock");
		int timeout = 3000;
		int waitTime = 100;
		if (lockFile.exists()) {
			while (true) {
				if (timeout <= 0)
					throw new RuntimeException("Não conseguiu limpar lock do mongo.");
				try {
					FileUtils.forceDelete(lockFile);
					break;
				} catch (IOException e) {
					try {
						timeout -= waitTime;
						Thread.sleep(waitTime);
					} catch (InterruptedException e1) {
						// Ignora
					}
				}
			}
		}
	}

}
