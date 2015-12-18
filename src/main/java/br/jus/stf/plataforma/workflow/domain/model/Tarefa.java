package br.jus.stf.plataforma.workflow.domain.model;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.ProcessoWorkflowId;
import br.jus.stf.shared.TarefaId;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Tarefa implements Entity<Tarefa, TarefaId> {
	
	private TarefaId id;
	private String nome;
	private String descricao; 
	private Responsavel responsavel;
	private ProcessoWorkflowId processo;
	private Metadado metadado;
	
	public Tarefa(TarefaId id, String nome, String descricao, ProcessoWorkflowId processo, Metadado metadado) {
		Validate.notNull(id, "tarefa.id.required");
		Validate.notBlank(nome, "tarefa.nome.required");
		Validate.notBlank(descricao, "tarefa.descricao.required");
		Validate.notNull(processo, "tarefa.processo.required");
			
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.processo = processo;
		this.metadado = metadado;
	}
	
	@Override
	public TarefaId id() {
		return id;
	}
	
	public String nome() {
		return nome;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public Responsavel reponsavel() {
		return responsavel;
	}
	
	public ProcessoWorkflowId processo() {
		return processo;
	}
	
	public Metadado metadado() {
		return metadado;
	}
		
	public void atribuir(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public boolean sameIdentityAs(Tarefa other) {
		return other != null && id.equals(other.id);
	}
	
}
