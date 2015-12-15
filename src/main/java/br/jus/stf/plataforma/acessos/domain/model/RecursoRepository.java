package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;

import br.jus.stf.plataforma.shared.security.resource.ResourceType;
import br.jus.stf.shared.RecursoId;



public interface RecursoRepository {
	
	public Recurso findOne(Long sequencial);
	
	public Recurso findOne(String nome, ResourceType tipoRecurso);
	
	public Recurso save(Recurso recurso);
	
	public List<Recurso> findAllRecurso();
	
	public List<Permissao> findPermissaoByRecurso(RecursoId id);

}