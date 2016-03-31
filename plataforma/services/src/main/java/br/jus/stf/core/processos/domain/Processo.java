package br.jus.stf.core.processos.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.02.2016
 */
@Document(indexName = "processos", type = "processo")
public class Processo {
	
    @Id
    private String id;
    
    @Field
    private Long protocoloId;
    
    @Field
    private String processoId;
    
    @Field
    private String numero;
    
    @Field(indexAnalyzer = "keyword")
    private String protocolo;
    
    @Field
    private Set<String> partes = new HashSet<>();
    
    public Processo() {
    	// O Parser JSON (Jackson) sempre usa o construtor default antes de popular uma nova instância.
	}
    
    public Processo(Long protocoloId, String protocolo) {
    	this.id = UUID.randomUUID().toString();
    	this.protocoloId = protocoloId;
		this.protocolo = protocolo;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    public String getId() {
		return id;
	}
    
    public void setProtocoloId(Long protocoloId) {
		this.protocoloId = protocoloId;
	}
    
    public Long getProtocoloId() {
		return protocoloId;
	}

    public void setProcessoId(String processoId) {
		this.processoId = processoId;
	}
    
    public String getProcessoId() {
		return processoId;
	}

    public void setNumero(String numero) {
		this.numero = numero;
	}
    
    public String getNumero() {
		return numero;
	}
    
    public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
    
    public String getProtocolo() {
		return protocolo;
	}
    
    public void setPartes(Set<String> partes) {
		this.partes = partes;
	}
    
    public Set<String> getPartes() {
		return partes;
	}
    
}
