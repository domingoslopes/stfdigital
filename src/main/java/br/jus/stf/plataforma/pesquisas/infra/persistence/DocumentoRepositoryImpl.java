package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.Documento;
import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.DocumentoRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.Indice;
import br.jus.stf.plataforma.pesquisas.domain.model.indexacao.SubDocumento;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository("documentoIndexadoRepository")
public class DocumentoRepositoryImpl implements DocumentoRepository {

	private static final String SCRIPT_UPDATE_ITEM_COLECAO = "ctx._source.%s.each{item -> if (item.%s == id) { fields.each{ index, value -> item[index] = value } }}";

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public void criar(final Indice indice) {
		if (!elasticsearchTemplate.indexExists(indice.nome())) {
			elasticsearchTemplate.createIndex(indice.nome(), indice.configuracao());
		}
		indice.mapeamentos().forEach((tipo, mapeamento) ->
			elasticsearchTemplate.putMapping(indice.nome(), tipo, mapeamento));
		elasticsearchTemplate.refresh(indice.nome(), true);
	}
	
	@Override
	public void salvar(final Documento documento) {
		IndexQuery query = new IndexQueryBuilder()
			.withIndexName(documento.indice().nome())
			.withType(documento.tipo())
			.withSource(documento.objeto())
			.build();
		Optional.ofNullable(documento.id()).ifPresent(query::setId);
		elasticsearchTemplate.index(query);
	}

	@Override
	public void atualizar(final Documento documento) {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.doc(documento.objeto());
		updateRequest.upsert(documento.objeto());
		UpdateQuery query = new UpdateQueryBuilder()
			.withId(documento.id())
			.withType(documento.tipo())
			.withIndexName(documento.indice().nome())
			.withUpdateRequest(updateRequest)
			.withDoUpsert(true)
			.build();
		elasticsearchTemplate.update(query);
	}

	@Override
	public void atualizarColecao(final SubDocumento documento) {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.script(String.format(SCRIPT_UPDATE_ITEM_COLECAO, documento.campoColecao(), documento.expressaoId()));
		updateRequest.scriptParams(criarScriptParams(documento));
		UpdateQuery query = new UpdateQueryBuilder()
			.withId(documento.id())
			.withType(documento.tipo())
			.withIndexName(documento.indice().nome())
			.withUpdateRequest(updateRequest)
			.build();
		elasticsearchTemplate.update(query);
	}

	private Map<String, Object> criarScriptParams(SubDocumento documento) {
		Map<String, Object> scriptParams = new HashMap<>();
		scriptParams.put("id", documento.idItem());
		scriptParams.put("fields", jsonToMap(documento.objeto()));
		return scriptParams;
	}

	private HashMap<String, Object> jsonToMap(String objeto) {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {	};
		
		try {
			return mapper.readValue(objeto, typeRef);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao converter JSON do objeto.", e);
		}
	}

}
