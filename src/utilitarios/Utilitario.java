package utilitarios;

import java.awt.Component;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

public class Utilitario {
	
	public static void bloquearLiberarCampos(JPanel panel, boolean acao){
	    Component[] componentes = panel.getComponents(); // altere para o nome da variavel do seu painel
	    
	    for (Component componente : componentes) {
    		componente.setEnabled(acao);
	    }
	} 
	
	public static void bloquearLiberarCampos2Niveis(JPanel panel, boolean acao){
	    Component[] componentes = panel.getComponents(); // altere para o nome da variavel do seu painel
	    
	    for (Component componente : componentes) {
	    	JPanel neto=(JPanel)componente;
	    	bloquearLiberarCampos(neto,acao);
    		//componente.setEnabled(acao);
	    }
	}
	
	public static void exibirEsconderCampos(JPanel panel, boolean acao){
	    Component[] componentes = panel.getComponents(); // altere para o nome da variavel do seu painel
	    
	    for (Component componente : componentes) {
    		componente.setVisible(acao);
	    }
	}
	
	public static Date converteStringParaDate(String texto){
		Date ret=null;
		try {
			ret= new SimpleDateFormat("dd/MM/yyyy").parse(texto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;

	}
	
    public static String escolherArquivo() {
    	
    	String arquivo=null;
    	
        try {

            //Configurando o meu JFileChooser
            JFileChooser jfc = new JFileChooser();
            
            jfc.setSelectedFile(new File("certidao.doc"));
            
            //Configura o filtro de seleção
            jfc.setFileFilter(new FileNameExtensionFilter("Arquivo Word (*.doc)", "doc"));
            
            //Configura o Label do botão de seleção
            jfc.setApproveButtonText("Selecionar");
            //Configura o Título da caixa de Dialogo
            jfc.setDialogTitle("Selecionar");
            //Configura a possíbilidade de selecionar vários arquivos
            jfc.setAcceptAllFileFilterUsed(false);

            int returnVal = jfc.showOpenDialog(null);
                        
            if (!(returnVal == JFileChooser.APPROVE_OPTION)) { // Teste o usuário seleionou algo
                return null;
            }
            
            arquivo = jfc.getSelectedFile().getAbsolutePath();
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return arquivo;
    }
    

    
    public static MaskFormatter buscaMascaraProcesso(){
		MaskFormatter mascaraProcesso=null;
		
		try{
			mascaraProcesso = new MaskFormatter("#######-##.####.5.01.####");
			mascaraProcesso.setValidCharacters("0123456789");
		}catch (Exception e){
			System.out.println("Máscara de processo inválida!");
		}
		return mascaraProcesso;		
	}    

    
    public static MaskFormatter buscaMascaraData(){
		MaskFormatter mascaraProcesso=null;
		
		try{
			mascaraProcesso = new MaskFormatter("##/##/####");
			mascaraProcesso.setValidCharacters("0123456789");
		}catch (Exception e){
			System.out.println("Máscara de data inválida!");
		}
		return mascaraProcesso;		
	}
    
    public static MaskFormatter buscaMascaraValor(){
		MaskFormatter mascaraProcesso=null;
		
		try{
			mascaraProcesso = new MaskFormatter("###.###.###,##");
			mascaraProcesso.setValidCharacters("0123456789");
		}catch (Exception e){
			System.out.println("Máscara de data inválida!");
		}
		return mascaraProcesso;		
	}
    
    public static boolean isDataValida(String dataTexto){
    	
    	boolean isValida=true;
    	
    	Date data = null;

    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    		format.setLenient(false);
    		data = format.parse(dataTexto);
    	} 
    	catch (ParseException e) {
    		isValida=false;
    	}
    	return isValida;
    	
    }
}
