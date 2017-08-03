package br.imd.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class OpenLogFiles extends JInternalFrame implements ActionListener {
	
	// Cria uma label
	private JLabel textLabel =  new JLabel("Arquivo de Log: ", SwingConstants.LEFT);
	
	// Cria uma caixa de texto
	private JTextField textBox  = new JTextField();
	
	// Cria um botão
	private JButton chooseFileButton = new JButton("Abrir Arquivo");
	
	public OpenLogFiles() {
		
		// Cria container e altera o layout dele
		Container selection = this.getContentPane();
		selection.setLayout(new BorderLayout());
		
		// Cria paineis
		JPanel rightSide = new JPanel();
		JPanel middle = new JPanel();
		JPanel leftSide = new JPanel();
		
		// Altera o tamanho da caixa de texto
		textBox.setPreferredSize(new Dimension(465,28));
		
		// Adiciona conteudo aos paineis
		leftSide.add(this.textLabel);
		middle.add(this.textBox);
		rightSide.add(chooseFileButton);
	
		// Adicionando os paineis e campo de texto ao container
		selection.add(BorderLayout.WEST, leftSide);
		selection.add(middle);
		selection.add(BorderLayout.EAST, rightSide);
		
		// Configurando a janela
		setSize(695,348);
		setTitle("Selecionar Arquivo de Log");
		
		// Action Listener para quando o botão for clicado
		chooseFileButton.addActionListener(this);
		
		/* Impede que a janela seja arrastada. 
		 * Referência: http://www.artima.com/forums/flat.jsp?forum=1&thread=3708
		 */
		for(MouseListener listener : ((BasicInternalFrameUI) this.getUI()).getNorthPane().getMouseListeners()){
			((BasicInternalFrameUI) this.getUI()).getNorthPane().removeMouseListener(listener);
		}
		
		// Impossibilita que o usuário digite na caixa de texto
		textBox.setEditable(false);
		textBox.setBackground(Color.WHITE);
		
	}
	
	/* Ação a ser executada ao clicar no botão. Uma janela para
	 * seleção de arquivos é aberta
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser open = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*csv)", "csv");
		open.setFileFilter(filter);
		open.setCurrentDirectory(new File(System.getProperty("user.home")));
		if (open.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = open.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			this.textBox.setText(path);
			// acessa a InsiderThreat
			AppWindow.it.readLogFile(path);
			dispose();
			JOptionPane.showMessageDialog(AppWindow.screen, "O arquivo de Log foi carregado");
			AppWindow.buttonStartProcessing.setEnabled(true);
		}
	}
}
