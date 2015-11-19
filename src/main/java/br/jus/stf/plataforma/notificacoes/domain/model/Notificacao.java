package br.jus.stf.plataforma.notificacoes.domain.model;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.Validate;

import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.shared.stereotype.Entity;

/**
 * @author Lucas.Rodrigues
 *
 */
public class Notificacao implements Entity<Notificacao, NotificacaoId> {

	private NotificacaoId id;
	private TipoNotificacao tipo;
	private String mensagem;
	private Date dataCriacao;
	private String notificante;
	private boolean lida;
	private String notificado;
	
	public Notificacao(final TipoNotificacao tipo, final String mensagem, final String notificado) {
		Validate.notNull(tipo, "notificacao.tipo.required");
		Validate.notBlank(mensagem, "notificacao.mensagem.required");
		Validate.notBlank(notificado, "notificacao.notificado.required");
		
		this.id = new NotificacaoId(UUID.randomUUID());
		this.tipo = tipo;
		this.mensagem = mensagem;
		this.notificado = notificado;
		
		this.dataCriacao = new Date();
		this.notificante = SecurityContextUtil.getNomeUsuario();
	}

	@Override
	public NotificacaoId id() {
		return id;
	}
	
	public TipoNotificacao tipo() {
		return tipo;
	}
	
	public String mensagem() {
		return mensagem;
	}
	
	public Date dataCriacao() {
		return dataCriacao;
	}
	
	public String notificante() {
		return notificante;
	}
	
	public boolean lida() {
		return lida;
	}
	
	public String notificado() {
		return notificado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		
		Notificacao other = (Notificacao) obj;
		return sameIdentityAs(other);
	}
	
	@Override
	public boolean sameIdentityAs(Notificacao other) {
		return id.sameValueAs(other.id);
	}

}
