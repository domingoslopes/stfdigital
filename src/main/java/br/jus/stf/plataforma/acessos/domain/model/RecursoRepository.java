package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;

import br.jus.stf.plataforma.shared.security.resource.ResourceType;



public interface RecursoRepository {
	
	public Recurso findOne(Long sequencial);
	
	public Recurso findOne(String nome, ResourceType tipoRecurso);
	
	public Recurso save(Recurso recurso);
	
	public List<Recurso> findAllRecurso();

}