import ptstemmer.exceptions.PTStemmerException;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws PTStemmerException, IOException {
        long time = System.currentTimeMillis();

        WikiIndex wikiIndex = new WikiIndex("C:/Users/victo/IdeaProjects/Indexador/wikiSample");

        long usedMemBefore = usedMem();
        wikiIndex.startIndex();
        long usedMemAfter = usedMem();

        wikiIndex.gravaIndice(new File("C:/Users/victo/IdeaProjects/Indexador/index.obj"));

        System.out.println("Indice gravado em: "+ (System.currentTimeMillis() - time) / (1000.0 * 60));
        System.out.println("Memoria usada: " + (usedMemAfter - usedMemBefore) / (1024.0 * 1024.0));
    }

    public static long usedMem() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
