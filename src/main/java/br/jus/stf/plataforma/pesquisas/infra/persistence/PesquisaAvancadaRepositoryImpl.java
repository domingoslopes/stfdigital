package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.Validate;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancada;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancadaId;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.PesquisaAvancadaRepository;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Resultado;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.shared.UsuarioId;


/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PesquisaAvancadaRepositoryImpl extends SimpleJpaRepository<PesquisaAvancada, PesquisaAvancadaId> implements PesquisaAvancadaRepository {
	
	private EntityManager entityManager;
	
	@Autowired
	private Client elasticsearchClient;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	public PesquisaAvancadaRepositoryImpl(EntityManager entityManager) {
	    super(PesquisaAvancada.class, entityManager);
	    this.entityManager = entityManager;
    }
	
	@Override
	public PesquisaAvancadaId nextId() {
		Query query = entityManager.createNativeQuery("SELECT plataforma.seq_pesquisa_avancada.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new PesquisaAvancadaId(sequencial);
	}

	@SuppressWarnings("unchecked")
    @Override
	public PesquisaAvancada save(PesquisaAvancada pesquisa) {
		return super.save(pesquisa);
	}

	@Override
	public List<PesquisaAvancada> listarMinhas() {
		UsuarioId usuarioId = SecurityContextUtil.getUser().getUserDetails().getUsuarioId();
		return super.findAll(new Specification<PesquisaAvancada>() {
			
			@Override
			public Predicate toPredicate(Root<PesquisaAvancada> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("usuario"), usuarioId);
			}
		});
	}

	@Override
	public List<PesquisaAvancada> listarPorMeusPapeis() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Resultado> executar(String consulta, Integer pagina, Integer tamanho) {
		Validate.notBlank(consulta, "pesquisaAvancada.consulta.required");
		
		SearchRequestBuilder builder = new SearchRequestBuilder(elasticsearchClient);
		builder.setExtraSource(consulta);
		Optional.ofNullable(tamanho).ifPresent(t -> {
			if (t > 0) {
				builder.internalBuilder().size(t);
			}
			Optional.ofNullable(pagina).ifPresent(p -> {
				if (p > 0 && t > 0) {
					builder.internalBuilder().from(p * t);
				} else {
					builder.internalBuilder().from(0);
				}
			});	
		});
		return new ResultadoPesquisa().extract(builder.get());
	}
}
