package br.jus.stf.plataforma.documentos.infra.configuration.fs;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import br.jus.stf.plataforma.shared.settings.Profiles;

@Configuration
@Profile(Profiles.DOCUMENTO_FS)
public class FSConfiguration {

	@Value("${fs.docs-dir}")
	private String docsDirPath;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void checkDocsDir() throws IOException {
		File docsDir = new File(documentosDirPath());
		if (env.acceptsProfiles(Profiles.DESENVOLVIMENTO)) { // No desenvolvimento, tenta criar diretório
			if (!docsDir.exists()) {
				if (!docsDir.mkdir()) throw new RuntimeException("Erro ao criar o diretório de documentos.");
			} else {
				if (!env.acceptsProfiles(Profiles.KEEP_DATA)) {
					FileUtils.forceDelete(docsDir);
					if (!docsDir.mkdir()) throw new RuntimeException("Erro ao criar o diretório de documentos.");
				}
			}
		} else {
			if (!docsDir.exists()) {
				throw new RuntimeException("Diretório de documentos inexistente.");
			}
		}
	}

	@Bean(name = "documentosDirPath")
	public String documentosDirPath() {
		String normalizedPath;
		if (docsDirPath.startsWith("~")) {
			normalizedPath = System.getProperty("user.home") + docsDirPath.substring(1);
		} else {
			normalizedPath = docsDirPath;
		}
		return normalizedPath;
	}
	
}
