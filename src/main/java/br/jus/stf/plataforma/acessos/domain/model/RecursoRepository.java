package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;



public interface RecursoRepository {
	
	public Recurso findOne(Long sequencial);
	
	public Recurso findOne(String nome, TipoRecurso tipoRecurso);
	
	public Recurso save(Recurso recurso);
	
	public List<Recurso> findAllRecurso();

}