package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import org.apache.commons.lang3.Validate;

import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.AssuntoId;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.PreferenciaId;
import br.jus.stf.shared.ProcessoId;
import br.jus.stf.shared.TeseId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 13.01.2016
 */
@Entity
@DiscriminatorValue("RECURSAL")
public class ProcessoRecursal extends Processo {
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "PROCESSO_ASSUNTO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<AssuntoId> assuntos = new HashSet<AssuntoId>(0);
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "PROCESSO_TESE", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<TeseId> teses = new HashSet<TeseId>(0);

	ProcessoRecursal() {

	}
	
	public ProcessoRecursal(final ProcessoId id, final ClasseId classe, final Long numero, final PeticaoId peticao, final ProcessoSituacao situacao, final Set<PreferenciaId> preferencias) {
		super(id, classe, numero, peticao, situacao, preferencias);
	}
	
	public TipoProcesso tipoProcesso() {
		return TipoProcesso.RECURSAL;
	}
	
	public Set<AssuntoId> assuntos(){
		return Collections.unmodifiableSet(assuntos);
	}

	public void atribuirAssuntos(final Set<AssuntoId> assuntos) {
		Validate.notEmpty(assuntos, "processoRecursal.assuntos.required");
		
		this.assuntos.addAll(assuntos);
	}
	
	public void removerAssuntos(final Set<AssuntoId> assuntos) {
		Validate.notEmpty(assuntos, "processoRecursal.assuntos.required");
		
		Iterator<AssuntoId> assuntoIterator = this.assuntos.iterator();
		
		while(assuntoIterator.hasNext()) {
			AssuntoId assuntoId = assuntoIterator.next();
			
			if (assuntos.contains(assuntoId)) {
				assuntoIterator.remove();
			}
		}
	}
	
	public Set<TeseId> teses(){
		return Collections.unmodifiableSet(teses);
	}

	public void atribuirTeses(final Set<TeseId> teses) {
		Validate.notEmpty(teses, "processoRecursal.teses.required");
		
		this.teses.addAll(teses);
	}
	
	public void removerTeses(final Set<TeseId> teses) {
		Validate.notEmpty(teses, "processoRecursal.teses.required");
		
		Iterator<TeseId> teseIterator = this.teses.iterator();
		
		while(teseIterator.hasNext()) {
			TeseId teseId = teseIterator.next();
			
			if (teses.contains(teseId)) {
				teseIterator.remove();
			}
		}
	}

}
