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
		ministros.add(new Ministro(42, "MIN. CÁRMEN LÚCIA"));
		ministros.add(new Ministro(28, "MIN. CELSO DE MELLO"));
		ministros.add(new Ministro(44, "MIN. DIAS TOFFOLI"));
		ministros.add(new Ministro(49, "MIN. EDSON FACHIN"));
		ministros.add(new Ministro(36, "MIN. GILMAR MENDES"));
		ministros.add(new Ministro(45, "MIN. LUIZ FUX"));
		ministros.add(new Ministro(30, "MIN. MARCO AURÉLIO"));
		ministros.add(new Ministro(48, "MIN. ROBERTO BARROSO"));
		ministros.add(new Ministro(46, "MIN. ROSA WEBER"));
		ministros.add(new Ministro(47, "MIN. TEORI ZAVASCKI"));
		ministros.add(new Ministro(1, "MINISTRO PRESIDENTE"));

		return ministros;
	}
	
}
