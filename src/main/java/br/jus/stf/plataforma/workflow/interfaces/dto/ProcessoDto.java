package br.jus.stf.plataforma.workflow.interfaces.dto;


public class ProcessoDto {
	private Long id;
	private String status;
	
	public ProcessoDto(){
		
	}
	
	public ProcessoDto(Long id, String status){
		this.id = id;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getStatus() {
		return status;
	}
	
}
