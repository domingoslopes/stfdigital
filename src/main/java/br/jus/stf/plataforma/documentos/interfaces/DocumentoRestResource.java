package br.jus.stf.plataforma.documentos.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.documentos.interfaces.commands.SalvarDocumentosCommand;
import br.jus.stf.plataforma.documentos.interfaces.dto.DocumentoDto;
import br.jus.stf.plataforma.documentos.interfaces.facade.DocumentoServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Api REST para salvar e recuperar documentos
 * 
 * @author Lucas Rodrigues
 */
@RestController
@RequestMapping("/api/documentos")
public class DocumentoRestResource {
	
	@Autowired
	private DocumentoServiceFacade documentoServiceFacade;
	
	@Autowired
	private Validator validator;

	@ApiOperation("Salva os documentos temporários")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public List<DocumentoDto> salvar(@RequestBody SalvarDocumentosCommand command) {
		Set<ConstraintViolation<SalvarDocumentosCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
		return documentoServiceFacade.salvarDocumentos(command.getDocumentos());
	}	
	
	@ApiOperation("Recupera um documento do repositório")
	@RequestMapping(value = "/{documentoId}", method = RequestMethod.GET)
	public void recuperar(@PathVariable("documentoId") Long documentoId, HttpServletResponse response) throws IOException {
		InputStream is = documentoServiceFacade.pesquisaDocumento(documentoId);
		IOUtils.copy(is, response.getOutputStream());
		IOUtils.closeQuietly(is);
	    setPdfReponseHeaders(documentoId, response);
	}
	
	@ApiOperation("Envia um documento para armazenamento temporário e retorna o indentificador")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String upload(@RequestParam("file") MultipartFile file) {
		return documentoServiceFacade.salvarDocumentoTemporario(file);
	}

	/**
	 * Define os headers para o pdf 
	 * 
	 * @param documentoId
	 * @param response
	 */
	private void setPdfReponseHeaders(Long documentoId, HttpServletResponse response) {
		String filename = documentoId + ".pdf";
	    response.addHeader(HttpHeaders.CONTENT_TYPE, "application/pdf");
	    response.addHeader(HttpHeaders.CONTENT_DISPOSITION, createPDFContentDisposition(filename));
	    response.addHeader(HttpHeaders.CACHE_CONTROL, "must-revalidate, post-check=0, pre-check=0");
	}

	/**
	 * Cria o header do Content-Disposition para definir o nome do arquivo
	 * 
	 * @param filename
	 * @return
	 */
	private String createPDFContentDisposition(String filename) {
		StringBuilder contentDisposition = new StringBuilder("form-data; name=\"");
		contentDisposition.append(filename).append('\"');
		contentDisposition.append("; filename=\"");
		contentDisposition.append(filename).append('\"');
		return contentDisposition.toString();
	}
	
}
