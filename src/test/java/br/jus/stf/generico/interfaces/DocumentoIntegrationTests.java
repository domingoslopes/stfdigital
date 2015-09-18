package br.jus.stf.generico.interfaces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import br.jus.stf.AbstractIntegrationTests;
import br.jus.stf.generico.interfaces.dto.DocumentoDto;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class DocumentoIntegrationTests extends AbstractIntegrationTests {
	
	@Test
	public void enviarArquivo() throws Exception {
		String nomeArquivo = "teste_arq_temp.pdf";
		String mime = "application/pdf";
		String caminho = "pdf/archimate.pdf";
		
		byte[] arquivo = IOUtils.toByteArray(new ClassPathResource(caminho).getInputStream());

	    MockMultipartFile mockArquivo = new MockMultipartFile("file", nomeArquivo, mime, arquivo);
		
	    String idTemp = mockMvc.perform(fileUpload("/api/documentos/upload")
	    			.file(mockArquivo)
	    			.contentType(MediaType.MULTIPART_FORM_DATA)
	    			.content(arquivo))
	    	.andExpect(status().is2xxSuccessful())
	    	.andReturn().getResponse().getContentAsString();
	    
	    String json = mockMvc.perform(post("/api/documentos")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"documentos\" : [\"" + idTemp + "\"]}"))
			.andExpect(status().is2xxSuccessful())
			.andReturn().getResponse().getContentAsString();
	    
		JavaType type = TypeFactory.defaultInstance()
				.constructParametricType(ArrayList.class, DocumentoDto.class);
		List<DocumentoDto> dtos = new ObjectMapper().readValue(json, type); 
	 
	    mockMvc.perform(get("/api/documentos/" + dtos.get(0).getDocumentoId()))
	    	.andExpect(status().isOk())
	    	.andExpect(content().bytes(arquivo));
	}
}
