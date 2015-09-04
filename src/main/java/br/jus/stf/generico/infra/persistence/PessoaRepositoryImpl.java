package br.jus.stf.generico.infra.persistence;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.generico.domain.model.Pessoa;
import br.jus.stf.generico.domain.model.PessoaRepository;
import br.jus.stf.shared.domain.model.PessoaId;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class PessoaRepositoryImpl extends SimpleJpaRepository<Pessoa, Long> implements PessoaRepository {

	@Autowired
	public PessoaRepositoryImpl(EntityManager entityManager) {
		super(Pessoa.class, entityManager);
	}

	@Override
	public Pessoa find(PessoaId pessoaId) {
		return super.findOne(pessoaId.toLong());
	}

	@Override
	public void store(Pessoa pessoa) {
		super.save(pessoa);
	}

}
