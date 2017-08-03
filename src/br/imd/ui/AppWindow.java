package br.imd.ui;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.imd.model.InsiderThreat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class AppWindow extends JFrame implements ActionListener {
	
	// Declara a InsiderThreat estática para que outras classes acessem
	public static InsiderThreat it;
	
	// Cria a tela de trabalho
	public static JDesktopPane screen = new JDesktopPane();
	
	// Criando o menu
	private JMenuBar mnbar   = new JMenuBar();
	
	// Definindo as guias do menu
	private JMenu fileMenu   = new JMenu("Arquivo");
	
	// Definindo as opções das guias do menu
	JMenuItem menuOpenUsersFile = new JMenuItem("Selecionar Arquivo de Usuário");
	JMenuItem menuOpenLogFile = new JMenuItem("Selecionar Arquivo de Log");
	
	// Definindo botões
	public static JButton buttonViewAllUsers = new JButton("Visualizar Todos os Perfis");
	public static JButton buttonViewUsersByRole = new JButton("Visualizar Perfis Por Cargo");
	public static JButton buttonSearchUserById = new JButton("Pesquisar Perfil");
	public static JButton buttonStartProcessing = new JButton("Iniciar Processamento");
	public static JButton buttonViewSuspects = new JButton("Visualizar Perfis Suspeitos");
	public static JButton buttonSaveProcessing = new JButton("Salvar Processamento");
	
	public AppWindow(){
		// Definindo containers
		Container c1 = this.getContentPane();
		c1.setLayout(new BorderLayout());
		
		Container c2 = this.getContentPane();
		c2.setLayout(new BorderLayout());
		
		// Definindo a tela central
		screen.setBackground(Color.WHITE);
		JLabel label1 = new JLabel("Selecione os arquivos no menu 'Arquivo'", SwingConstants.CENTER);
		label1.setFont(new Font("Calibri", Font.ITALIC, 22));
		label1.setBounds(new Rectangle(new Point(175, 150), label1.getPreferredSize()));
		screen.add(label1);
		
		// Configurando botões
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(6,1));
		buttons.add(buttonViewAllUsers);
		buttons.add(buttonViewUsersByRole);
		buttons.add(buttonSearchUserById);
		buttons.add(buttonStartProcessing);
		buttons.add(buttonViewSuspects);
		buttons.add(buttonSaveProcessing);
		buttonViewAllUsers.setEnabled(false);
		buttonViewUsersByRole.setEnabled(false);
		buttonSearchUserById.setEnabled(false);
		buttonStartProcessing.setEnabled(false);
		buttonViewSuspects.setEnabled(false);
		buttonSaveProcessing.setEnabled(false);
		
		// Configurando o menu
		setJMenuBar(mnbar);
		mnbar.add(fileMenu);
		fileMenu.add(menuOpenUsersFile);
		fileMenu.add(menuOpenLogFile);
		
		// Criando o painel esquerdo
		JPanel side = new JPanel();
		side.add(buttons);
		
		// Inserindo o painel esquerdo e a tela central no Container
		c1.add(BorderLayout.WEST, side);
		c2.add(BorderLayout.CENTER, screen);
		
		// Configura a janela
		setSize(900, 400);
		setResizable(false);
		setTitle("Insider Threat");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		// Action Listeners para quando os botões forem clicados
		menuOpenUsersFile.addActionListener(this);
		menuOpenLogFile.addActionListener(this);
		buttonViewAllUsers.addActionListener(this);
		buttonViewUsersByRole.addActionListener(this);
		buttonSearchUserById.addActionListener(this);
		buttonStartProcessing.addActionListener(this);
		buttonViewSuspects.addActionListener(this);
		buttonSaveProcessing.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		// Ação a ser executada quando o item "Selecionar Arquivo de Usuários" do menu é clicado
		if (e.getSource() == menuOpenUsersFile) {
			screenClear();
			// reinicia a InsiderThreat cada vez que for escolhido o arquivo de usuários
			it = new InsiderThreat();
			OpenUserFiles open = new OpenUserFiles();
			open.setVisible(true);
			screen.add(open);
		}
		// Ação a ser executada quando o item "Selecionar Arquivo de Log" do menu é clicado
		else if (e.getSource() == menuOpenLogFile) {
			screenClear();
			OpenLogFiles open;
			open = new OpenLogFiles();
			open.setVisible(true);
			screen.add(open);
		}
		// Ação a ser executada quando o botão "Visualizar Todos os Perfis" é clicado
		else if (e.getSource() == buttonViewAllUsers) {
			screenClear();
			ViewAllUsers view = new ViewAllUsers();
			view.setVisible(true);
			screen.add(view);
		}
		// Ação a ser executada quando o botão "Visualizar Perfis Por Cargo" é clicado
		else if (e.getSource() == buttonViewUsersByRole) {
			screenClear();
			ViewRolesList view = new ViewRolesList();
			view.setVisible(true);
			screen.add(view);
		}
		// Ação a ser executada quando o botão "Pesquisar Perfil" é clicado
		else if (e.getSource() == buttonSearchUserById) {
			screenClear();
			FindProfile find = new FindProfile();
			find.setVisible(true);
			screen.add(find);
		}
		// Ação a ser executada quando o botão "Iniciar Processamento" é clicado
		else if (e.getSource() == buttonStartProcessing) {
			screenClear();
			StartProcessing start = new StartProcessing();
			start.setVisible(true);
			screen.add(start);
		}
		// Ação a ser executada quando o botão "Visualizar Perfis Suspeitos" é clicado
		else if (e.getSource() == buttonViewSuspects) {
			screenClear();
			ViewSuspects view = new ViewSuspects();
			view.setVisible(true);
			screen.add(view);
		}
		// Ação a ser executada quando o botão "Salvar Processamento" é clicado
		else if (e.getSource() == buttonSaveProcessing) {
			screenClear();
			SaveProcessing save = new SaveProcessing();
			save.setVisible(true);
			screen.add(save);
		}
	}
	
	// Limpa a tela
	private void screenClear() {
		screen.removeAll();
		revalidate();
		repaint();
	}
	
	public static void main(String[] args) {
			AppWindow app = new AppWindow();
			app.setVisible(true);
		}
}
