package indice.estrutura;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




public class IndiceSimples extends Indice {
	/**
	 * Versao - para gravação do arquivo binário
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,List<Ocorrencia>> mapIndice = new HashMap<String,List<Ocorrencia>>();

	
	public IndiceSimples() { }


	@Override
	public void index(String termo,int docId,int freqTermo) {
		Ocorrencia ocorrencia = new Ocorrencia(docId, freqTermo);
		if (!mapIndice.containsKey(termo)) {
			List<Ocorrencia> ocorrencias = new ArrayList();
			ocorrencias.add(ocorrencia);
			mapIndice.put(termo, ocorrencias);
		}
		else {
			List<Ocorrencia> OcorrenciaJaFoi = mapIndice.get(termo);
			OcorrenciaJaFoi.add(ocorrencia);
		}
		
	}


	@Override
	public Map<String,Integer> getNumDocPerTerm() {
		
		Map<String, Integer> listaRetorno = new HashMap<String, Integer>();
		
		for(Map.Entry<String,List<Ocorrencia>> entry : mapIndice.entrySet()) {
			listaRetorno.put(entry.getKey(),entry.getValue().size());
		}
		
		return listaRetorno;
	}
	
	@Override
	public int getNumDocumentos() {
		HashSet<Integer> hashSet = new HashSet<Integer>();
		
		for(Map.Entry<String,List<Ocorrencia>> entry : mapIndice.entrySet()) {
			for (Ocorrencia ocorrencia : entry.getValue()) {
				hashSet.add(ocorrencia.getDocId());
			}
		}
		
		return hashSet.size();
	}
	
	@Override
	public Set<String> getListTermos() {
		return mapIndice.keySet();
	}	
	
	@Override
	public List<Ocorrencia> getListOccur(String termo) {
		return mapIndice.get(termo);
	}	
}
