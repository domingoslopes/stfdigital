package br.jus.stf.plataforma.action.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface ActionRepository extends JpaRepository<Action, ActionId>, JpaSpecificationExecutor<Action> {

	List<Action> findByContextAndResourcesInfo_Type(String context, String resourcesType);

	List<Action> findByResourcesInfo_Type(String resourcesType);
	
}
