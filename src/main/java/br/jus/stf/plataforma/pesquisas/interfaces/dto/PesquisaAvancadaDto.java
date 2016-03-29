package br.jus.stf.plataforma.pesquisas.interfaces.dto;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * @author Lucas.Rodrigues
 *
 */
public class PesquisaAvancadaDto {
	
	private Long id;
	private String nome;
	private String tipo;
	private JsonNode consulta;
	private String[] indices;
	
	public PesquisaAvancadaDto(Long id, String nome, String tipo, JsonNode consulta, String[] indices) {
	    this.id = id;
	    this.nome = nome;
	    this.tipo = tipo;
	    this.consulta = consulta;
	    this.indices = indices;
    }
	
    public Long getId() {
    	return id;
    }
	
    public String getNome() {
    	return nome;
    }
    
    public String getTipo() {
    	return tipo;
    }
	
    public JsonNode getConsulta() {
    	return consulta;
    }
	
    public String[] getIndices() {
    	return indices;
    }
	
}
