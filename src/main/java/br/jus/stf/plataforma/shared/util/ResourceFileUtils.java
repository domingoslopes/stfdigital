package br.jus.stf.plataforma.shared.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ResourceFileUtils {

	private ResourceFileUtils(){
		
	}
	
	/**
	 * Retorna uma string do conteúdo do arquivo
	 * 
	 * @param location
	 * @return
	 * @throws IOException 
	 */
	public static String read(String location) throws IOException {
		InputStream input = ResourceFileUtils.class.getResourceAsStream(location);
		return StringUtils.trimAllWhitespace(IOUtils.toString(input));
	}
	
}
