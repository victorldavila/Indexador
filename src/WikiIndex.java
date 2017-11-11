import indice.estrutura.Indice;
import indice.estrutura.IndiceLight;
import indice.estrutura.Ocorrencia;
import org.clapper.util.html.HTMLUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import ptstemmer.Stemmer;
import ptstemmer.exceptions.PTStemmerException;
import ptstemmer.implementations.OrengoStemmer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WikiIndex {
    private File dirWikiSample;
    private Stemmer stemmer ;
    private Indice indiceLight;

    public WikiIndex(String dirPath) throws PTStemmerException {
        dirWikiSample = new File(dirPath);

        indiceLight = new IndiceLight(1500);
        stemmer = new OrengoStemmer();
    }

    public void startIndex() throws IOException {
        int numeroIndex = 1;

        for(File dirWiki : dirWikiSample.listFiles()) {
            if(dirWiki.isDirectory()) {
                for(File arqWiki : dirWiki.listFiles()) {
                    //System.out.println("Indexando artigo numero: " + numeroIndex + " " + arqWiki.getAbsolutePath());

                    String txtLimpo = readFile(arqWiki);

                    indexFile(arqWiki.getName().replaceAll("\\.html", ""), txtLimpo);

                    numeroIndex++;
                }
            }
        }

        indiceLight.concluiIndexacao();
    }

    public void indexFile(String docId, String txtLimpo) {
        String[] arrTermos = txtLimpo.split("\\W+");

        Map<String, Integer> mapFreqTermDoc = getFreqTerm(arrTermos);

        index(Integer.valueOf(docId), mapFreqTermDoc);
    }

    private void index(Integer docId, Map<String, Integer> mapFreqTermDoc) {
        for(String term : mapFreqTermDoc.keySet()) {
            indiceLight.index(term, docId, mapFreqTermDoc.get(term));
        }
    }

    public Map<String,Integer> getFreqTerm(String[] terms) {
        int sizeArray = terms.length;

        Map<String, Integer> mapFreqTerm = new HashMap<>();

        for(int index = 0; index < sizeArray; index++) {
            if(!terms[index].isEmpty()) {
                if(!StringUtil.isStopWord(terms[index])) {
                    if(!mapFreqTerm.containsKey(stemmer.getWordStem(terms[index]))) {
                        mapFreqTerm.put( stemmer.getWordStem(terms[index]), 1);
                    } else {
                        mapFreqTerm.put(stemmer.getWordStem(terms[index]), mapFreqTerm.get(stemmer.getWordStem(terms[index])) + 1);
                    }
                }
            }
        }

        return mapFreqTerm;
    }

    public String cleanFile(String txtLimpo) {
        txtLimpo = StringUtil.replaceAcento(txtLimpo);
        txtLimpo = txtLimpo.toLowerCase();

        return txtLimpo;
    }

    private String readFile(File arqWiki) throws IOException {
        String txtFile = ArquivoUtil.leTexto(arqWiki);
        Document docText = Jsoup.parse(txtFile);
        // Get back the string of the body.
        txtFile = docText.body().text();
        return cleanFile(txtFile);
    }

    public void gravaIndice(File f) throws IOException {
        ObjectOutputStream arqOutput = new ObjectOutputStream(	new FileOutputStream(f));
        //BufferedWriter bufferedWriter = new BufferedWriter(	new FileWriter(f));
        arqOutput.writeObject(indiceLight);
        //Set<String> termos = indiceLight.getListTermos();

        /*for (String termo : indiceLight.getListTermos()) {
            for (Ocorrencia oc : indiceLight.getListOccur(termo)) {
                String line = termo + ": <" + String.valueOf(oc.getDocId()) + ", " + String.valueOf(oc.getFreq()) + ">";
                //System.out.println(line);
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }*/

        arqOutput.writeObject(indiceLight);
        //bufferedWriter.close();
        arqOutput.close();
    }
}
