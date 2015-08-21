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
		
		classesProcessuais.add(new ClasseProcessual("AC","REAÇÃO CAUTELAR"));
		classesProcessuais.add(new ClasseProcessual("AC-a","AC-aAÇÃO CAUTELAR"));
		classesProcessuais.add(new ClasseProcessual("ACi","ACi-aAPELAÇÃO CÍVEL"));
		classesProcessuais.add(new ClasseProcessual("ACi-a","ACL-aAPELAÇÃO CÍVEL"));
		classesProcessuais.add(new ClasseProcessual("ACL","ACO-aAPELAÇÃO COMERCIAL"));
		classesProcessuais.add(new ClasseProcessual("ACL-a","ACr-aAPELAÇÃO COMERCIAL"));
		classesProcessuais.add(new ClasseProcessual("ACO","AE-aAÇÃO CÍVEL ORIGINÁRIA"));
		classesProcessuais.add(new ClasseProcessual("ACO-a","AGP-aAÇÃO CÍVEL ORIGINÁRIA"));
		classesProcessuais.add(new ClasseProcessual("ACr","ADIAPELAÇÃO CRIMINAL"));
		classesProcessuais.add(new ClasseProcessual("ACr-a","SDAPELAÇÃO CRIMINAL"));
		classesProcessuais.add(new ClasseProcessual("AD","PPEAÇÃO DECLARATÓRIA"));
		classesProcessuais.add(new ClasseProcessual("ADC","SEAÇÃO DECLARATÓRIA DE CONSTITUCIONALIDADE"));
		classesProcessuais.add(new ClasseProcessual("ADI","ACOAÇÃO DIRETA DE INCONSTITUCIONALIDADE"));
		classesProcessuais.add(new ClasseProcessual("ADO","HDAÇÃO DIRETA DE INCONSTITUCIONALIDADE POR OMISSÃO"));
		classesProcessuais.add(new ClasseProcessual("ADPF","MSARGÜIÇÃO DE DESCUMPRIMENTO DE PRECEITO FUNDAMENTAL"));
		classesProcessuais.add(new ClasseProcessual("AE","AOAÇÃO ESPECIAL"));
		classesProcessuais.add(new ClasseProcessual("AE-a","RHDAÇÃO ESPECIAL"));
		classesProcessuais.add(new ClasseProcessual("AGP","CRAGRAVO DE PETIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("AGP-a","IFAGRAVO DE PETIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("AGR","AORAGRAVO"));
		classesProcessuais.add(new ClasseProcessual("AGR-a","ARAGRAVO"));
		classesProcessuais.add(new ClasseProcessual("AI","EPAGRAVO DE INSTRUMENTO"));
		classesProcessuais.add(new ClasseProcessual("AI-a","SSAGRAVO DE INSTRUMENTO"));
		classesProcessuais.add(new ClasseProcessual("AImp","CCARGÜIÇÃO DE IMPEDIMENTO"));
		classesProcessuais.add(new ClasseProcessual("AO","PAAÇÃO ORIGINÁRIA"));
		classesProcessuais.add(new ClasseProcessual("AO-a","APAÇÃO ORIGINÁRIA"));
		classesProcessuais.add(new ClasseProcessual("AOE","OACOAÇÃO ORIGINÁRIA ESPECIAL"));
		classesProcessuais.add(new ClasseProcessual("AOE-a","PETAVAÇÃO ORIGINÁRIA ESPECIAL"));
		classesProcessuais.add(new ClasseProcessual("AOR","PETAAÇÃO CÍVEL ORIGINÁRIA"));
		classesProcessuais.add(new ClasseProcessual("AOR-a","ADCAÇÃO CÍVEL ORIGINÁRIA"));
		classesProcessuais.add(new ClasseProcessual("AP","AOEAÇÃO PENAL"));
		classesProcessuais.add(new ClasseProcessual("AP-a","RCRAÇÃO PENAL"));
		classesProcessuais.add(new ClasseProcessual("APL","ASAPELAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("APL-a","QCAPELAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("AR","ADPFAÇÃO RESCISÓRIA"));
		classesProcessuais.add(new ClasseProcessual("AR-a","RCAÇÃO RESCISÓRIA"));
		classesProcessuais.add(new ClasseProcessual("ARE","CJRECURSO EXTRAORDINÁRIO COM AGRAVO"));
		classesProcessuais.add(new ClasseProcessual("ARg","RELAÇÃO REGRESSIVA"));
		classesProcessuais.add(new ClasseProcessual("ARg-a","APLAÇÃO REGRESSIVA"));
		classesProcessuais.add(new ClasseProcessual("ARv","AGRARGUIÇÃO DE RELEVÂNCIA"));
		classesProcessuais.add(new ClasseProcessual("ARv-a","AGPARGUIÇÃO DE RELEVÂNCIA"));
		classesProcessuais.add(new ClasseProcessual("AS","CTARGÜIÇÃO DE SUSPEIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("AS-a","RTCARGÜIÇÃO DE SUSPEIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("CA","RTCRCONFLITO DE ATRIBUIÇÕES"));
		classesProcessuais.add(new ClasseProcessual("CA-a","ERCONFLITO DE ATRIBUIÇÕES"));
		classesProcessuais.add(new ClasseProcessual("CC","RVCRCONFLITO DE COMPETÊNCIA"));
		classesProcessuais.add(new ClasseProcessual("CC-a","ACLCONFLITO DE COMPETÊNCIA"));
		classesProcessuais.add(new ClasseProcessual("CJ","AECONFLITO DE JURISDIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("CJ-a","RTCOCONFLITO DE JURISDIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("Cm","RCLANCOMUNICAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("Cm-a","PRESPCOMUNICAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("CP","ARgCARTA PRECATÓRIA"));
		classesProcessuais.add(new ClasseProcessual("CR","ESCARTA ROGATÓRIA"));
		classesProcessuais.add(new ClasseProcessual("CR-a","IACARTA ROGATÓRIA"));
		classesProcessuais.add(new ClasseProcessual("CT","IPCARTA TESTEMUNHÁVEL"));
		classesProcessuais.add(new ClasseProcessual("CT-a","IPECARTA TESTEMUNHÁVEL"));
		classesProcessuais.add(new ClasseProcessual("Den","IntDENÚNCIA"));
		classesProcessuais.add(new ClasseProcessual("Den-a","LSDENÚNCIA"));
		classesProcessuais.add(new ClasseProcessual("EI","NotEXCEÇÃO DE INCOMPETÊNCIA"));
		classesProcessuais.add(new ClasseProcessual("EL","PCEXCEÇÃO DE LITISPENDÊNCIA"));
		classesProcessuais.add(new ClasseProcessual("EP","RALEXECUÇÃO PENAL"));
		classesProcessuais.add(new ClasseProcessual("ER","RLSEMBARGOS REMETIDOS"));
		classesProcessuais.add(new ClasseProcessual("ER-a","RNEEMBARGOS REMETIDOS"));
		classesProcessuais.add(new ClasseProcessual("ES","SAEXCEÇÃO DE SUSPEIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("ES-a","SLEXCEÇÃO DE SUSPEIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("EV","ADEXCEÇÃO DA VERDADE"));
		classesProcessuais.add(new ClasseProcessual("Ext","CPEXTRADIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("Ext-a","PtJEXTRADIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("HC","InFHABEAS CORPUS"));
		classesProcessuais.add(new ClasseProcessual("HC-a","ACiHABEAS CORPUS"));
		classesProcessuais.add(new ClasseProcessual("HD","ACrHABEAS DATA"));
		classesProcessuais.add(new ClasseProcessual("IA","ARvINQUÉRITO ADMINISTRATIVO"));
		classesProcessuais.add(new ClasseProcessual("IF","CAINTERVENÇÃO FEDERAL"));
		classesProcessuais.add(new ClasseProcessual("IF-a","CmINTERVENÇÃO FEDERAL"));
		classesProcessuais.add(new ClasseProcessual("InF","EVINCIDENTE DE FALSIDADE"));
		classesProcessuais.add(new ClasseProcessual("Inq","InqINQUÉRITO"));
		classesProcessuais.add(new ClasseProcessual("Inq-a","PAvINQUÉRITO"));
		classesProcessuais.add(new ClasseProcessual("Int","PetINTERPELAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("Int-a","RpINTERPELAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("IP","RRINQUÉRITO POLICIAL"));
		classesProcessuais.add(new ClasseProcessual("IP-a","STAINQUÉRITO POLICIAL"));
		classesProcessuais.add(new ClasseProcessual("IPE","ADOINQUÉRITO POLICIAL ESPECIAL"));
		classesProcessuais.add(new ClasseProcessual("LS","PSVLIQUIDAÇÃO DE SENTENÇA"));
		classesProcessuais.add(new ClasseProcessual("MI","AImpMANDADO DE INJUNÇÃO"));
		classesProcessuais.add(new ClasseProcessual("MI-a","ELMANDADO DE INJUNÇÃO"));
		classesProcessuais.add(new ClasseProcessual("MS","EIMANDADO DE SEGURANÇA"));
		classesProcessuais.add(new ClasseProcessual("MS-a","RHCMANDADO DE SEGURANÇA"));
		classesProcessuais.add(new ClasseProcessual("Not","HCNOTIFICAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("OACO","RclOPOSIÇÃO EM AÇÃO CIVIL ORIGINÁRIA"));
		classesProcessuais.add(new ClasseProcessual("PA","AREPROCESSO ADMINISTRATIVO"));
		classesProcessuais.add(new ClasseProcessual("PA-a","ExtPROCESSO ADMINISTRATIVO"));
		classesProcessuais.add(new ClasseProcessual("PAv","AIPEDIDO DE AVOCAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("PAv-a","MIPEDIDO DE AVOCAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("PC","RMIPROCESSO CRIME"));
		classesProcessuais.add(new ClasseProcessual("Pet","ACPETIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("PETA","SECPETIÇÃO AVULSA"));
		classesProcessuais.add(new ClasseProcessual("Pet-a","DenPETIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("PETAV","RMSPETIÇÃO AVULSA"));
		classesProcessuais.add(new ClasseProcessual("PPE","AGR-aPRISÃO PREVENTIVA PARA EXTRADIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("PPE-a","AI-aPRISÃO PREVENTIVA PARA EXTRADIÇÃO"));
		classesProcessuais.add(new ClasseProcessual("PRESP","AO-aPROCESSO DE RESPONSABILIDADE"));
		classesProcessuais.add(new ClasseProcessual("PSV","AOE-aPROPOSTA DE SÚMULA VINCULANTE"));
		classesProcessuais.add(new ClasseProcessual("PtJ","AOR-aPROTESTO JUDICIAL"));
		classesProcessuais.add(new ClasseProcessual("QC","AP-aQUEIXA-CRIME"));
		classesProcessuais.add(new ClasseProcessual("QC-a","APL-aQUEIXA-CRIME"));
		classesProcessuais.add(new ClasseProcessual("RAL","AR-aRECURSO DE APREENSÃO DE LIVRO"));
		classesProcessuais.add(new ClasseProcessual("RC","ARg-aRECURSO CRIME"));
		classesProcessuais.add(new ClasseProcessual("RC-a","ARv-aRECURSO CRIME"));
		classesProcessuais.add(new ClasseProcessual("RE","AS-aRECURSO EXTRAORDINÁRIO"));
		classesProcessuais.add(new ClasseProcessual("Rcl","CA-aRECLAMAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("Rcl-a","CC-aRECLAMAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("RCLAN","CJ-aRECLAMAÇÃO DE ANTIGUIDADE"));
		classesProcessuais.add(new ClasseProcessual("RCR","Cm-aRECURSO ORDINÁRIO CRIMINAL"));
		classesProcessuais.add(new ClasseProcessual("RCR-a","CR-aRECURSO ORDINÁRIO CRIMINAL"));
		classesProcessuais.add(new ClasseProcessual("RE-a","CT-aRECURSO EXTRAORDINÁRIO"));
		classesProcessuais.add(new ClasseProcessual("REL","Den-aRECURSO ELEITORAL"));
		classesProcessuais.add(new ClasseProcessual("REL-a","ER-aRECURSO ELEITORAL"));
		classesProcessuais.add(new ClasseProcessual("RHC","ES-aRECURSO ORDINÁRIO EM HABEAS CORPUS"));
		classesProcessuais.add(new ClasseProcessual("RHC-a","Ext-aRECURSO ORDINÁRIO EM HABEAS CORPUS"));
		classesProcessuais.add(new ClasseProcessual("RHD","HC-aRECURSO ORDINÁRIO EM HABEAS DATA"));
		classesProcessuais.add(new ClasseProcessual("RLS","IF-aRECURSO DE LIQUIDAÇÃO DE SENTENÇA"));
		classesProcessuais.add(new ClasseProcessual("RMI","Inq-aRECURSO ORDINÁRIO EM MANDADO DE INJUNÇÃO"));
		classesProcessuais.add(new ClasseProcessual("RMI-a","Int-aRECURSO ORDINÁRIO EM MANDADO DE INJUNÇÃO"));
		classesProcessuais.add(new ClasseProcessual("RMS","IP-aRECURSO ORD. EM MANDADO DE SEGURANÇA"));
		classesProcessuais.add(new ClasseProcessual("RMS-a","MI-aRECURSO ORD. EM MANDADO DE SEGURANÇA"));
		classesProcessuais.add(new ClasseProcessual("RNE","MS-aRETIFICAÇÃO DE NOME DE ESTRANGEIRO"));
		classesProcessuais.add(new ClasseProcessual("Rp","PA-aREPRESENTAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("Rp-a","PAv-aREPRESENTAÇÃO"));
		classesProcessuais.add(new ClasseProcessual("RR","Pet-aRECURSO DE REVISTA"));
		classesProcessuais.add(new ClasseProcessual("RR-a","PPE-aRECURSO DE REVISTA"));
		classesProcessuais.add(new ClasseProcessual("RTC","QC-aREVISTA CÍVEL"));
		classesProcessuais.add(new ClasseProcessual("RTC-a","RC-aREVISTA CÍVEL"));
		classesProcessuais.add(new ClasseProcessual("RTCO","Rcl-aREVISTA COMERCIAL"));
		classesProcessuais.add(new ClasseProcessual("RTCO-a","RCR-aREVISTA COMERCIAL"));
		classesProcessuais.add(new ClasseProcessual("RTCR","RE-aREVISTA CRIME"));
		classesProcessuais.add(new ClasseProcessual("RTCR-a","REL-aREVISTA CRIME"));
		classesProcessuais.add(new ClasseProcessual("RvC","RHC-aREVISÃO CRIMINAL"));
		classesProcessuais.add(new ClasseProcessual("RvC-a","RMI-aREVISÃO CRIMINAL"));
		classesProcessuais.add(new ClasseProcessual("RVCR","RMS-aREVISÃO CRIME"));
		classesProcessuais.add(new ClasseProcessual("RVCR-a","Rp-aREVISÃO CRIME"));
		classesProcessuais.add(new ClasseProcessual("SA","RR-aSENTENÇA ARBITRAL"));
		classesProcessuais.add(new ClasseProcessual("SD","RTC-aSUSPENSÃO DE DIREITOS"));
		classesProcessuais.add(new ClasseProcessual("SE","RTCO-aSENTENÇA ESTRANGEIRA"));
		classesProcessuais.add(new ClasseProcessual("SE-a","RTCR-aSENTENÇA ESTRANGEIRA"));
		classesProcessuais.add(new ClasseProcessual("SEC","RvC-aSENTENÇA ESTRANGEIRA CONTESTADA"));
		classesProcessuais.add(new ClasseProcessual("SL","RVCR-aSUSPENSÃO DE LIMINAR"));
		classesProcessuais.add(new ClasseProcessual("SS","SE-aSUSPENSÃO DE SEGURANÇA"));
		classesProcessuais.add(new ClasseProcessual("SS-a","SS-aSUSPENSÃO DE SEGURANÇA"));
		classesProcessuais.add(new ClasseProcessual("STA","RvCSUSPENSÃO DE TUTELA ANTECIPADA"));

		
		return classesProcessuais;
	}
}
