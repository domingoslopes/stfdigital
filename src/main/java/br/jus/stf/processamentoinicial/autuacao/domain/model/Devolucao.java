package br.jus.stf.processamentoinicial.autuacao.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.ModeloId;
import br.jus.stf.shared.TextoId;
import br.jus.stf.shared.stereotype.ValueObject;

@Embeddable
public class Devolucao implements ValueObject<Devolucao> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "DSC_MOTIVO_DEVOLUCAO")
	private String motivo;
	
	@Embedded
	@AttributeOverride(name = "sequencial", column = @Column(name = "SEQ_TEXTO_DEVOLUCAO"))
	private TextoId texto;
	
	@Embedded
	@AttributeOverride(name = "sequencial", column = @Column(name = "SEQ_MODELO_DEVOLUCAO"))
	private ModeloId modelo;
	
	
	Devolucao() {
		
	}
	
	public Devolucao(final String motivo) {
		Validate.notBlank(motivo, "devolucao.motivo.required");
		
		this.motivo = motivo;
	}
	
	public Devolucao(final String motivo, final TextoId texto, final ModeloId modelo) {
		this(motivo);
		
		Validate.notNull(texto, "motivoDevolucao.texto.required");
		Validate.notNull(modelo, "motivoDevolucao.modelo.required");
		
		this.texto = texto;
		this.modelo = modelo;
	}
	
	public String motivo() {
		return motivo;
	}
	
	public TextoId texto() {
		return texto;
	}
	
	public ModeloId modelo(){
		return modelo;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(motivo)
				.append(texto).append(modelo).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
	
		Devolucao other = (Devolucao) obj;
		
		return sameValueAs(other);
	}

	@Override
	public boolean sameValueAs(final Devolucao other) {
		return other != null
				&& motivo.equals(other.motivo)
				&& texto.equals(other.texto)
				&& modelo.equals(other.modelo);
	}

}
