package janelaAgenda;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main extends JFrame{
	public static Agenda minhaAgenda = new Agenda(); //cria um objeto onde será armazenado os contatos
	//cria-se os componentes da barra de MENU
	private JMenuItem menuAbreAgenda = new JMenuItem("Abrir Agenda");
	private JMenuItem menuFechaAgenda = new JMenuItem("Fechar Agenda");
	private JRadioButtonMenuItem[] menuItemEstilo = new JRadioButtonMenuItem[3];
	
	private JMenu menuAgenda = new JMenu("Agenda");
	private JMenu menuEstilo = new JMenu("Estilo");
	
	private JMenuBar menuBar = new JMenuBar();
	
	private InterfaceAgenda agenda = null;
	
	private JDesktopPane desktop; //cria-se a janela principal
	private ButtonGroup group = new ButtonGroup();

	public Main() { //método construtor, que monta a janela principal do programa
		super("TRABALHO SOBRE INTERFACE GRÁFICA");

		desktop = new JDesktopPane(){
		    //adiciona uma imagem de fundo para o desktop
		    public void paintComponent(Graphics g){
			    try{
				    super.paintComponents(g);
				
				    Image img = ImageIO.read(new java.net.URL(this.getClass().getResource("imagens/Desktop.png"), "Desktop.png"));
					    if (img != null){
						    g.drawImage(img, 0, 0, 1600, 790, this);
					    }
			    }
			
			    catch(Exception e){
				    e.printStackTrace();
			    }
		    }
	    };
	    
	    //cor do fundo do desktop
	    desktop.setBackground(Color.WHITE);
	    
	    //seta os ícones dos menus
	    menuAgenda.setIcon(new ImageIcon(this.getClass().getResource("imagens/Agenda2.png")));
	    
	    menuAbreAgenda.setIcon(new ImageIcon(this.getClass().getResource("imagens/Agenda.png")));
	    
	    menuEstilo.setIcon(new ImageIcon(this.getClass().getResource("imagens/Estilo.png")));
	    
	    //evento do botao do menu Abrir Agenda
	    menuAbreAgenda.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		menuAgendaActionPerformed(e);
	    	}
	    });
	    
	    menuFechaAgenda.setIcon(new ImageIcon(this.getClass().getResource("imagens/Fechar.png")));
	    menuFechaAgenda.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		menuFechaAgendaActionPerformed(e);
	    	}
	    });
	    

	    //adicionando no menu Estilo as opções de estilo
	    menuItemEstilo[0] = new JRadioButtonMenuItem("Metal");
	    menuItemEstilo[0].setSelected(true);
	    menuItemEstilo[0].setIcon(new ImageIcon(this.getClass().getResource("imagens/Java.png")));
	    menuItemEstilo[1] = new JRadioButtonMenuItem("Motif");
	    menuItemEstilo[1].setIcon(new ImageIcon(this.getClass().getResource("imagens/Linux.png")));
	    menuItemEstilo[2] = new JRadioButtonMenuItem("Windows");
	    menuItemEstilo[2].setIcon(new ImageIcon(this.getClass().getResource("imagens/Windows.png")));
	 
	    //método que muda o estilo da janela
	    for (int i = 0; i < menuItemEstilo.length; i++){
	    	group.add(menuItemEstilo[i]);
	    	menuEstilo.add(menuItemEstilo[i]);
	    	menuItemEstilo[i].addItemListener(new ItemListener(){
	    		public void itemStateChanged(ItemEvent e){
	    			menuEstiloItemStateChanged(e);
	    		}
	    	});
	    }
	    
	    //Adicionando os menus à barra
	    menuAgenda.add(menuAbreAgenda);
	    menuAgenda.addSeparator();
	    menuAgenda.add(menuFechaAgenda);
	    
	    menuBar.add(menuAgenda);
	    menuBar.add(menuEstilo);

	    this.getContentPane().add(desktop);
	    
	    this.setJMenuBar(menuBar);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
    }
	
	//ação ao clicar mandar Abrir Agenda
	private void menuAgendaActionPerformed(ActionEvent e){
		agenda = InterfaceAgenda.getInstance();
		desktop.remove(agenda);
		desktop.add(agenda);
		agenda.setVisible(true);
	}
	
	private void menuFechaAgendaActionPerformed(ActionEvent e){
		//verifica se já existe instancia
		if (InterfaceAgenda.isInstance()){
			int ret = JOptionPane.showConfirmDialog(this,
				"Deseja Fechar?",
				"Fechar",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.OK_OPTION){
				agenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				agenda.destroyInstance();
			}
		}
	}
	
	private void menuEstiloItemStateChanged(ItemEvent e){
		for (int i = 0; i < menuItemEstilo.length; i++){
			if (menuItemEstilo[i].isSelected()){
				atualizar(i);
			}
		}
	}
	
	public void atualizar(int valor){
		UIManager.LookAndFeelInfo[] look = UIManager.getInstalledLookAndFeels();
		try{
			UIManager.setLookAndFeel(look[valor].getClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Main m = new Main(); //chama o método que contrói a janela principal do programa
	}
}
