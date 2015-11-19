package br.jus.stf.plataforma.monitoring;

import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Encapsula as informações extraídas durante o monitoramento de uma dada requisição. 
 * Contém informações de identificação (URL, Usuário), defeitos (Causa de Erros, 
 * Status do Processamento) e perfomance (Tempo de Processamento).
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M4
 * @since 16.10.2015
 */
public class RequestTrace {
	
	@JsonProperty("@timestamp")
	private final String timestamp;
	
	private String usuario;

	private String method;
	
	private String causa;
	
	private String url;
	
	private int status;

	private long performance;

	/**
	 * Cria uma nova instância da classe com informações de identificação da requisição
	 * e o do usuário solicitante (usuário logado na aplicação)
	 * 
	 * @param method o método da requisição (POST, GET e etc.)
	 * @param url a URL da requisição (URL da API)
	 * @param usuario o usuário solicitante
	 */
	public RequestTrace(String method, String url, String usuario) {
		this.timestamp = now().format(ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
		this.method = method;
		this.url = url;
		this.usuario = usuario;
	}

	/**
	 * @return o método da requisição (POST, GET e etc.)
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * @return a URL da requisição (URL da API)
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @return o usuário solicitante (usuário logado na aplicação)
	 */
	public String getUsuario() {
		return usuario;
	}
	
	/**
	 * @return o código de status com o resultado do processamento da requisição
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * @return o tempo total de processamento da requisição, em milisegundos
	 */
	public long getPerformance() {
		return performance;
	}
	
	/**
	 * @return a stacktrace completa da exceção gerada durante o processamento da requisição
	 */
	public String getCausa() {
		return causa;
	}
	
	/**
	 * Registra a stacktrace da exceção gerada durante o processamento da requisição
	 * 
	 * @param causa a stacktrace completa
	 */
	public void record(String causa) {
		this.causa = causa;
	}
	
	/**
	 * Registra o código de status final, após o processamento da requisição
	 * 
	 * @param status o código de status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Registra o tempo de processamento da requisição, em milisegundos
	 * 
	 * @param performance o tempo de processamento
	 */
	public void setPerformance(long performance) {
		this.performance = performance;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Requisição [metodo: %s]  [url: %s] [usuario: %s] [status: %s] [performance: %s]", method, url, usuario, status, performance);
	}

}
