package br.jus.stf.plataforma.pesquisas.domain.model.pesquisa;

import java.util.List;

import org.springframework.data.domain.Pageable;

/**
 * @author Lucas.Rodrigues
 *
 */
public interface PesquisaRepository {
	
	/**
	 * Sugere resultados de pesquisa nos índices verificando se os filtros
	 * informados são prefixos do texto que representa o valor do campo.
	 * Ex: Campo "nome" filtrado por "Jo", retorna os objetos com nomes iguais a:
	 * João, José e etc. Inclusive complemento do primeiro nome.
	 * 
	 * @param pesquisa
	 * @return o resultado da pesquisa
	 */
	List<Resultado> sugerir(Pesquisa pesquisa);

	/**
	 * Pesquisa nos índices aplicando os filtros informados onde o valor
	 * deve estar presente nos resultados.  
	 * 
	 * @param pesquisa
	 * @param paginacao
	 * @return o resultado da pesquisa
	 */
	List<Resultado> pesquisar(Pesquisa pesquisa, Pageable paginacao);

	/**
	 * Realize a contagem dos resultados da pesquisa com os filtros informados.
	 * 
	 * @param pesquisa
	 * @return
	 */
	Long count(Pesquisa pesquisa);
	
}
