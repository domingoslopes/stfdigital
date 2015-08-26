package br.jus.stf.autuacao.infra;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;
import br.jus.stf.autuacao.domain.MinistroRepository;
import br.jus.stf.autuacao.domain.entity.Ministro;

/**
  * @author anderson.araujo
  * 
  * @since 1.0.0
  * @since 22.07.2015
  */
@Repository
public class MinistroRepositoryMemory implements MinistroRepository {

	public List<Ministro> listar() {
		List<Ministro> ministros = new LinkedList<Ministro>();
		ministros.add(new Ministro(42, "Min. Cármen Lúcia"));
		ministros.add(new Ministro(28, "Min. Celso de Mello"));
		ministros.add(new Ministro(44, "Min. Dias Toffoli"));
		ministros.add(new Ministro(49, "Min. Edson Fachin"));
		ministros.add(new Ministro(36, "Min. Gilmar Mendes"));
		ministros.add(new Ministro(45, "Min. Luiz Fux"));
		ministros.add(new Ministro(30, "Min. Marco Aurélio"));
		ministros.add(new Ministro(48, "Min. Roberto Barroso"));
		ministros.add(new Ministro(46, "Min. Rosa Weber"));
		ministros.add(new Ministro(47, "Min. Teori Zavascki"));
		ministros.add(new Ministro(1, "Ministro Presidente"));

		return ministros;
	}
	
}
