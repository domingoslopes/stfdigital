package br.jus.stf.plataforma.documentos.infra.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumentoDownload;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.monitoring.method.MethodCall;
import br.jus.stf.plataforma.monitoring.method.MethodMonitoringAspect;

/**
 * Aspecto para monitoramento de chamadas de métodos de armazenamento/recuperação de documentos.
 * 
 * @author Tomas.Godoi
 *
 */
@Aspect
@Component
public class DocumentoMonitoringAspect extends MethodMonitoringAspect {

	private static final String MONITOR_GROUP = "PersistenciaDocumento";
	
	/**
	 * Monitora a operação save().
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* br.jus.stf.plataforma.documentos.infra.persistence.ConteudoDocumentoRepository.save(..))")
	public Object saveMonitoringAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodCall methodCall = new MethodCall();
		try {
			Object[] args = proceedingJoinPoint.getArgs();
			DocumentoTemporario temp = (DocumentoTemporario) args[1];
			methodCall.setResourceSize(temp.tamanho());
			methodCall.setMethod("save()");
			methodCall.setMonitorGroup(MONITOR_GROUP);
			
			Object value = monitorMethodCall(proceedingJoinPoint, methodCall);
			
			String id = (String) value;
			methodCall.setResourceId(id);
			return value;
		} finally {
			monitoringReporter.reportDocumentoOperation(methodCall);
		}
	}

	/**
	 * Monitora a operação downloadConteudo()
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* br.jus.stf.plataforma.documentos.infra.persistence.ConteudoDocumentoRepository.downloadConteudo(..))")
	public Object downloadMonitoringAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodCall methodCall = new MethodCall();
		try {
			Object[] args = proceedingJoinPoint.getArgs();
			String id = (String) args[0];
			methodCall.setResourceId(id);
			methodCall.setMethod("downloadConteudo()");
			methodCall.setMonitorGroup(MONITOR_GROUP);
			
			Object value = monitorMethodCall(proceedingJoinPoint, methodCall);
			
			ConteudoDocumentoDownload cdd = (ConteudoDocumentoDownload) value;
			methodCall.setResourceSize(cdd.tamanho());
			return value;
		} finally {
			monitoringReporter.reportDocumentoOperation(methodCall);
		}
	}
}
