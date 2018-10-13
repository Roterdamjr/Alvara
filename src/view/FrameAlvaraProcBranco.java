package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Advogado;
import modelo.Parte;
import modelo.Processo;
import utilitarios.Utilitario;
import dao.ProcessoPJe1GDao;
import dao.ProcessoSapwebDao;


public class FrameAlvaraProcBranco extends JFrame {

	
	private JPanel contentPane;
	private JComboBox<String> cboBenficiario;
	Processo processo= new Processo();
	JFormattedTextField fmtProcesso;
	JButton btnSelProcesso;
	JButton btnCriaLinha;
	private JTable tblProcEmBranco;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAlvaraProcBranco frame = new FrameAlvaraProcBranco();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameAlvaraProcBranco() {
		setTitle("Alvar\u00E1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1257, 511);
		contentPane = new JPanel();


		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 20));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel_2 = new JLabel("Processo");
		panel.add(lblNewLabel_2);

		fmtProcesso = new JFormattedTextField(
		Utilitario.buscaMascaraProcesso());


		fmtProcesso.setText("0162000-57.2009.5.01.0040");
		fmtProcesso.setColumns(15);
		panel.add(fmtProcesso);

		btnSelProcesso = new JButton("Seleciona");
		btnSelProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscaDadosDeProcessoDoBanco();
			}
		});
		panel.add(btnSelProcesso);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(4, 1, 0, 20));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);

		JLabel lblNewLabel = new JLabel("Nome Beneficiario");
		panel_3.add(lblNewLabel);

		cboBenficiario = new JComboBox<String>();
		panel_3.add(cboBenficiario);

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);

		btnCriaLinha = new JButton("Cria Linha");
		panel_4.add(btnCriaLinha);
		btnCriaLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				criaLinha();
			}
		});

		tblProcEmBranco = new JTable();
		tblProcEmBranco.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "Processo", "Nome_parte", "CPF_OAB", "New column", "New column", "New column", "New column", "New coluum", "New column", "New column", "New column", "New column", "New column", "VT", "Local VT", "New column", "OAB"
			}
		));
		contentPane.add(tblProcEmBranco, BorderLayout.SOUTH);

		JScrollPane scrollPane_1 = new JScrollPane(tblProcEmBranco);
		panel_1.add(scrollPane_1);
		
		/*
		 * ======================================================= 
		 * C´O D I G O P
		 * * P E R S O N A L I Z A D O
		 * ======================================================
		 */
		testaConexao();

		//se pressionar ENTER com foco no campo de processo dispara botão "Seleciona"
		fmtProcesso.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if	(evt.getKeyCode()	==	KeyEvent.VK_ENTER)	{	
					habilitaDesabilitaCompentes(false);
					new ThreadDadosDeBanco().execute();		
				}	
			}
		});			
		
		//colunas pequenas
		
		for (int i=4;i<=13;i++){
			tblProcEmBranco.getColumnModel().getColumn(i).setPreferredWidth(5);
		}
	}

    private class ThreadDadosDeBanco extends SwingWorker {
    	
        protected Object doInBackground() throws Exception {
        	buscaDadosDeProcessoDoBanco();
        	//try { Thread.sleep (5000); } catch (InterruptedException ex) {}
            return null;
        }
        
        @Override
        public void done() {
        	habilitaDesabilitaCompentes(true);
        }
       
    }
    
    private void habilitaDesabilitaCompentes(boolean acao){
    	fmtProcesso.setEnabled(acao);
		btnSelProcesso.setEnabled(acao);
		cboBenficiario.setEnabled(acao);
		cboBenficiario.setEnabled(acao);;

    }
    
	private void buscaDadosDeProcessoDoBanco() {		

		String numCnj=fmtProcesso.getText().toString();
		
		try {
			processo=new ProcessoSapwebDao().buscaDadosDoProcessoParaAlvara(numCnj);
			if(processo==null){
				System.out.println("processo não encontrado ");
				processo=new  ProcessoPJe1GDao().buscaDadosDoProcessoParaAlvara(numCnj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		populaCombos();			
	}
	
	private void populaCombos() {
		
		//limpa combos
		cboBenficiario.removeAllItems();
		
		//preenche
		for(Parte parte:processo.getPartes()){
			cboBenficiario.addItem(parte.getNome());
		}

		for(Advogado adv:processo.getAdvogados()){
			cboBenficiario.addItem(adv.getNome());
		}
	}
	
	
	private void criaLinha() {
		String nomeBeneficiario=cboBenficiario.getSelectedItem().toString();
		String OAB=null,CPF_OAB=null;
		
		Parte pteSelecionada=buscaParteSelecionada(nomeBeneficiario);
		
		if(pteSelecionada!=null){
			CPF_OAB=pteSelecionada.getCPF();
			
		}else{
			Advogado adv= buscaAdvSelecionada(nomeBeneficiario);
			OAB=adv.getOAB();
			CPF_OAB=OAB;
		}
		
		Object[] linha=new Object[] {
				null,processo.getNumCnj(),nomeBeneficiario,
				CPF_OAB,
				null,null,null,null,null,null,	null,null,null,null,
				processo.getSetor().getParteNumerica() ,
				processo.getSetor().getParteNaoNumerica(),null,
				OAB
				};								
	
		DefaultTableModel modelo = (DefaultTableModel) tblProcEmBranco.getModel();
		modelo.addRow(linha);
	}
	
	

	private Parte buscaParteSelecionada(String nome){
		Parte parte=null;
		for(Parte pte:processo.getPartes()){
			if(pte.getNome().equals(nome)){
				parte=pte;
				break;
			}
		}
		return parte;
	}
	
	private Advogado buscaAdvSelecionada(String nome){
		Advogado advogado=null;
		for(Advogado adv:processo.getAdvogados()){
			if(adv.getNome().equals(nome)){
				advogado=adv;
				break;
			}
		}
		return advogado;
	}
	
	private Advogado buscaAdvogadoelecionado(String nome){
		Advogado advogado=null;
		for(Advogado obj:processo.getAdvogados()){
			if(obj.getNome().equals(nome)){
				advogado=obj;
				break;
			}
		}
		return advogado;
	}
	
	private void testaConexao(){
		try{
		 ProcessoPJe1GDao  daoPJe1G=new ProcessoPJe1GDao();
	    } catch (Exception e) {
	         e.printStackTrace();
	         System.out.println("Erro de conexão no banco PJe1G: "+ e.getMessage());  
	         JOptionPane.showMessageDialog(null,  "Erro de conexão no banco PJe1G: ", "Erro" , JOptionPane.ERROR_MESSAGE);
	         System.exit(0);
	    }		
		
/*		try{
			ProcessoPJe2GDao daoPJe2G=new ProcessoPJe2GDao();
	    } catch (Exception e) {
	         e.printStackTrace();
	         System.out.println("Erro de conexão no banco PJe2G: "+ e.getMessage());  
	         JOptionPane.showMessageDialog(null,  "Erro de conexão no banco PJe2G: ", "Erro", JOptionPane.ERROR_MESSAGE);
	         System.exit(0);
	    }	*/
		
		try{
			ProcessoSapwebDao daoSapweb=new  ProcessoSapwebDao();
	    } catch (Exception e) {
	         e.printStackTrace();
	         System.out.println("Erro de conexão no banco Sapweb: "+ e.getMessage());  
	         JOptionPane.showMessageDialog(null,  "Erro de conexão no banco Sapweb: ", "Erro", JOptionPane.ERROR_MESSAGE);
	         System.exit(0);
	    }	
	}
	private void populaDadosParaTeste(){
		Processo processo=new Processo();

		Parte parte1=new Parte();
		
		parte1.setNome("Armando");
		parte1.setCPF("796.329.477/20");
		processo.addParte(parte1);
		
		Parte parte2=new Parte();
		parte2.setNome("Bruno");
		parte2.setCPF("011.789.900/23");
		processo.addParte(parte2);		
		
		Advogado advogado1 = new Advogado(); 
		advogado1.setNome("Rui Barbosa");		
		advogado1.setOAB("RJ 1000/D");
		processo.addAdvogado(advogado1);
		
		Advogado advogado2 = new Advogado(); 
		advogado2.setNome("Nelos Hungria");		
		advogado2.setOAB("RJ 2000/D");
		processo.addAdvogado(advogado2);
		

		this.processo= processo;
	}
}
