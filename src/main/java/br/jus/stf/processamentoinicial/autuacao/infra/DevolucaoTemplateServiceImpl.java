package br.jus.stf.processamentoinicial.autuacao.infra;

import static java.lang.String.format;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import br.jus.stf.processamentoinicial.autuacao.domain.DevolucaoTemplateService;
import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;


/**
 * @author Lucas.Rodrigues
 *
 */
@Service
public class DevolucaoTemplateServiceImpl implements DevolucaoTemplateService {

	@Override
	public InputStream carregarTemplate(MotivoDevolucao motivoDevolucao, String extensao) throws Exception {
		try {
			extensao = Optional.ofNullable(extensao).orElse("html");
			String templatePath = format("templates/devolucao/%s.%s", motivoDevolucao.descricao().toLowerCase(), extensao);
			return new ClassPathResource(templatePath).getInputStream(); 
		} catch (Exception e) {
			throw new Exception("Não foi possível carregar template.", e);
		}
	}
}
