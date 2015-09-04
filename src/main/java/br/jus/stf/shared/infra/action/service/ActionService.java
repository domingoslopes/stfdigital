package br.jus.stf.shared.infra.action.service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.jus.stf.shared.infra.action.handler.ActionConditionHandler;
import br.jus.stf.shared.infra.action.support.ActionConditionHandlerInfo;
import br.jus.stf.shared.infra.action.support.ActionMappingInfo;
import br.jus.stf.shared.infra.action.support.ResourcesMode;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;


/**
 * Serviço que permite listar as ações, verificar e executar uma ação específica
 * levando em conta as regras de permissão (papéis, {@link ResourcesMode}, {@link ActionConditionHandler})
 * 
 * @author Lucas.Rodrigues
 * 
 */
@Service
public class ActionService {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ActionMappingRegistry mappingRegistry;

	/**
	 * Retorna a lista de ações de ações registradas.
	 * 
	 * @return a lista de ações
	 */
	public Collection<ActionMappingInfo> listActions() {
		return mappingRegistry.getRegisteredActions();
	}
	
	/**
	 * Verifica se o usuário tem permissão sobre a ação e os recursos
	 * 
	 * @param id
	 * @param resources
	 * @return true se permitido, false caso contrário
	 */
	public boolean isAllowed(String id, ArrayNode resources) {
		ActionMappingInfo actionInfo = mappingRegistry.findRegisteredActionsById(id);
		List<?> converted = convertResources(resources, actionInfo);
		return isAllowed(actionInfo, converted);
	}
	
	/**
	 * Verifica as restrições e executa uma ação.
	 * 
	 * @param id
	 * @param resources
	 * @return objeto de retorno da ação
	 */
	public Object executeAction(String id, ArrayNode resources) {
		
		ActionMappingInfo actionInfo = mappingRegistry.findRegisteredActionsById(id);
		List<?> converted = convertResources(resources, actionInfo);
		Class<?> controllerClass = actionInfo.getControllerClass(); 
		
		if (isAllowed(actionInfo, converted)) {
			try {
				Object controller = applicationContext.getBean(controllerClass);
				Method method = BeanUtils
						.findDeclaredMethodWithMinimalParameters(controllerClass, actionInfo.getMethodName());
				if (method.getParameterCount() == 0) {
					return method.invoke(controller);
				} else if (ResourcesMode.One.equals(
						actionInfo.getResourcesMode())) {
					return method.invoke(controller, converted.get(0));
				}
				return method.invoke(controller, converted);
			} catch(Exception e) {
				throw new RuntimeException("Erro ao executar ação: " + actionInfo.getDescription(), e);
			}
		}
		throw new RuntimeException("Não foi permitida execução da ação: " + actionInfo.getDescription());
	}

	/**
	 * Verifica se uma ação deve ser exibida considerando os recursos de entrada.
	 * 
	 * <p>
	 * A ação e os recurso são submetidos aos handlers que definirão se a ação deve ser exibida ou não.
	 * 
	 * <p>
	 * Os handlers são definidos em função das anotações adicionadas à classe da ação. A cada anotação está relacionada um handler.
	 * 
	 * @param actionInfo as informações da ação
	 * @param resources os recursos selecionados
	 * @return true, se a ação pode ser listada ou executada, false, caso contrário
	 */
	private boolean isAllowed(ActionMappingInfo actionInfo, List<?> resources) {
		
		if (!actionInfo.isValidResourceMode(resources) || !actionInfo.hasNeededAuthorities()) {
			return false;
		}
			
		for (ActionConditionHandlerInfo handlerInfo : actionInfo.getActionHandlersInfo()) {
			return matches(handlerInfo, resources);
		}
		return true;
	}

	/**
	 * Converte a coleção de recursos para um tipo mapeado
	 * @param resources
	 * @param actionInfo
	 * @return os recursos covertidos para tipo infomado
	 */
	private List<?> convertResources(ArrayNode resources, ActionMappingInfo actionInfo) {
		if (ResourcesMode.None.equals(actionInfo.getResourcesMode())) {
			return Collections.emptyList();
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			JavaType type = TypeFactory.defaultInstance()
					.constructParametricType(List.class, actionInfo.getResourcesClass());
			return mapper.convertValue(resources, type);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao converter recursos!", e);
		}
	}

	/**
	 * Submete a ação a um dado handler, informando a anotação do handler e os recursos
	 * selecionados. O handler definirá se a ação deve, ou não, ser listada.
	 *
	 * @param handlerInfo as informações do handler
	 * @param resources os recursos selecionados
	 * @return true, se aprovado, false, caso contrário
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean matches(ActionConditionHandlerInfo handlerInfo, List<?> resources) {

		ActionConditionHandler handler = (ActionConditionHandler) applicationContext
																	.getBean(handlerInfo.getHandlerClass());
		Class<?> clazz = handlerInfo.getAnnotation().annotationType();
		return handler.matches(clazz.cast(handlerInfo.getAnnotation()), resources);
	}
	
}
