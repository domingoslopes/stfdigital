package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.processamentoinicial.suporte.domain.DocumentoAdapter;
import br.jus.stf.processamentoinicial.suporte.domain.model.Modelo;
import br.jus.stf.processamentoinicial.suporte.domain.model.ModeloRepository;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ModeloDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.ModeloDtoAssembler;
import br.jus.stf.shared.ModeloId;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Serviços Rest de Modelo.
 * 
 * @author Tomas.Godoi
 *
 */
@RestController
@RequestMapping("/api/modelos")
public class ModeloRestResource {

	@Autowired
	private ModeloRepository modeloRepository;

	@Autowired
	private DocumentoAdapter documentoAdapter;

	@Autowired
	private ModeloDtoAssembler modeloDtoAssembler;

	@ApiOperation("Recupera os modelos existentes")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ModeloDto> listar() {
		List<Modelo> modelos = modeloRepository.findAll();
		return modelos.stream().map(modelo -> modeloDtoAssembler.toDto(modelo)).collect(Collectors.toList());
	}

	@ApiOperation("Recupera os dados de um modelo")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModeloDto consultar(@PathVariable("id") Long id) {
		Modelo modelo = modeloRepository.findOne(new ModeloId(id));
		return modeloDtoAssembler.toDto(modelo);
	}

	@ApiOperation("Recupera o conteúdo de um documento de modelo")
	@RequestMapping(value = "/{modeloId}/documento/conteudo.docx", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> recuperarConteudo(@PathVariable("modeloId") Long modeloId)
	        throws IOException {
		Modelo modelo = modeloRepository.findOne(new ModeloId(modeloId));
		InputStream is = documentoAdapter.recuperarConteudo(modelo.documento());
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

}
