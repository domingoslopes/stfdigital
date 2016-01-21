package br.jus.stf.processamentoinicial.recursaledistribuicao.infra.persistence;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.MotivoInaptidao;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.Processo;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRepository;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.ProcessoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class ProcessoRepositoryImpl extends SimpleJpaRepository<Processo, ProcessoId> implements ProcessoRepository {

	private EntityManager entityManager;
	
	@Autowired
	public ProcessoRepositoryImpl(EntityManager entityManager) {
		super(Processo.class, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public ProcessoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT autuacao.seq_processo.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new ProcessoId(sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Processo> T save(Processo processo) {
		return (T) super.save(processo);
	}
	
	@Override
	public Long nextNumero(ClasseId classe) {
		synchronized (this) {
			String key = classe.toString();
			Query query = entityManager.createNativeQuery("SELECT NVL(MAX(num_processo), 0) FROM autuacao.processo WHERE sig_classe = :classe");
			Long ultimoNumero = ((BigInteger) query.setParameter("classe", key).getSingleResult()).longValue();
			
			return ultimoNumero + 1;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Processo> findByPessoaInPartes(PessoaId pessoaId) {
		Query query = entityManager.createQuery("SELECT proc FROM Processo proc INNER JOIN proc.partes part WITH part.pessoaId = :pessoaId");
		query.setParameter("pessoaId", pessoaId);
		
		return query.getResultList();
	}
	
	@Override
	public MotivoInaptidao findOneMotivoInaptidao(Long id) {
		Query query = entityManager.createQuery("SELECT motivo FROM MotivoInaptidao motivo WHERE motivo.sequencial = :id");
		query.setParameter("id", id);
		
		return (MotivoInaptidao)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MotivoInaptidao> findAllMotivoInaptidao() {
		Query query = entityManager.createQuery("SELECT motivo FROM MotivoInaptidao motivo ORDER BY motivo.descricao");
		
		return query.getResultList();
	}

	@Override
    public Processo findByPeticao(PeticaoId peticaoId) {
		Query query = entityManager.createQuery("SELECT processo FROM Processo processo WHERE processo.peticao = :id");
		query.setParameter("id", peticaoId);
		return (Processo) query.getSingleResult();
    }
	
}
