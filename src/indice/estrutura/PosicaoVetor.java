package indice.estrutura;

import java.io.Serializable;

public class PosicaoVetor implements Serializable{
	private int idTermo;
	private int posInicial;
	private int numDocumentos;
	
	public PosicaoVetor(int idTermo) {
		this.idTermo = idTermo;
	}

	public int getIdTermo() {
		return idTermo;
	}
	public int getNumDocumentos() {
		return numDocumentos;
	}
	public int getPosInicial(){
		return this.posInicial;
	}
	public void setPosInicial(int posInicial){
		this.posInicial = posInicial;
	}
	public void setNumDocumentos(int num){
		this.numDocumentos = num;
	}
}
