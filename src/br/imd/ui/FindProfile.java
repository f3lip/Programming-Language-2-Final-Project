package br.imd.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.imd.model.User;

public class FindProfile extends JInternalFrame implements ActionListener {
	
	// Cria uma label
	private JLabel textLabel = new JLabel("Digite o ID do usuário: ");
	
	// Cria uma caixa de texto
	private JTextField textBox = new JTextField();
	
	// Cria um botão
	private JButton findButton = new JButton("Pesquisar");
	
	public FindProfile() {	
		/*
		 * Interface
		 */
		
		// Cria paineis
		JPanel leftSide = new JPanel();
		JPanel middle = new JPanel();
		JPanel rightSide = new JPanel();
		
		// Altera o tamanho da caixa de texto
		this.textBox.setPreferredSize(new Dimension(448,28));
		
		//Adiciona conteudo aos paineis
		leftSide.add(this.textLabel);
		middle.add(this.textBox);
		rightSide.add(this.findButton);
		
		// Adiciona conteudo ao frame
		this.add(leftSide, BorderLayout.WEST);
		this.add(middle);
		this.add(rightSide, BorderLayout.EAST);
		
		// Configura o frame
		this.setSize(695,348);
		this.setTitle("Pesquisar Perfil");
		this.findButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		
		// Ação a ser executada quando o botão "Pesquisar" for clicado
		User user = AppWindow.it.users.searchById(this.textBox.getText());
		UserWindow window = new UserWindow(user);
		window.setVisible(true);
	}
	
	
			
}
