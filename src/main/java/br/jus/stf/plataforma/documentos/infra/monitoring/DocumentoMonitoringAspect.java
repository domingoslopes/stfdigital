package br.jus.stf.plataforma.documentos.infra.monitoring;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumentoDownload;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;

/**
 * Aspecto para monitoramento do armazenamento de documentos.
 * 
 * @author Tomas.Godoi
 *
 */
@Aspect
@Component
public class DocumentoMonitoringAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoMonitoringAspect.class);
	@Autowired
	private DocumentoMonitoringReporter monitoringReporter;

	/**
	 * Monitora a operação save().
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 */
	@Around("execution(* br.jus.stf.plataforma.documentos.infra.persistence.ConteudoDocumentoRepository.save(..))")
	public Object saveMonitoringAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		DocumentoOperation documentoOperation = new DocumentoOperation();
		try {
			Object[] args = proceedingJoinPoint.getArgs();
			DocumentoTemporario temp = (DocumentoTemporario) args[1];
			documentoOperation.setSize(temp.tamanho());
			documentoOperation.setOperation("save()");
			Object value = monitorOperation(proceedingJoinPoint, documentoOperation);
			String id = (String) value;
			documentoOperation.setId(id);
			return value;
		} finally {
			monitoringReporter.reportDocumentoOperation(documentoOperation);
		}
	}

	/**
	 * Monitora a operação downloadConteudo()
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 */
	@Around("execution(* br.jus.stf.plataforma.documentos.infra.persistence.ConteudoDocumentoRepository.downloadConteudo(..))")
	public Object downloadMonitoringAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		DocumentoOperation documentoOperation = new DocumentoOperation();
		try {
			Object[] args = proceedingJoinPoint.getArgs();
			String id = (String) args[0];
			documentoOperation.setId(id);
			documentoOperation.setOperation("downloadConteudo()");
			Object value = monitorOperation(proceedingJoinPoint, documentoOperation);
			ConteudoDocumentoDownload cdd = (ConteudoDocumentoDownload) value;
			documentoOperation.setSize(cdd.tamanho());
			return value;
		} finally {
			monitoringReporter.reportDocumentoOperation(documentoOperation);
		}
	}

	/**
	 * 
	 * Monitora a duração da operação.
	 * 
	 * @param proceedingJoinPoint
	 * @param documentoOperation
	 * @return
	 */
	private Object monitorOperation(ProceedingJoinPoint proceedingJoinPoint, DocumentoOperation documentoOperation) {
		Instant start = null;
		Instant end = null;
		Object value = null;
		documentoOperation.setPersisterClass(proceedingJoinPoint.getTarget().getClass().getSimpleName());
		try {
			start = Instant.now();
			value = proceedingJoinPoint.proceed();
			end = Instant.now();
			documentoOperation.setErrorOcurred(false);
		} catch (Throwable e) {
			end = Instant.now(); // Não foi colocado no finally para não contabilizar a possível exceção.
			documentoOperation.setErrorOcurred(true);
			throw new RuntimeException("Erro ao monitorar duração do armazenamento do documento.", e);
		} finally {
			if (start != null && end != null) {
				documentoOperation.setDurationInMillis(Duration.between(start, end).toMillis());
				long durationInMillis = Duration.between(start, end).toMillis();
				LOGGER.debug("Demorou " + durationInMillis + "ms ");
			}
		}
		return value;
	}
}
