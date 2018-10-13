package modelo;

public class Setor {
	private String siglaSetor;

	public String getParteNumerica(){

		String texto=siglaSetor.replace("VT","");
		String retorno="";

		for (int i=0;i<texto.length();i++){
			String caracter=texto.substring(i,i+1);

			if (isNumerico(caracter)) {
				retorno+= caracter;
			}
		}
		return retorno;
	}
	
	public String getParteNaoNumerica(){

		String texto=siglaSetor.replace("VT","");
		String retorno="";

		for (int i=0;i<texto.length();i++){
			String caracter=texto.substring(i,i+1);

			if (!isNumerico(caracter)) {
				retorno+= caracter;
			}
		}
		return retorno;
	}
	
	private boolean isNumerico(String s) {
	    return java.util.regex.Pattern.matches("\\d+", s);
	}
	
	public String getSiglaSetor() {
		return siglaSetor;
	}

	public void setSiglaSetor(String siglaSetor) {
		this.siglaSetor = siglaSetor;
	}
	
	
}
