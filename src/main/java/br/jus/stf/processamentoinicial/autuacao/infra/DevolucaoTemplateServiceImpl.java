package br.jus.stf.processamentoinicial.autuacao.infra;

import static java.lang.String.format;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import br.jus.stf.processamentoinicial.autuacao.domain.DevolucaoTemplateService;
import br.jus.stf.processamentoinicial.autuacao.domain.model.TipoDevolucao;


/**
 * @author Lucas.Rodrigues
 *
 */
@Service
public class DevolucaoTemplateServiceImpl implements DevolucaoTemplateService {

	@Override
	public InputStream carregarTemplate(TipoDevolucao tipoDevolucao, String extensao) throws Exception {
		try {
			extensao = Optional.ofNullable(extensao).orElse("html");
			String templatePath = format("templates/devolucao/%s.%s", tipoDevolucao.name().toLowerCase(), extensao);
			return new ClassPathResource(templatePath).getInputStream(); 
		} catch (Exception e) {
			throw new Exception("Não foi possível carregar template.", e);
		}
	}
}
