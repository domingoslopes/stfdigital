package br.jus.stf.plataforma.action.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface ActionRepository extends JpaRepository<Action, ActionId>, QueryDslPredicateExecutor<Action> {

	List<Action> findByContextAndResourcesInfo_Type(String context, String resourcesType);

	List<Action> findByResourcesInfo_Type(String resourcesType);
	
}
