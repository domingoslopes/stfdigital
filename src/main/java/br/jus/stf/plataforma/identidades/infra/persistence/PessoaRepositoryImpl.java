package br.jus.stf.plataforma.identidades.infra.persistence;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.plataforma.identidades.domain.model.PessoaRepository;
import br.jus.stf.shared.PessoaId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PessoaRepositoryImpl extends SimpleJpaRepository<Pessoa, PessoaId> implements PessoaRepository {

	private EntityManager entityManager;
	
	@Autowired
	public PessoaRepositoryImpl(EntityManager entityManager) {
		super(Pessoa.class, entityManager);
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Pessoa> T save(Pessoa pessoa) {
		return (T) super.save(pessoa);
	}

	@Override
	public PessoaId nextId() {
		Query query = entityManager.createNativeQuery("SELECT CORPORATIVO.SEQ_PESSOA.NEXTVAL FROM DUAL");
		Long sequencial = ((BigInteger) query.getSingleResult()).longValue();
		return new PessoaId(sequencial);
	}

}
