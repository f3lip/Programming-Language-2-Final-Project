package br.imd.ui;


import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import br.imd.model.User;

public class StartProcessing extends JInternalFrame {
	public static User[] suspects;
	
	public StartProcessing() {
		
		/*
		 * InsiderThreat
		 */
		
		// Define um array de users com os usu�rios suspeitos
		suspects = AppWindow.it.getSuspectUsersArray();
		
		// Exibe uma mensagem avisando que o processamento foi conclu�do
		JOptionPane.showMessageDialog(AppWindow.screen, "Processamento conclu�do");
	 
		// Ativa os bot�es "Visualizar Perfis Suspeitos" e "Salvar Processamento" j� que o processamento ja foi conclu�do
		AppWindow.buttonViewSuspects.setEnabled(true);
		AppWindow.buttonSaveProcessing.setEnabled(true);
	}

}
