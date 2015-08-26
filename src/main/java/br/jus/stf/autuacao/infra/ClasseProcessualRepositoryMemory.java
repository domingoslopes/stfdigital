package br.jus.stf.autuacao.infra;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;
import br.jus.stf.autuacao.domain.ClasseProcessualRepository;
import br.jus.stf.autuacao.domain.entity.ClasseProcessual;

/**
 * @author Anderson.Araujo
 *
 */
@Repository
public class ClasseProcessualRepositoryMemory implements ClasseProcessualRepository {
	/**
	 * Retorna todas as classes processuais ativas.
	 */
	public List<ClasseProcessual> listar() {
		List<ClasseProcessual> classesProcessuais = new LinkedList<ClasseProcessual>();
		classesProcessuais.add(new ClasseProcessual("RE",   "Recurso Extraordinário"));
		classesProcessuais.add(new ClasseProcessual("ADI",  "Ação Direta de Inconstitucionalidade"));
		classesProcessuais.add(new ClasseProcessual("PPE",  "Prisão Preventiva para Extradição"));
		classesProcessuais.add(new ClasseProcessual("ACO",  "Ação Cível Originária"));
		classesProcessuais.add(new ClasseProcessual("HD",   "Habeas Data"));
		classesProcessuais.add(new ClasseProcessual("MS",   "Mandado de Segurança"));
		classesProcessuais.add(new ClasseProcessual("AO",   "Ação Originária"));
		classesProcessuais.add(new ClasseProcessual("RHD",  "Recurso Ordinário em Habeas Data"));
		classesProcessuais.add(new ClasseProcessual("IF",   "Intervenção Federal"));
		classesProcessuais.add(new ClasseProcessual("AR",   "Ação Rescisória"));
		classesProcessuais.add(new ClasseProcessual("EP",   "Execução Penal"));
		classesProcessuais.add(new ClasseProcessual("SS",   "Suspensão de Segurança"));
		classesProcessuais.add(new ClasseProcessual("CC",   "Conflito de Competência"));
		classesProcessuais.add(new ClasseProcessual("AP",   "Ação Penal"));
		classesProcessuais.add(new ClasseProcessual("OACO", "Oposição em Ação Civil Originária"));
		classesProcessuais.add(new ClasseProcessual("ADC",  "Ação Declaratória de Constitucionalidade"));
		classesProcessuais.add(new ClasseProcessual("AOE",  "Ação Originária Especial"));
		classesProcessuais.add(new ClasseProcessual("AS",   "Arguição de Suspeição"));
		classesProcessuais.add(new ClasseProcessual("ADPF", "Arguição de Descumprimento de Preceito Fundamental"));
		classesProcessuais.add(new ClasseProcessual("RC",   "Recurso Crime"));
		classesProcessuais.add(new ClasseProcessual("SL",   "Suspensão de Liminar"));
		classesProcessuais.add(new ClasseProcessual("Cm",   "Comunicação"));
		classesProcessuais.add(new ClasseProcessual("Inq",  "Inquérito"));
		classesProcessuais.add(new ClasseProcessual("Pet",  "Petição"));
		classesProcessuais.add(new ClasseProcessual("STA",  "Suspensão de Tutela Antecipada"));
		classesProcessuais.add(new ClasseProcessual("ADO",  "Ação direta de Inconstitucionalidade por Omissão"));
		classesProcessuais.add(new ClasseProcessual("PSV",  "Proposta de Súmula Vinculante"));
		classesProcessuais.add(new ClasseProcessual("AImp", "Arguição de Impedimento"));
		classesProcessuais.add(new ClasseProcessual("EL",   "Exceção de Litispendência"));
		classesProcessuais.add(new ClasseProcessual("EI",   "Exceção de Incompetência"));
		classesProcessuais.add(new ClasseProcessual("RHC",  "Recurso Ordinário em Habeas Corpus"));
		classesProcessuais.add(new ClasseProcessual("HC",   "Habeas Corpus"));
		classesProcessuais.add(new ClasseProcessual("Rcl",  "Reclamação"));
		classesProcessuais.add(new ClasseProcessual("ARE",  "Recurso Extraordinário com Agravo"));
		classesProcessuais.add(new ClasseProcessual("Ext",  "Extradição"));
		classesProcessuais.add(new ClasseProcessual("AI",   "Agravo de Instrumento"));
		classesProcessuais.add(new ClasseProcessual("MI",   "Mandado de Injunção"));
		classesProcessuais.add(new ClasseProcessual("RMI",  "Recurso Ordinário em Mandado de Injunção"));
		classesProcessuais.add(new ClasseProcessual("AC",   "Ação Cautelar"));
		classesProcessuais.add(new ClasseProcessual("SEC",  "Sentença Estrangeira Contestada"));
		classesProcessuais.add(new ClasseProcessual("RMS",  "Recurso Ordinário em Mandado de Segurança"));
		classesProcessuais.add(new ClasseProcessual("RvC",  "Revisão Criminal"));
		
		classesProcessuais.sort((c1, c2) -> c1.getNome().compareTo(c2.getNome()));
	
		return classesProcessuais;
	}
}
