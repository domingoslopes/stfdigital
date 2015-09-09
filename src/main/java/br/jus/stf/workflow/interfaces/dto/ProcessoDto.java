package br.jus.stf.workflow.interfaces.dto;

import java.util.Map;

public class ProcessoDto {
	private String id;
	private Map<String, Object> variaveis;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<String, Object> getVariaveis() {
		return variaveis;
	}
	public void setVariaveis(Map<String, Object> variaveis) {
		this.variaveis = variaveis;
	}
	
	public ProcessoDto(){
		
	}
	
	public ProcessoDto(String id, Map<String, Object> variaveis){
		this.id = id;
		this.variaveis = variaveis;
	}
}
