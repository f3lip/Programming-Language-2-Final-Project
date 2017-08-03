package br.imd.ui;


import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveProcessing extends JInternalFrame {
	
	/*
	 * Abre uma janela para escolha do local onde o arquivo será salvo e o nome que será dado
	 */
	public SaveProcessing() {
		JFileChooser save = new JFileChooser();
		save.setCurrentDirectory(new File(System.getProperty("user.home")));
		save.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*csv)", "csv");
		save.setFileFilter(filter);
		if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			File selectedFile = save.getSelectedFile();
			String filename = selectedFile.getAbsolutePath();
			AppWindow.it.saveSuspectsFile(filename, StartProcessing.suspects);
			JOptionPane.showMessageDialog(AppWindow.screen, "O arquivo de usuários suspeitos foi salvo");
		}
	}
}
