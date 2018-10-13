package teste;

import java.util.ArrayList;

import modelo.Advogado;
import modelo.Parte;
import modelo.Setor;
import dao.ProcessoPJe1GDao;
import dao.ProcessoSapwebDao;

public class Teste {
	public static void main(String[] args) {
		new Teste().testaSigla();

	}

	private void testaSigla(){
		Setor s = new Setor();
		s.setSiglaSetor("VT38RJ");
		System.out.println(s.getParteNumerica() + "/" + s.getParteNaoNumerica());
	
		s.setSiglaSetor("VT1BP");
		System.out.println(s.getParteNumerica() + "/" + s.getParteNaoNumerica());

		
		s.setSiglaSetor("VT1MAC");
		System.out.println(s.getParteNumerica() + "/" + s.getParteNaoNumerica());
	}
	
	private void executa() {
		
		//sapweb
		try {
			ArrayList<Parte> partes = new ProcessoSapwebDao()
					.buscaDadosDasPartes("00292000420085010007");

			for (Parte pt : partes) {
				System.out.println("Parte:" + pt.getNome() + " CPF: "
						+ pt.getCPF());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ArrayList<Advogado> advogados = new ProcessoSapwebDao()
					.buscaDadosDosAdvogados("00292000420085010007");

			for (Advogado adv : advogados) {
				System.out.println("Advogado:" + adv.getNome() + " OAB: "
						+ adv.getOAB());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		//pje
		
		try {
			ArrayList<Parte> partes = new ProcessoPJe1GDao()
					.buscaDadosDasPartes("0010000-19.2012.5.01.0541");

			for (Parte pt : partes) {
				System.out.println("Parte:" + pt.getNome() + " CPF: "
						+ pt.getCPF());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ArrayList<Advogado> advogados = new ProcessoPJe1GDao()
					.buscaDadosDosAdvogados("0010000-19.2012.5.01.0541");

			for (Advogado adv : advogados) {
				System.out.println("Advogado:" + adv.getNome() + " OAB: "
						+ adv.getOAB());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		
		
	}
}
