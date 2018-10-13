package modelo;

import java.util.ArrayList;

public class Processo {
	private String numCnj;
	private String  exequente;
	private String dataDistribuicao;
	private ArrayList<Parte> partes= new  ArrayList<Parte>();
	private ArrayList<Advogado> advogados= new  ArrayList<Advogado>();
	private Setor setor;
	private String sistemaOrigem;
	 
	public void addParte(Parte parte){
		partes.add(parte);
	}
	
	public ArrayList<Advogado> getAdvogados() {
		return advogados;
	}

	public void addAdvogado(Advogado advogado){
		advogados.add(advogado);
	}
	
	public String getSistemaOrigem() {
		return sistemaOrigem;
	}
	public void setSistemaOrigem(String sistemaOrigem) {
		this.sistemaOrigem = sistemaOrigem;
	}
	public String getNumCnj() {
		return numCnj;
	}
	public void setNumCnj(String numCnj) {
		this.numCnj = numCnj;
	}

	public String getExequente() {
		return exequente;
	}
	public void setExequente(String exequente) {
		this.exequente = exequente;
	}
	public String getDataDistribuicao() {
		return dataDistribuicao;
	}
	public void setDataDistribuicao(String dataDistribuicao) {
		this.dataDistribuicao = dataDistribuicao;
	}
	public ArrayList<Parte> getPartes() {
		return partes;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public void setPartes(ArrayList<Parte> partes) {
		this.partes = partes;
	}

	public void setAdvogados(ArrayList<Advogado> advogados) {
		this.advogados = advogados;
	}


	
	
}
