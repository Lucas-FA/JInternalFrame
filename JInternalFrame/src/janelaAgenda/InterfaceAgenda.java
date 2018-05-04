package janelaAgenda;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

public class InterfaceAgenda extends JInternalFrame{
	//primeiramente criamos os componentes
	private JTable tabela;
	
	private JButton buttonNovo = new JButton("Novo");
	private JButton buttonEditar = new JButton("Editar");
	private JButton buttonRemover = new JButton("Remover");
	private JButton buttonFechar = new JButton("Fechar");
	
	private FormCadAgenda formCadAgenda; //objeto do tipo formulário criado
	private static InterfaceAgenda INSTANCE = null;
	//definindo o modelo da tabela
	private DefaultTableModel modelo = new DefaultTableModel();
	//Método que constrói a janela da agenda
	public InterfaceAgenda(){
		super("Agenda", true, true , false, true);
		Container pane = this.getContentPane();
		pane.setLayout(null);
		
		//define posicionamento do botão na janela
		buttonNovo.setBounds(5, 10, 90, 70);
		buttonNovo.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonNovo.setVerticalAlignment(SwingConstants.TOP);
		buttonNovo.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonNovo.setIcon(new ImageIcon(getClass().getResource("imagens/Novo.png")));
		buttonNovo.addActionListener(new ActionListener(){ //ao clicar…
			public void actionPerformed(ActionEvent e){
				buttonNovoActionPerformed(e); //chama esse método
			}
		});
		
		//define posicionamento do botão na janela
		buttonEditar.setBounds(95, 10, 90, 70);
		buttonEditar.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonEditar.setVerticalAlignment(SwingConstants.TOP);
		buttonEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonEditar.setIcon(new ImageIcon(getClass().getResource("imagens/Alterar.png")));
		buttonEditar.addActionListener(new ActionListener(){ //ao clicar…
			public void actionPerformed(ActionEvent e){
                buttonEditarActionPerformed(e); //chama esse método
			}
		});
		
		//define posicionamento do botão na janela
		buttonRemover.setBounds(185, 10, 90, 70);
		buttonRemover.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonRemover.setVerticalAlignment(SwingConstants.TOP);
		buttonRemover.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonRemover.setIcon(new
		ImageIcon(getClass().getResource("imagens/Remover.png")));
		buttonRemover.addActionListener(new ActionListener(){ //ao clicar…
			public void actionPerformed(ActionEvent e){
				buttonRemoverActionPerformed(e); //chama esse método
			}
		});
		
		//define posicionamento do botão na janela
		buttonFechar.setBounds(280, 10, 90, 70);
		buttonFechar.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonFechar.setVerticalAlignment(SwingConstants.TOP);
		buttonFechar.setVerticalTextPosition(SwingConstants.BOTTOM);
		buttonFechar.setIcon(new ImageIcon(getClass().getResource("imagens/Fechar.png")));
		buttonFechar.addActionListener(new ActionListener(){ //ao clicar…
			public void actionPerformed(ActionEvent e){
				buttonFecharActionPerformed(e); //chama esse método
			}
		});
		
		this.addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameClosing(InternalFrameEvent e){
			    interfaceAgendaInternalFrameClosing(e);
			}
		});
		
		//montagem da tabela
		tabela = new JTable(modelo);

		modelo.addColumn("Codigo");
		modelo.addColumn("Nome");
		modelo.addColumn("Telefone");
		modelo.addColumn("E-mail");
		modelo.addColumn("Endereço");

		JScrollPane scrollpane = new JScrollPane(tabela);
		scrollpane.setBounds(5, 95, 680, 390);

		pane.add(buttonNovo);
		pane.add(buttonEditar);
		pane.add(buttonRemover);
		pane.add(buttonFechar);
		pane.add(scrollpane);

		preencheTabela();
		
		this.setResizable(false);
		this.setSize(700, 550);
	}
	
	//evento do botão NOVO
	private void buttonNovoActionPerformed(ActionEvent e){
		formCadAgenda = new FormCadAgenda(this);
		formCadAgenda.setModal(true);
		formCadAgenda.inicia();
	}
		
	//evento do botão editar
	private void buttonEditarActionPerformed(ActionEvent e){
		// pega a linha selecionada
		int linha = tabela.getSelectedRow();

		if (linha < 0) //verifica se existe linha
			return;
		else{
			//atribui os valores da linha selecionada para edição no formulário
			String codigo = tabela.getValueAt(linha, 0).toString();
			String nome = tabela.getValueAt(linha,1).toString();
			String telefone = tabela.getValueAt(linha, 2).toString();
			String email = tabela.getValueAt(linha, 3).toString();
			String endereco = tabela.getValueAt(linha, 4).toString();

			formCadAgenda= new FormCadAgenda(this, codigo, nome, telefone, email, endereco);
			formCadAgenda.setModal(true);
			formCadAgenda.inicia();
		}
	}
	
	//evento do botão remover
	private void buttonRemoverActionPerformed(ActionEvent e){
		//mostra o JOptionPane
		int resposta = JOptionPane.showConfirmDialog(this,
		"Deseja remover registro?",
		"Remoção",
		JOptionPane.OK_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE);
		if (resposta == JOptionPane.OK_OPTION){
			//seleciona a linha para remoção
			int linha = tabela.getSelectedRow();
			int coluna = 0;
			
			if (linha < 0)
				return;
			else{
				String codigo = tabela.getValueAt(linha, coluna).toString();
				Main.minhaAgenda.remove(codigo); //remove o contato
				modelo.removeRow(linha); //remove a linha

				JOptionPane.showMessageDialog(this,
				"Registro excluído com sucesso!!!",
				"Remover", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	//evento do botão fechar
	private void buttonFecharActionPerformed(ActionEvent e){
		//mostra mensagem de confirmação de Fechar
		int ret = JOptionPane.showConfirmDialog(this,
		"Deseja Fechar?",
		"Fechar",
		JOptionPane.OK_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE);
		//fecha a janela da agenda caso clique em OK
		if (ret == JOptionPane.OK_OPTION){
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.destroyInstance();
		}
	}
	
	//evendo ao clicar no “X” de fechar a janela ao em vez do botão
	private void interfaceAgendaInternalFrameClosing(InternalFrameEvent e){
		int ret = JOptionPane.showConfirmDialog(this,
			"Deseja Fechar?",
			"Fechar",
		JOptionPane.OK_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE);
		//fecha a janela da agenda caso clique em OK
		if (ret == JOptionPane.OK_OPTION){
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.destroyInstance();
		}
		
		else
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	//método verificar se já existe uma instancia classe InterfaceAgenda
	public static InterfaceAgenda getInstance(){
		if (InterfaceAgenda.INSTANCE == null){
			return INSTANCE = new InterfaceAgenda();
		}
		return INSTANCE;
	}
	
	//método que preenche a JTABLE com os valores passados
	public void preencheTabela(){
		ArrayList lista = Main.minhaAgenda.getList();
		//ordena os contatos por ordem de código
		Collections.sort(lista, Pessoa.ORDEM_CODIGO);
		Iterator it = lista.iterator();
		
		while (it.hasNext()){
			Pessoa p = (Pessoa)it.next();

			modelo.addRow(new String[]{
				p.getCodigo(),
				p.getNome(),
				p.getTelefone(),
				p.getEmail(),
				p.getEndereco()	
			});
		}
	}
	
	public void atualizaTabela(String[] dados){
		int linha = tabela.getSelectedRow();
		
		tabela.setValueAt(dados[0], linha, 0);
		tabela.setValueAt(dados[1], linha, 1);
		tabela.setValueAt(dados[2], linha, 2);
		tabela.setValueAt(dados[3], linha, 3);
		tabela.setValueAt(dados[4], linha, 4);
	}
	
	public InterfaceAgenda destroyInstance(){
		this.dispose();
		return INSTANCE = null;
	}
	
	public static boolean isInstance(){
		if (INSTANCE == null) return false;
	
	return true;
	}
	
	public void addRow(Object[] o){
		modelo.addRow(o);
	}
}

