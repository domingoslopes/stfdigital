package br.jus.stf.autuacao.domain;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.autuacao.domain.model.Peticao;


/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Component
public class PeticaoService {

	private static Map<String, File> arquivosTemporarios = new HashMap<String, File>();
	private static String EXTENSAO_ARQUIVO = ".pdf";
	private static final String ID_ARQUIVO_TEMP_PETICIONAMENTO = "arq_temp_pet_";
	
	@Autowired
	private ProcessoAdapter processoAdapter;

	@Autowired
	private TarefaAdapter tarefaAdapter;

	@Deprecated
	public void preautuar(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

	@Deprecated
	public void autuar(String idPeticao, boolean peticaoValida, String motivo) {
		if (peticaoValida) {
			tarefaAdapter.completar(idPeticao);
		} else {
			tarefaAdapter.sinalizar("Petição Inválida", idPeticao);
		}
	}

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Deprecated
	public String distribuir(String idPeticao, String relator) {
		String idProcesso = "";
		
		tarefaAdapter.completar(idPeticao);
		
		return idProcesso;
	}

	@Deprecated
	public void devolver(String idPeticao) {
		tarefaAdapter.completar(idPeticao);
	}

	/**
	 * Recupera uma petição de acordo com o id informado.
	 * @param id - Identificador da petição.
	 * @return Dados da petição.
	 */
	public Peticao consultar(String id){
		return null;
	}
	
	/**
	 * Grava um arquivo recebido pelo processo de peticionamento.
	 * @param arquivo Arquivo recebido.
	 * @return Identificador do arquivo.
	 * @throws IOException
	 */
	public String gravarArquivo(MultipartFile arquivo) throws IOException{
		String idArquivoTemporario = "";
		File arquivoTemporario = null;
		
		arquivoTemporario = this.gravarArquivoTemporario(arquivo);
		idArquivoTemporario = this.adicionarArquivoMapa(arquivoTemporario);
		
		return idArquivoTemporario;
	}
	
	/**
	 * Grava o arquivo no repositório temporário.
	 * @param arquivo Arquivo recebido.
	 * @return Objeto contendo as informações do arquivo.
	 * @throws IOException
	 */
	private File gravarArquivoTemporario(MultipartFile arquivo) throws IOException{
		File arquivoTemporario = File.createTempFile(ID_ARQUIVO_TEMP_PETICIONAMENTO, EXTENSAO_ARQUIVO);
		arquivo.transferTo(arquivoTemporario);
		return arquivoTemporario;
	}
	
	/**
	 * Adiciona um arquivo ao mapa de arquivos recebidos.
	 * @param arquivo Arquivo recebido.
	 * @return Identificador do arquivo adicionado.
	 * @throws IOException
	 */
	private String adicionarArquivoMapa(File arquivo) throws IOException {
		String idArquivoTemporaio = arquivo.getName();
		arquivosTemporarios.put(idArquivoTemporaio, arquivo);
		return idArquivoTemporaio;
	}
	
	/**
	 * Exclui um arquivo temporário.
	 * @param idArquivoTemporario Identificador do arquivo.
	 */
	public void excluirArquivoTemporario(String idArquivoTemporario){
		File arquivoTemporario = arquivosTemporarios.get(idArquivoTemporario);
		arquivosTemporarios.remove(idArquivoTemporario);
		arquivoTemporario.delete();
	}
	
	/**
	 * Recupera um arquivo temporário.
	 * @param id Identificador do arquivo temporário.
	 * @return O arquivo temporário.
	 */
	public File consultarArquivoTemporario(String id){
		return arquivosTemporarios.get(id);
	}
}
