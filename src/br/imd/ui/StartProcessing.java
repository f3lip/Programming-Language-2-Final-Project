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
		
		// Define um array de users com os usuários suspeitos
		suspects = AppWindow.it.getSuspectUsersArray();
		
		// Exibe uma mensagem avisando que o processamento foi concluído
		JOptionPane.showMessageDialog(AppWindow.screen, "Processamento concluído");
	 
		// Ativa os botões "Visualizar Perfis Suspeitos" e "Salvar Processamento" já que o processamento ja foi concluído
		AppWindow.buttonViewSuspects.setEnabled(true);
		AppWindow.buttonSaveProcessing.setEnabled(true);
	}

}
