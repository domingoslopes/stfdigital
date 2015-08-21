package br.jus.stf.autuacao.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

import br.jus.stf.shared.domain.model.ClasseId;
import br.jus.stf.shared.domain.model.DocumentoId;
import br.jus.stf.shared.domain.model.MinistroId;
import br.jus.stf.shared.domain.model.ProcessInstanceId;
import br.jus.stf.shared.domain.stereotype.Entity;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:25
 */
public class Peticao implements Entity<Peticao> {

	private PeticaoId numeroPeticao;
	private ClasseId classeSugerida;
	private List<Parte> partes;
	private List<DocumentoId> documentos;
	private ClasseId classeProcessual;
	private String motivoRecusa;
	private PeticaoStatus status;
	private ProcessInstanceId processInstanceId;
	private ProcessoRepository processoRepository;

	// Hibernate
	Peticao(){

	}

	/**
	 * 
	 * @param numeroPeticao
	 * @param classeSugerida
	 * @param partes
	 * @param documentos
	 */
	public Peticao(PeticaoId numeroPeticao, ClasseId classeSugerida, List<Parte> partes, List<DocumentoId> documentos){
		Validate.notNull(numeroPeticao, "peticao.numeroPeticao.required");
		Validate.notNull(classeSugerida, "peticao.classeSugerida.required");
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.noNullElements(partes, "peticao.partes.noNullElements");
		Validate.notEmpty(documentos, "peticao.documentos.notEmpty");
		Validate.noNullElements(documentos, "peticao.documentos.noNullElements");
	
		this.numeroPeticao = numeroPeticao;
		this.classeSugerida = classeSugerida;
		this.partes = partes;
		this.documentos = documentos;
	}

	public PeticaoId numeroPeticao(){
		return this.numeroPeticao;
	}

	public ClasseId classeSugerida(){
		return this.classeSugerida;
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

	/**
	 * 
	 * @param partes
	 */
	public void setPartes(final List<Parte> partes){
		Validate.notEmpty(partes, "peticao.partes.notEmpty");
		Validate.noNullElements(partes, "peticao.partes.noNullElements");
	
		this.partes = partes;
	}

	public List<DocumentoId> documentos(){
		return Collections.unmodifiableList(this.documentos);
	}

	/**
	 * 
	 * @param documentos
	 */
	public void setDocumentos(final List<DocumentoId> documentos){
		Validate.notEmpty(documentos, "peticao.documentos.notEmpty");
		Validate.noNullElements(documentos, "peticao.documentos.noNullElements");
	
		this.documentos = documentos;
	}

	public ClasseId classeProcessual(){
		return this.classeProcessual;
	}
	
	public String motivoRecusa(){
		return this.motivoRecusa;
	}

	public void registrar(){
		if (this.status != null) {
			throw new IllegalStateException("peticao.registrar.exception " + this.status);
		}
	
		this.status = PeticaoStatus.A_AUTUAR;
	}

	public void autuar(){
		if (this.status != PeticaoStatus.A_AUTUAR) {
			throw new IllegalStateException("peticao.autuar.exception " + this.status);
		}
	
		this.status = PeticaoStatus.EM_AUTUACAO;
	}

	/**
	 * 
	 * @param classeProcessual
	 */
	public void aceitar(final ClasseId classeProcessual){
		Validate.notNull(classeProcessual, "peticao.classeProcessual.required");
	
		if (this.status != PeticaoStatus.EM_AUTUACAO) {
			throw new IllegalStateException("peticao.aceitar.exception " + this.status);
		}
	
		this.classeProcessual = classeProcessual;
		this.status = PeticaoStatus.ACEITA;
	}

	/**
	 * 
	 * @param motivoRecusa
	 */
	public void recusar(final String motivoRecusa){
		Validate.notNull(motivoRecusa, "peticao.motivoRecusa.required");
	
		if (this.status != PeticaoStatus.EM_AUTUACAO) {
			throw new IllegalStateException("peticao.recusar.exception " + this.status);
		}
	
		this.motivoRecusa = motivoRecusa;
		this.status = PeticaoStatus.RECUSADA;
	}

	public Processo distribuir(final MinistroId ministroRelator){
		Validate.notNull(ministroRelator, "peticao.ministroRelator.required");

		if (this.status != PeticaoStatus.ACEITA) {
			throw new IllegalStateException("peticao.distribuir.exception " + this.status);
		}
			
		this.status = PeticaoStatus.DISTRIBUIDA;
		return new Processo(this.classeProcessual, processoRepository.nextProcessoId(), ministroRelator, this.numeroPeticao, this.partes, this.documentos);
	}

	public PeticaoStatus status(){
		return this.status;
	}

	public ProcessInstanceId processInstanceId(){
		return processInstanceId;
	}

	/**
	 * 
	 * @param processInstanceId
	 */
	public void setProcessInstanceId(final ProcessInstanceId processInstanceId){
		Validate.notNull(processInstanceId, "peticao.processInstanceId.required");
	
		this.processInstanceId = processInstanceId;
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameIdentityAs(Peticao other){
		return other != null && this.classeSugerida.sameValueAs(other.classeSugerida) && this.numeroPeticao.sameValueAs(other.numeroPeticao);
	}

}