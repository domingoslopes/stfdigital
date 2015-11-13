package br.jus.stf.plataforma.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Monitora o processamento de requisições específicas ({@link MonitoredRequest}), coletando 
 * informações de uso, status e performance.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M4
 * @since 16.10.2015
 */
@Component
public class RequestMonitor {
	
	@Autowired
	private RequestTraceReporter requestTraceReporter;
	
	/**
	 * Monitora todo o ciclo de vida da requisição, coletando informações de identificação, erros e performance.
	 * 
	 * @param monitoredRequest a requisição que se deseja monitorar
	 */
	public void monitor(MonitoredRequest monitoredRequest) {
		RequestTrace requestTrace = beforeExecution(monitoredRequest);
		try {
			monitoredRequest.execute(requestTrace);
			if (monitoredRequest.hasFailed()) {
				requestTrace.record(monitoredRequest.exception());
			}
		} finally {
			afterExecution(requestTrace);
		}
	}

	/**
	 * Inicia o tracing da requisição, extraindo informações de identificação (URL, Método e Usuário)
	 * 
	 * @param monitoredRequest a requisição sendo monitorada
	 * @return o objeto com informações iniciais de identificação
	 */
	private RequestTrace beforeExecution(MonitoredRequest monitoredRequest) {
		return new RequestTrace(monitoredRequest.method(), monitoredRequest.url(), monitoredRequest.username());
	}

	/**
	 * Finaliza o tracing da requisição, extraindo informações de resultado (erros e performance).
	 * 
	 * @param requestTrace o objeto populado com informações de resultado
	 */
	private void afterExecution(RequestTrace requestTrace) {
		requestTraceReporter.reportRequestTrace(requestTrace);
	}
}
