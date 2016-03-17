package br.jus.stf.processamentoinicial.autuacao.infra.persistence;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.identidades.domain.model.TipoAssociado;
import br.jus.stf.plataforma.shared.security.AcessosRestAdapter;
import br.jus.stf.plataforma.shared.security.SecurityContextUtil;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.autuacao.domain.model.MotivoDevolucao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Orgao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.Peticao;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoRepository;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.suporte.domain.model.Modelo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;
import br.jus.stf.shared.TipoDocumentoId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PeticaoRepositoryImpl extends SimpleJpaRepository<Peticao, PeticaoId> implements PeticaoRepository {

	@Autowired
	private AcessosRestAdapter acessosRestAdapter;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	private EntityManager entityManager;
	
	@Autowired
	public PeticaoRepositoryImpl(EntityManager entityManager) {
		super(Peticao.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Peticao> T findOne(PeticaoId id) {
		return (T) super.findOne(id);
	}

	@Override
	public PeticaoId nextId() {
		Query query = entityManager.createNativeQuery("SELECT autuacao.seq_peticao.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new PeticaoId(sequencial);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Peticao> T save(Peticao peticao) {
		return (T) super.save(peticao);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Peticao> T saveAndFlush(Peticao peticao) {
		return (T) super.saveAndFlush(peticao);
	}
	
	@Override
	public Long nextNumero() {
		synchronized (this) {
			Integer key = Calendar.getInstance().get(Calendar.YEAR);
			Query query = entityManager.createNativeQuery("SELECT NVL(MAX(num_peticao), 0) FROM autuacao.peticao WHERE num_ano_peticao = :anoPeticao");
			Long ultimoNumero = ((BigInteger) query.setParameter("anoPeticao", key).getSingleResult()).longValue();
			
			return ultimoNumero + 1;
		}
	}
	
	@Override
	public TipoPeca findOneTipoPeca(TipoDocumentoId id) {
		Query query = entityManager.createQuery("SELECT tipo FROM TipoPeca tipo WHERE tipo.id = :id");
		query.setParameter("id", id);
		return (TipoPeca)query.getSingleResult();
	}
	
	@Override
	public List<TipoPeca> findAllTipoPeca() {
		TypedQuery<TipoPeca> query = entityManager.createQuery("SELECT tipo FROM TipoPeca tipo ORDER BY tipo.nome", TipoPeca.class);
		return query.getResultList();
	}
	
	@Override
	public Orgao findOneOrgao(Long id) {
		Query query = entityManager.createQuery("SELECT orgao FROM Orgao orgao WHERE orgao.sequencial = :id");
		query.setParameter("id", id);
		return (Orgao)query.getSingleResult();
	}
	
	@Override
	public List<Orgao> findAllOrgao() {
		TypedQuery<Orgao> query = entityManager.createQuery("SELECT orgao FROM Orgao orgao ORDER BY orgao.nome", Orgao.class);
		return query.getResultList();
	}
	
	@Override
	public List<Orgao> findOrgaoRepresentados(boolean verificarPerfil) {
		String gestorCadastro = "gestor-cadastro";
		
		if (verificarPerfil && SecurityContextUtil.getUser().getUserDetails().getPapeis().contains(gestorCadastro)) {
			return findAllOrgao();
		} else {
			PessoaId id = SecurityContextUtil.getUser().getUserDetails().getPessoaId();
			TipoAssociado[] tipos = new TipoAssociado[] { TipoAssociado.GESTOR, TipoAssociado.REPRESENTANTE };
			
			return findOrgaoByTipoAssociacao(id, tipos); 	
		}
	}

	@Override
	public boolean estaoNoMesmoStatus(List<PeticaoId> ids, PeticaoStatus status) {
		Query query = entityManager.createQuery("SELECT COUNT(peticao) FROM Peticao peticao JOIN peticao.processosWorkflow as pw WHERE peticao.id IN (:ids) AND pw.status != :status");
		query.setParameter("ids", ids);
		query.setParameter("status", status.name());
		Long count = (Long)query.getSingleResult();
		return count == 0;
	}

	@Override
	public void refresh(Peticao peticao) {
		entityManager.flush();
		entityManager.refresh(peticao);
	}
	
	@Override
	public MotivoDevolucao findOneMotivoDevolucao(Long id) {
		Query query = entityManager.createQuery("SELECT motivo FROM MotivoDevolucao motivo WHERE motivo.sequencial = :id");
		query.setParameter("id", id);
		return (MotivoDevolucao)query.getSingleResult();
	}
	
	@Override
	public List<MotivoDevolucao> findAllMotivoDevolucao() {
		TypedQuery<MotivoDevolucao> query = entityManager.createQuery("SELECT motivo FROM MotivoDevolucao motivo ORDER BY motivo.descricao", MotivoDevolucao.class);
		return query.getResultList();
	}
	
	@Override
	public List<Modelo> findModeloByMotivoDevolucao(MotivoDevolucao motivoDevolucao) {
		TypedQuery<Modelo> query = entityManager.createQuery("SELECT modelo FROM Modelo modelo WHERE modelo.tipoModelo.id IN :tipos ORDER BY modelo.nome", Modelo.class);
		
		query.setParameter("tipos", motivoDevolucao.tiposModelo());
		return query.getResultList();
	}
	
	private List<Orgao> findOrgaoByTipoAssociacao(PessoaId id, TipoAssociado... tipos){
		TypedQuery<Orgao> query = entityManager.createQuery("SELECT orgao FROM Orgao orgao WHERE orgao.id IN (SELECT asso.orgao FROM Associado asso WHERE asso.tipo IN :tipos AND asso.pessoa.id = :id) ORDER BY orgao.nome", Orgao.class);
		
		query.setParameter("tipos", Arrays.asList(tipos));
		query.setParameter("id", id);
		return query.getResultList();
	}
	
}
