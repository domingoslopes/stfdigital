package br.jus.stf.plataforma.documentos.infra.configuration;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.jezhumble.javasysmon.JavaSysMon;
import com.jezhumble.javasysmon.ProcessInfo;

import br.jus.stf.plataforma.shared.persistence.LocalData;

/**
 * Classe utilitária para matar processos remanescentes do mongo no ambiente
 * local.
 * 
 * @author Tomas.Godoi
 *
 */
public final class LocalMongoCleaner {

	private static final Pattern pattern = Pattern.compile(".*extract.+extractmongod.*");
	private static final boolean isLinux = System.getProperty("os.name").toLowerCase().startsWith("linux");
	
	private LocalMongoCleaner() {

	}

	public static void killExtracted() throws IOException {
		JavaSysMon monitor = new JavaSysMon();
		ProcessInfo[] processTable = monitor.processTable();
		for (ProcessInfo pi : processTable) {
			if (isMongodExtractedProcess(pi)) {
				if (!isLinux) {
					monitor.killProcess(pi.getPid());
				} else {
					// No Linux, o método acima estava dando erro.
					Runtime.getRuntime().exec("kill -9 " + pi.getPid());
				}
			}
		}
	}

	private static boolean isMongodExtractedProcess(ProcessInfo pi) {
		Matcher matcher = pattern.matcher(pi.getCommand());
		return matcher.matches();
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
