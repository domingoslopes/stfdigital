package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.stereotype.ValueObject;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Pesquisa implements ValueObject<Pesquisa> {
	
    private static final long serialVersionUID = 1L;

	private String[] campos;
	private String[] tipos;
	private String[] indices;
	private Map<String, String[]> filtros = new HashMap<String, String[]>();
	private Map<String, String> ordenadores = new HashMap<String, String>(0);
	private String campoAgregacao;
	private String consulta;
	
	public Pesquisa(final String[] indices, final Map<String, String[]> filtros) {
		Validate.notEmpty(indices, "pesquisa.indices.required");
		Validate.notEmpty(filtros, "pesquisa.filtros.required");
		
		this.indices = indices;
		this.filtros.putAll(filtros);
	}
	
	public Pesquisa(final String consulta, final String[] indices) {
		Validate.notEmpty(consulta, "pesquisa.consulta.required");
		Validate.notEmpty(indices, "pesquisa.indices.required");
		
		this.indices = indices;
		this.consulta = consulta;
	}
	
	public String[] campos() {
		return campos;
	}
	
	public String[] tipos() {
		return tipos;
	}
	
	public String[] indices() {
		return indices;
	}
	
	public Map<String, String[]> filtros() {
		return filtros;
	}
	
	public Map<String, String> ordenadores() {
		return ordenadores;
	}
	
	public Pesquisa comCampos(final String[] campos) {
		this.campos = campos;
		return this;
	}
	
	public Pesquisa comTipos(final String[] tipos) {
		this.tipos = tipos;
		return this;
	}
	
	public Pesquisa comOrdenadores(final Map<String, String> ordenadores) {
		Optional.ofNullable(ordenadores).ifPresent(this.ordenadores::putAll);
		return this;
	}
	
	public Pesquisa agregadoPeloCampo(String campoAgregacao) {
		this.campoAgregacao = campoAgregacao;
		return this;
	}
	
	public String campoAgregacao() {
		return this.campoAgregacao;
	}
	
	public String consulta() {
		return this.consulta;
	}
	
	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
		result = prime * result + ((campoAgregacao == null) ? 0 : campoAgregacao.hashCode());
		result = prime * result + Arrays.hashCode(campos);
		result = prime * result + ((filtros == null) ? 0 : filtros.hashCode());
		result = prime * result + Arrays.hashCode(indices);
		result = prime * result + ((ordenadores == null) ? 0 : ordenadores.hashCode());
		result = prime * result + Arrays.hashCode(tipos);
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj) {
	    	return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	    	return false;
	    }
	    Pesquisa other = (Pesquisa) obj;
	    return sameValueAs(other);
    }

	@Override
	public boolean sameValueAs(Pesquisa other) {
		if (campoAgregacao == null) {
			if (other.campoAgregacao != null) return false;
		} else if (!campoAgregacao.equals(other.campoAgregacao)) return false;
		if (!Arrays.equals(campos, other.campos)) return false;
		if (filtros == null) {
			if (other.filtros != null) return false;
		} else if (!filtros.equals(other.filtros)) return false;
		if (!Arrays.equals(indices, other.indices)) return false;
		if (ordenadores == null) {
			if (other.ordenadores != null) return false;
		} else if (!ordenadores.equals(other.ordenadores)) return false;
		if (!Arrays.equals(tipos, other.tipos)) return false;
	    return true;
	}

}
