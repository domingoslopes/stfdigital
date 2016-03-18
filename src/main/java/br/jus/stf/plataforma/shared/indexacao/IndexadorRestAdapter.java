package br.jus.stf.plataforma.shared.indexacao;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.jus.stf.plataforma.pesquisas.interfaces.IndexadorRestResource;
import br.jus.stf.plataforma.pesquisas.interfaces.command.AtualizarColecaoCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.AtualizarCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.CriarIndiceCommand;
import br.jus.stf.plataforma.pesquisas.interfaces.command.IndexarCommand;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
@Component
public class IndexadorRestAdapter {

	private IndexadorRestResource indexadorRestResource;
	
	private ObjectWriter objectWriter;
	
	private ObjectReader objectReader;
	
	@Autowired
	public IndexadorRestAdapter(IndexadorRestResource indexadorRestResource) {
		this.indexadorRestResource = indexadorRestResource;
		loadMapper();
	}
		
	/**
	 * @param indice
	 * @param configuracao
	 * @throws Exception
	 */
	public void criarIndice(String indice, String configuracao) throws Exception {
		JsonNode json = objectReader.readTree(configuracao);
		CriarIndiceCommand command = new CriarIndiceCommand(indice, json);
		indexadorRestResource.criarIndice(command, new BeanPropertyBindingResult(command, "criarIndiceCommand"));
	}
	
	/**
	 * Indexar uma entidade
	 * 
	 * @param indice
	 * @param objeto
	 * @throws Exception
	 */
	public void indexar(String indice, Entity<?, ?> objeto) throws Exception {
		try {
			IndexarCommand indexarCommand = criarComandoIndexacao(indice, objeto);
			indexadorRestResource.indexar(indexarCommand, new BeanPropertyBindingResult(indexarCommand, "indexarCommand"));
		} catch (Exception e) {
			throw new Exception("Não foi possível indexar o objeto!", e);
		}
	}
	
	/**
	 * Indexar um mapa customizado
	 * 
	 * @param indice
	 * @param id
	 * @param tipo
	 * @param mapaDeIndexacao
	 * @throws Exception
	 */
	public void indexar(String indice, String id, String tipo, Map<String, Object> mapaDeIndexacao) throws Exception {
		try {
			indexarOuAtualizar(indice, id, tipo, mapaDeIndexacao);
		} catch (Exception e) {
			throw new Exception("Não foi possível indexar o objeto!", e);
		}
	}
	
	/**
	 * Atualiza um objeto indexado
	 * 
	 * @param indice
	 * @param id
	 * @param tipo
	 * @param mapaDeAtualizacao
	 * @throws Exception
	 */
	public void atualizar(String indice, String id, String tipo, Map<String, Object> mapaDeAtualizacao) throws Exception {
		try {
			indexarOuAtualizar(indice, id, tipo, mapaDeAtualizacao);
		} catch (Exception e) {
			throw new Exception("Não foi possível atualizar o objeto!", e);
		}
	}
	
	/**
	 * Atualiza um item de coleção de um objeto indexado 
	 * 
	 * @param indice
	 * @param id
	 * @param tipo
	 * @param campoColecao
	 * @param expressaoId
	 * @param idItem
	 * @param mapaDeAtualizacao
	 * @throws Exception
	 */
	public void atualizarItemDeColecao(String indice, String id, String tipo, String campoColecao, String expressaoId, Object idItem, Map<String, Object> mapaDeAtualizacao) throws Exception {
		try {
			AtualizarColecaoCommand atualizarCommand = criarComandoAtualizacaoColecao(indice, id, tipo, campoColecao, expressaoId, idItem, mapaDeAtualizacao);
			indexadorRestResource.atualizarColecao(atualizarCommand, new BeanPropertyBindingResult(atualizarCommand, "atualizarCommand"));
		} catch (Exception e) {
			throw new Exception("Não foi possível atualizar o objeto!", e);
		}
	}
	
	/**
	 * Indexa ou atualiza um objeto com um mapa
	 * 
	 * @param indice
	 * @param id
	 * @param tipo
	 * @param mapaDeAtualizacao
	 * @throws Exception
	 */
	private void indexarOuAtualizar(String indice, String id, String tipo, Map<String, Object> mapaDeAtualizacao) throws Exception {
		AtualizarCommand atualizarCommand = criarComandoAtualizacao(indice, id, tipo, mapaDeAtualizacao);
		indexadorRestResource.atualizar(atualizarCommand, new BeanPropertyBindingResult(atualizarCommand, "atualizarCommand"));
	}

	/**
	 * Cria o comando de indexação
	 * 
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
	 * Cria o comando de atualização
	 * 
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
	
	/**
	 * Cria o comando de atualização de um item de coleção
	 * 
	 * @param indice
	 * @param id
	 * @param tipo
	 * @param campoColecao
	 * @param expressaoId
	 * @param idItem
	 * @param mapaDeAtualizacao
	 * @return
	 * @throws IOException
	 */
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
		String jsonString = objectWriter.writeValueAsString(objeto);
		return objectReader.readTree(jsonString);
	}
	
	/**
	 * Carrega os objetos de mapeamento 
	 */
	private void loadMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectWriter = mapper.writer();
		objectReader = mapper.reader();
	}
	
}
