package br.jus.stf.plataforma.acessos.domain.model;

import java.util.List;



public interface UsuarioRepository {
	
	public <T extends Usuario> T findOne(Long sequencial);
	
	public <T extends Usuario> T save(Usuario principal);
	
	public Papel findOnePapel(Long sequencial);
	
	public List<Papel> findAllPapel();
	
	public Grupo findOneGrupo(Long sequencial);
	
	public List<Grupo> findAllGrupo();
	
	public TipoInformacao findOneTipoInformacao(Long sequencial);
	
	public List<TipoInformacao> findAllTipoInformacao();
	
	public TipoSegmento findOneTipoSegmento(Long sequencial);
	
	public List<TipoSegmento> findAllTipoSegmento();
	
	public Segmento findOneSegmento(Long sequencial);
	
	public List<Segmento> findAllSegmento();
	
	public Recurso findOneRecurso(Long sequencial);
	
	public List<Recurso> findAllRecurso();
	
	public List<Permissao> findByPermissaoUsuario(String login);
	
	public List<Permissao> findByPermissaoRecurso(Long sequencial);
	
	public List<Permissao> findByPermissaoPapel(Long sequencial);
	
	public List<Permissao> findByPermissaoGrupo(Long sequencial);

}