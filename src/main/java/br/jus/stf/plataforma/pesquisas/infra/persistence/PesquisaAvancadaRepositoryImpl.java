package br.jus.stf.plataforma.pesquisas.infra.persistence;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.Validate;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Criterio;
import br.jus.stf.plataforma.pesquisas.domain.model.pesquisa.Pesquisa;
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
		String sql = "select new PesquisaAvancada(pa.id, pa.nome, pa.tipo) from PesquisaAvancada pa where pa.usuario = :usuario";
		
		TypedQuery<PesquisaAvancada> query = entityManager.createQuery(sql, PesquisaAvancada.class);
		query.setParameter("usuario", usuarioId);
		
		return query.getResultList();
	}

	@Override
	public List<PesquisaAvancada> listarPorMeusPapeis() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Resultado> executar(Pesquisa pesquisa, Pageable paginacao) {
		Validate.notNull(pesquisa, "pesquisaAvancada.pesquisa.required");
		Validate.notEmpty(pesquisa.consulta(), "pesquisaAvancada.consulta.required");
		Validate.notEmpty(pesquisa.indices(), "pesquisaAvancada.indices.required");
				
		SearchRequestBuilder builder = new SearchRequestBuilder(elasticsearchClient);
		builder.setIndices(pesquisa.indices());
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		builder.setExtraSource(pesquisa.consulta());
		
		Optional.ofNullable(paginacao.getPageSize()).ifPresent(t -> {
			if (t > 0) {
				builder.setSize(t);
			}
			Optional.ofNullable(paginacao.getPageNumber()).ifPresent(p -> {
				if (p > 0 && t > 0) {
					builder.setFrom(p * t);
				} else {
					builder.setFrom(0);
				}
			});	
		});
		return new ResultadoPesquisa().extract(builder.get());
	}

	@Override
    public List<Criterio> listarCriterios(String[] indices) {
		StringBuilder query = new StringBuilder("select c from Criterio c");
		Optional.ofNullable(indices)
			.ifPresent(ids -> {
				query.append(" where c.indice in (");
				Iterator<String> itr = Arrays.asList(ids).iterator();
				
				while(itr.hasNext()) {
					query.append("'").append(itr.next()).append("'");
					if (itr.hasNext()) {
						query.append(",");
					}
				}
				query.append(")");
			});
	    return entityManager.createQuery(query.toString(), Criterio.class).getResultList();
    }
}
