package br.jus.stf.processamentoinicial.distribuicao.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

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
	
	@ElementCollection
	@CollectionTable(name = "PROCESSO_ASSUNTO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<AssuntoId> assuntos = new HashSet<AssuntoId>(0);
	
	@ElementCollection
	@CollectionTable(name = "PROCESSO_TESE", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false))
	private Set<TeseId> teses = new HashSet<TeseId>(0);
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PROCESSO_MOTIVO_INAPTIDAO", schema = "AUTUACAO", joinColumns = @JoinColumn(name = "SEQ_PROCESSO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "COD_MOTIVO_INAPTIDAO", nullable = false))
	private Set<MotivoInaptidao> motivosInaptidao = new HashSet<MotivoInaptidao>(0);

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
	
	public Set<MotivoInaptidao> motivosInaptidao(){
		return Collections.unmodifiableSet(motivosInaptidao);
	}
	
	public void autuar(Set<AssuntoId> assuntos, Set<ParteProcesso> poloAtivo, Set<ParteProcesso> poloPassivo) {
		Optional.ofNullable(assuntos).ifPresent(a -> {
			if (!a.isEmpty()) {
				atribuirAssuntos(a);
			}
		});
		poloAtivo.forEach(parte -> super.adicionarParte(parte));
		poloPassivo.forEach(parte -> super.adicionarParte(parte));
	}
	
	public void analisarPressupostosFormais(boolean processoApto, Set<MotivoInaptidao> motivosInaptidao, String observacaoAnalise) {
		
	}
	
	

}
