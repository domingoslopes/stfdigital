package br.jus.stf.plataforma.notificacoes.infra;

import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import br.jus.stf.plataforma.notificacoes.domain.model.NotificacaoService;
import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;

/**
 * @author Lucas.Rodrigues
 *
 */
public abstract class NotificacaoServiceImpl implements NotificacaoService, InitializingBean {

	@Autowired
	private AcessosRestAdapter acessosRestAdapter;
	
	protected Collection<GrantedAuthority> neededAuthorities;
	
	@Override
	public boolean permitidoEmitir() {
		return SecurityContextUtil.userContainsAll(neededAuthorities);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		neededAuthorities = acessosRestAdapter.carregarPermissoesRecurso("NOTIFICAR", "NOTIFICACAO");
	}
	
}
