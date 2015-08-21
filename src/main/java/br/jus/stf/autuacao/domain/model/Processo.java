package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public class Processo implements Entity<Processo> {

	private ClasseId classe;
	private ProcessoId numeroProcesso;
	private MinistroId ministroRelator;
	private PeticaoId peticao;
	private List<Parte> partes;
	private List<DocumentoId> pecas;

	// Hibernate
	Processo(){

	}

	/**
	 * 
	 * @param classe
	 * @param numero
	 * @param ministroRelator
	 * @param peticao
	 * @param partes
	 * @param pecas
	 */
	public Processo(final ClasseId classe, final ProcessoId numeroProcesso, final MinistroId ministroRelator, final PeticaoId peticao, List<Parte> partes, List<DocumentoId> pecas){
		Validate.notNull(classe, "processo.classe.required");
		Validate.notNull(numeroProcesso, "processo.numeroProcesso.required");
		Validate.notNull(ministroRelator, "processo.ministroRelator.required");
		Validate.notNull(peticao, "processo.peticao.required");
		Validate.notEmpty(partes, "processo.partes.notEmpty");
		Validate.noNullElements(partes, "processo.partes.noNullElements");
		Validate.notEmpty(pecas, "processo.pecas.notEmpty");
		Validate.noNullElements(pecas, "processo.pecas.noNullElements");
	
		this.classe = classe;
		this.numeroProcesso = numeroProcesso;
		this.ministroRelator = ministroRelator;
		this.peticao = peticao;
		this.partes = partes;
		this.pecas = pecas;
	}

	public ClasseId classe(){
		return this.classe;
	}

	public ProcessoId numeroProcesso(){
		return this.numeroProcesso;
	}

	public MinistroId ministroRelator(){
		return this.ministroRelator;
	}

	public PeticaoId peticao(){
		return this.peticao;
	}

	public List<Parte> partesPoloAtivo(){
		return Collections.unmodifiableList(partes.stream()
		  .filter(p -> p.polo() == TipoPolo.POLO_ATIVO)
		  .collect(Collectors.toList()));
	}

	public List<Parte> partesPoloPassivo(){
		return Collections.unmodifiableList(partes.stream()
		  .filter(p -> p.polo() == TipoPolo.POLO_PASSIVO)
		  .collect(Collectors.toList()));
	}

	public List<DocumentoId> pecas(){
		return Collections.unmodifiableList(pecas);
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameIdentityAs(Processo other){
		return other != null && this.classe.sameValueAs(other.classe) && this.numeroProcesso.sameValueAs(other.numeroProcesso);
	}

}