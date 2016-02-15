package br.jus.stf.plataforma.monitoring.method;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Aspecto abstrato para monitoramento de chamadas de métodos.
 * 
 * @author Tomas.Godoi
 *
 */
public abstract class MethodMonitoringAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodMonitoringAspect.class);
	@Autowired
	protected MethodMonitoringReporter monitoringReporter;

	/**
	 * 
	 * Monitora a duração da operação.
	 * 
	 * @param proceedingJoinPoint
	 * @param methodCall
	 * @return
	 * @throws Throwable 
	 */
	protected Object monitorMethodCall(ProceedingJoinPoint proceedingJoinPoint, MethodCall methodCall) throws Throwable {
		Instant start = null;
		Instant end = null;
		Object value = null;
		methodCall.setClazz(proceedingJoinPoint.getTarget().getClass().getSimpleName());
		try {
			start = Instant.now();
			value = proceedingJoinPoint.proceed();
			end = Instant.now();
			methodCall.setErrorOcurred(false);
		} catch (Throwable e) {
			end = Instant.now(); // Não foi colocado no finally para não contabilizar o possível relançamento da exceção.
			methodCall.setErrorOcurred(true);
			throw e;
		} finally {
			if (start != null && end != null) {
				methodCall.setDurationInMillis(Duration.between(start, end).toMillis());
				long durationInMillis = Duration.between(start, end).toMillis();
				LOGGER.debug("Demorou " + durationInMillis + "ms ");
			}
		}
		return value;
	}
}
