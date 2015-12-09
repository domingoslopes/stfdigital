package br.jus.stf.plataforma.shared.security;

import java.util.Collection;
import java.util.Optional;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;

/**
 * Implementação do handler para os métodos anotados com PreAuthorize, PreFilter,
 * PostAuthorize e PostFilter. O default para o PreFilter e PostFilter filtra apenas
 * coleções e arrays, assim a implementação proposta trata quando o retorno é um objeto
 * que pode ser filtrado.<br/>
 * O filtro pode retornar o valor filtrado se permitido, senão um Optional vazio caso o valor
 * filtrado seja um Optional ou nulo.
 * 
 * @author Lucas.Rodrigues
 *
 */
@Component
public class MethodSecurityExpressionHandlerImpl extends DefaultMethodSecurityExpressionHandler {

	@Override
	public Object filter(Object filterTarget, Expression filterExpression, EvaluationContext ctx) {
		
		MethodSecurityExpressionOperations rootObject = (MethodSecurityExpressionOperations) ctx.getRootObject().getValue();
		final boolean debug = logger.isDebugEnabled();
		
		if (filterTarget instanceof Collection || filterTarget.getClass().isArray()) {
			super.filter(filterTarget, filterExpression, ctx);
		}
		
		if (filterTarget != null) {
			
			if (debug) {
				logger.debug("Filtering object");
			}
			rootObject.setFilterObject(filterTarget);
			
			if (ExpressionUtils.evaluateAsBoolean(filterExpression, ctx)) {
				return filterTarget;
			}
			
			if (Optional.class.isAssignableFrom(filterTarget.getClass())) {
				return Optional.empty();
			}
		}
		return null;
	}
	
}
