package br.jus.stf.plataforma.documentos.interfaces;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import br.jus.stf.plataforma.documentos.domain.ControladorEdicaoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.Documento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.interfaces.dto.EdicaoDto;
import br.jus.stf.plataforma.documentos.interfaces.facade.DocumentoServiceFacade;
import br.jus.stf.plataforma.documentos.interfaces.facade.OnlyofficeCallbackFacade;
import br.jus.stf.shared.DocumentoId;

import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/onlyoffice")
public class OnlyofficeIntegrationRestResource {

	@Autowired
	private DocumentoServiceFacade documentoServiceFacade;

	@Autowired
	private DocumentoRepository documentoRepository;

	@Autowired
	private ControladorEdicaoDocumento controladorEdicaoDocumento;

	@Autowired
	private OnlyofficeCallbackFacade onlyofficeCallbackFacade;
	
	@Autowired
	@Qualifier("doocumentServerBaseUrl")
	private String doocumentServerBaseUrl;

	@ApiOperation("Recupera o conteúdo de um documento")
	@RequestMapping(value = "/documentos/{documentoId}/conteudo.docx", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> recuperarConteudo(@PathVariable("documentoId") Long documentoId)
	        throws IOException {
		ConteudoDocumento documento = documentoServiceFacade.pesquisaDocumento(documentoId);
		InputStream is = documento.stream();
		byte[] bytes = IOUtils.toByteArray(is);
		InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(bytes));
		HttpHeaders headers = createResponseHeaders(new Long(bytes.length));
		return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK);

	}

	private HttpHeaders createResponseHeaders(Long tamanho) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(tamanho);
		headers.setContentType(
		        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
		return headers;
	}

	@ApiOperation("Recupera o número de edição de um documento")
	@RequestMapping(value = "/documentos/{documentoId}/edicao", method = RequestMethod.GET)
	public EdicaoDto recuperarEdicao(@PathVariable("documentoId") Long documentoId) throws IOException {
		Documento documento = documentoRepository.findOne(new DocumentoId(documentoId));
		return new EdicaoDto(controladorEdicaoDocumento.gerarEdicao(documento.id()));
	}

	@ApiOperation("Callback para o onlyoffice")
	@RequestMapping(value = "/documentos/{documentoId}/callback", method = RequestMethod.POST)
	public Map<String, Object> callback(@PathVariable("documentoId") Long documentoId,
	        @RequestBody Map<String, Object> json) throws RestClientException, URISyntaxException {
		return onlyofficeCallbackFacade.callback(documentoId, json);
	}

	@ApiOperation("Recupera a url base do servidor de documentos")
	@RequestMapping(value = "/server/baseUrl", method = RequestMethod.GET)
	public String serverBaseUrl() {
		return doocumentServerBaseUrl;
	}
	
}
