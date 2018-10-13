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




public class FrameAlvara extends JFrame {
/*
	0101921-48.2017.5.01.0003	FABIO OLIVEIRA GOMES											RJ	RJ	112.287.307-76	PRECILIANA VITAL ANTUNES	RJ58586
	0101921-48.2017.5.01.0003	CARREFOUR COMERCIO E INDUSTRIA LTDA								RJ	RJ	45.543.915/0001-81	PRECILIANA VITAL ANTUNES	RJ58586
	0101921-48.2017.5.01.0003	LIANA OLIVEIRA SOARES											RJ	RJ	080.887.457-80	PRECILIANA VITAL ANTUNES	RJ58586
	0101921-48.2017.5.01.0003	LIANA OLIVEIRA SOARES											RJ	RJ	080.887.457-80	MARIA HELENA VILLELA AUTUORI ROSA	
	0101921-48.2017.5.01.0003	LIANA OLIVEIRA SOARES											RJ	RJ	080.887.457-80	TATIANE DE CICCO NASCIMBEM CHADID	
	*/
/*	
	0162000-57.2009.5.01.0040	WILSON MARIA DOS ANJOS FILHO 	7151948794							40	RJ		
	0162000-57.2009.5.01.0040	JULIANA LOPES DA COSTA	RJ108820D									40	RJ		RJ108820D
	0162000-57.2009.5.01.0040	EMPRESA DE VIAÇÃO ALGARVE LTDA	1435418000194						40	RJ		
	*/
	private JPanel contentPane;
	private JTable tblAlvara;
	private JComboBox<String> cboParte, cboAdvogado;
	Processo processo= new Processo();
	JFormattedTextField fmtProcesso;
	JButton btnSelProcesso;
	JButton btnCriaLinha;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAlvara frame = new FrameAlvara();
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
	public FrameAlvara() {
		setTitle("Alvar\u00E1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1257, 463);
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


		fmtProcesso.setText("0099300-15.2007.5.01.0008");
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

		JLabel lblNewLabel = new JLabel("Nome Parte");
		panel_3.add(lblNewLabel);

		cboParte = new JComboBox<String>();
		panel_3.add(cboParte);

		JLabel lblNewLabel_1 = new JLabel("Nome Advogado");
		panel_3.add(lblNewLabel_1);

		cboAdvogado = new JComboBox<String>();
		panel_3.add(cboAdvogado);

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);

		btnCriaLinha = new JButton("Cria Linha");
		panel_4.add(btnCriaLinha);
		btnCriaLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				criaLinha();
			}
		});
		


		tblAlvara = new JTable();
		tblAlvara.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No.", "Processo", "Nome Parte", "New column", "New column", "New column", "New column", "New column", 
				"New column", "New column", "New column", "New column", "New column", "VT", "LOCAL VT", "CPF Parte", "Nome Advogado", "OAB"
			}
		));

		contentPane.add(tblAlvara, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane(tblAlvara);
		panel_1.add(scrollPane);
		
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
		for (int i=3;i<=11;i++){
			tblAlvara.getColumnModel().getColumn(i).setPreferredWidth(5);
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
		cboAdvogado.setEnabled(acao);
		cboParte.setEnabled(acao);;
		cboAdvogado.setEnabled(acao);
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
		cboParte.removeAllItems();
		cboAdvogado.removeAllItems();
		
		//preenche
		for(Parte parte:processo.getPartes()){
			cboParte.addItem(parte.getNome());
		}

		for(Advogado adv:processo.getAdvogados()){
			cboAdvogado.addItem(adv.getNome());
		}
	}
	
	
	private void criaLinha() {
		String nomePte=cboParte.getSelectedItem().toString();
		Parte pteSelecionada=buscaParteSelecionada(nomePte);
		criaLinhaTabelaAlvara(nomePte, pteSelecionada);
	}
	
	private void criaLinhaTabelaAlvara(String nomePte,Parte pteSelecionada) {
		
		String nomeAdv=cboAdvogado.getSelectedItem().toString();
		Advogado advSelecionado= buscaAdvogadoelecionado(nomeAdv);
		
		Object[] linha=new Object[] {
				null,processo.getNumCnj(), nomePte, 
				null,null,null,null,null,null,null,null,null,null,
				processo.getSetor().getParteNumerica() ,
				processo.getSetor().getParteNaoNumerica(),
				pteSelecionada.getCPF(),nomeAdv,advSelecionado.getOAB()};								
	
		DefaultTableModel modelo = (DefaultTableModel) tblAlvara.getModel();
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
