package janelaAgenda;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FormCadAgenda extends JDialog{
	
	//serão criados os componentes JLabel, JTEXT, e JButton do formulário
	private JLabel labelNome = new JLabel("Nome:");;
	private JLabel labelTelefone = new JLabel("Telefone:");;
	private JLabel labelEmail = new JLabel("Email:");
	private JLabel labelEndereco = new JLabel("Endereco:");

	private JTextField textNome = new JTextField();
	private JTextField textTelefone = new JTextField();
	private JTextField textEmail = new JTextField();
	private JTextField textEndereco = new JTextField();

	private JButton buttonGravar;
	private JButton buttonFechar;

	private InterfaceAgenda pai;

	private String codigo = "";
	
	private boolean edicao = false; //variável de teste que será entendita mais à frente
	public FormCadAgenda(InterfaceAgenda newPai){
		this.pai = newPai;
	}
	
	//Método construtor
	public FormCadAgenda(InterfaceAgenda newPai, String codigo, String nome, String telefone, String email, String endereco){
		this(newPai);
		this.edicao = true;
		this.codigo = codigo;
		this.textNome.setText(nome);
		this.textTelefone.setText(telefone);
		this.textEmail.setText(email);
		this.textEndereco.setText(endereco);
	}
	
	void inicia(){ //este método é responsável por mostrar o formulário na agenda
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //oculta a janela ao fechar
		buttonGravar = new JButton("Gravar");
		buttonFechar = new JButton("Fechar");
		
		Container pane = this.getContentPane(); //cria Pane (container)
		//neste exemplo o layout é nulo então a disposição
		//dos componentes é definida atraves da propriedade setBounds
		pane.setLayout(null);
		
		//define posição do formulário(x,y) e altura e largura de cada component.
		labelNome.setBounds(10, 10, 50, 20);
		labelTelefone.setBounds(10, 41, 80, 20);
		labelEmail.setBounds(10, 72, 50, 20);
		labelEndereco.setBounds(10, 102, 80, 20);
		
		textNome.setBounds(70, 10, 150, 20);
		textTelefone.setBounds(70, 41, 70, 20);
		textEmail.setBounds(70, 72, 150, 20);
		textEndereco.setBounds(70, 102, 150, 20);
		
		buttonGravar.setBounds(68, 140, 80, 22);
		buttonFechar.setBounds(150, 140, 80, 22);
		
		//este método define ação do botao gravar
		buttonGravar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				buttonGravarActionPerformed(e); //ao clicar, chama este método
			}
		});
		
		//a mesma coisa do método anterior
		buttonFechar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				buttonFecharActionPerformed(e);
			}
		});
		
		// iremos adicionar os compenentes no container
		pane.add(labelNome);
		pane.add(labelTelefone);
		pane.add(labelEmail);
		pane.add(labelEndereco);
		
		pane.add(textNome);
		pane.add(textTelefone);
		pane.add(textEmail);
		pane.add(textEndereco);
		
		pane.add(buttonGravar);
		pane.add(buttonFechar);
		
		this.setBounds(40, 60, 250, 210); //seta o posicionamento
		this.setContentPane(pane);
		this.setVisible(true);	
	}
	
	//definindo o evento do Botão Gravar
	private void buttonGravarActionPerformed(ActionEvent e){
		int resposta = 0;
		//verifica se já é um arquivo novo pra inclusão ou apenas edição e um já cadastrado
		if (edicao == false){
			resposta = JOptionPane.showConfirmDialog(this, "Incluir Registro?", "Novo",
			//sendo falso é porque é novo "Inclusão",
			JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if (resposta == JOptionPane.OK_OPTION){
			//fazendo a inclusão do contato na agenda
			Pessoa p = new Pessoa(textNome.getText(),
			textTelefone.getText(),
			textEmail.getText(),
			textEndereco.getText());
			Main.minhaAgenda.add(p);
			
			JOptionPane.showMessageDialog(this, "Inclusão efetuada!!!", "Inclusão",
			JOptionPane.INFORMATION_MESSAGE);
			
			//adiciona uma linha nova de cadastro na tela pricipal da agenda
			pai.addRow(new String[]{p.getCodigo(),
			p.getNome(),
			p.getTelefone(),
			p.getEmail(),
			p.getEndereco()});

			this.dispose();
		}
		
		else{ //se edicao = true indica que é uma alteração.
			resposta = JOptionPane.showConfirmDialog(this,
			"Salvar Alterações",
			"Edição",
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE);

			if (resposta == JOptionPane.OK_OPTION){
				Pessoa p = Main.minhaAgenda.getPessoa(codigo);

				p.setValores(new String[]{textNome.getText(),
				textTelefone.getText(),
				textEmail.getText(),
				textEndereco.getText()});

				JOptionPane.showMessageDialog(this,
				"Edição efetuada!!!",
				"Edição",
				JOptionPane.INFORMATION_MESSAGE);

				pai.atualizaTabela(new String[]{p.getCodigo(),
				p.getNome(),
				p.getTelefone(),
				p.getEmail(),
				p.getEndereco()});

				this.dispose();
			}
		}
	}
		//fecha a janela do formulário.
		private void buttonFecharActionPerformed(ActionEvent e){
		this.dispose();
	}
}
