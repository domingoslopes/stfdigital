package br.jus.stf.autuacao.infra.persistence;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

import br.jus.stf.autuacao.domain.model.Processo;
import br.jus.stf.shared.domain.model.ClasseId;

/**
 * @author Lucas.Rodrigues
 *
 */
public class GeradorNumeroProcesso implements AnnotationValueGeneration<GerarNumeroProcesso> {
	
	private static Map<String, Long> numeros = new HashMap<String, Long>();
	
	private final ValueGenerator<Long> generator = new ValueGenerator<Long>() {
		public Long generateValue(Session session, Object owner) {
			Processo processo = (Processo) owner;
			return proximoNumero(processo.classe());
		}
	};
	
	@Override
	public GenerationTiming getGenerationTiming() {
		return GenerationTiming.INSERT;
	}

	@Override
	public ValueGenerator<?> getValueGenerator() {
		return generator;
	}

	@Override
	public boolean referenceColumnInSql() {
		return false;
	}

	@Override
	public String getDatabaseGeneratedReferencedColumnValue() {
		return null;
	}

	@Override
	public void initialize(GerarNumeroProcesso annotation, Class<?> propertyType) {

	}
	
	/**
	 * Recupera próximo número em memória
	 * 
	 * @param classe
	 * @return o número
	 */
	private static synchronized Long proximoNumero(ClasseId classe) {
		String key = classe.toString();
		if (numeros.containsKey(key)) {
			Long num = numeros.get(key) + 1;
			numeros.put(key, num);
			return num;
		} else {
			numeros.put(key, 1L);
			return 1L;
		}
	}

}
