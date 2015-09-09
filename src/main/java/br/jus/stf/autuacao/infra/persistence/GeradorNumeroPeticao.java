/**
 * 
 */
package br.jus.stf.autuacao.infra.persistence;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

/**
 * @author Lucas.Rodrigues
 *
 */
public class GeradorNumeroPeticao implements AnnotationValueGeneration<GerarNumeroPeticao> {

	private static Map<Integer, Long> numeros = new HashMap<Integer, Long>();
	
	private final ValueGenerator<Long> generator = new ValueGenerator<Long>() {
		public Long generateValue(Session session, Object owner) {
			return proximoNumero();
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
	public void initialize(GerarNumeroPeticao annotation, Class<?> propertyType) {

	}
	
	/**
	 * Recupera o próximo número de petição de acordo com o ano
	 * 
	 * @return o número da petição
	 */
	private static synchronized Long proximoNumero() {
		Integer key = Calendar.getInstance().get(Calendar.YEAR);
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
