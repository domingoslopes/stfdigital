package br.jus.stf.plataforma.shared.indexacao;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.AtualizarColecaoCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.AtualizarCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.CriarIndiceCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;
import br.jus.stf.shared.stereotype.Entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class IndexadorRestAdapter implements InitializingBean {

	@Autowired
	private IndexadorRestResource indexadorRestResource;
	
	private ObjectMapper objectMapper;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	public void criarIndice(String indice, String configuracao) throws Exception {
		JsonNode json = objectMapper.readTree(configuracao);
		CriarIndiceCommand command = new CriarIndiceCommand(indice, json);
		indexadorRestResource.criarIndice(command, new BeanPropertyBindingResult(command, "criarIndiceCommand"));
	}
	
	public void indexar(String indice, Entity<?, ?> objeto) throws Exception {
		try {
			IndexarCommand indexarCommand = criarComandoIndexacao(indice, objeto);
			indexadorRestResource.indexar(indexarCommand, new BeanPropertyBindingResult(indexarCommand, "indexarCommand"));
		} catch (Exception e) {
			throw new Exception("Não foi possível indexar o objeto!", e);
		}
	}
	
	public void indexar(String indice, String id, String tipo, Map<String, Object> mapaDeIndexacao) throws Exception {
		try {
			indexarOuAtualizar(indice, id, tipo, mapaDeIndexacao);
		} catch (Exception e) {
			throw new Exception("Não foi possível indexar o objeto!", e);
		}
	}
	
	public void atualizar(String indice, String id, String tipo, Map<String, Object> mapaDeAtualizacao) throws Exception {
		try {
			indexarOuAtualizar(indice, id, tipo, mapaDeAtualizacao);
		} catch (Exception e) {
			throw new Exception("Não foi possível atualizar o objeto!", e);
		}
	}
	
	public void atualizarItemDeColecao(String indice, String id, String tipo, String campoColecao, String expressaoId, Object idItem, Map<String, Object> mapaDeAtualizacao) throws Exception {
		try {
			AtualizarColecaoCommand atualizarCommand = criarComandoAtualizacaoColecao(indice, id, tipo, campoColecao, expressaoId, idItem, mapaDeAtualizacao);
			indexadorRestResource.atualizarColecao(atualizarCommand, new BeanPropertyBindingResult(atualizarCommand, "atualizarCommand"));
		} catch (Exception e) {
			throw new Exception("Não foi possível atualizar o objeto!", e);
		}
	}
	
	private void indexarOuAtualizar(String indice, String id, String tipo, Map<String, Object> mapaDeAtualizacao) throws Exception {
		AtualizarCommand atualizarCommand = criarComandoAtualizacao(indice, id, tipo, mapaDeAtualizacao);
		indexadorRestResource.atualizar(atualizarCommand, new BeanPropertyBindingResult(atualizarCommand, "atualizarCommand"));
	}

	/**
	 * @param indice
	 * @param objeto
	 * @return
	 * @throws IOException
	 */
	private IndexarCommand criarComandoIndexacao(String indice, Entity<?, ?> objeto) throws IOException {
		IndexarCommand command = new IndexarCommand();
		command.setId(objeto.id().toString());
		command.setTipo(objeto.getClass().getSimpleName());
		command.setIndice(indice);
		command.setObjeto(criarJson(objeto));
		return command;
	}
	
	/**
	 * @param indice
	 * @param mapaDeAtualizacao
	 * @return
	 * @throws IOException
	 */
	private AtualizarCommand criarComandoAtualizacao(String indice, String id, String tipo, Map<String, Object> mapaDeAtualizacao) throws IOException {
		AtualizarCommand command = new AtualizarCommand();
		command.setId(id);
		command.setTipo(tipo);
		command.setIndice(indice);
		command.setObjeto(criarJson(mapaDeAtualizacao));
		return command;
	}
	
	private AtualizarColecaoCommand criarComandoAtualizacaoColecao(String indice, String id, String tipo, String campoColecao, String expressaoId, Object idItem, Map<String, Object> mapaDeAtualizacao) throws IOException {
		AtualizarColecaoCommand command = new AtualizarColecaoCommand();
		command.setId(id);
		command.setTipo(tipo);
		command.setIndice(indice);
		command.setCampoColecao(campoColecao);
		command.setExpressaoId(expressaoId);
		command.setIdItem(idItem);
		command.setObjeto(criarJson(mapaDeAtualizacao));
		return command;
	}	
	
	/**
	 * Cria um json para ser indexado
	 * 
	 * @param peticao
	 * @return
	 * @throws IOException
	 */
	private JsonNode criarJson(Object objeto) throws IOException {
		String jsonString = objectMapper.writeValueAsString(objeto);
		return objectMapper.readTree(jsonString);
	}
	
}
