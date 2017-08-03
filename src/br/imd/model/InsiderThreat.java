package br.imd.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class InsiderThreat {
	
	public Tree users;
	
	/*
	 * Construtor
	 */
	public InsiderThreat() {
		this.users = new Tree();
	}
	
	/*
	 * Lê o arquivo de usuários e os adiciona na árvore
	 * Segue o padrão: [ nome_usuario, id_usuario, dominio, email, cargo ]
	 */
	public void readUsersFile(String csvFile) {
		
		BufferedReader br = null;
		try {
			
			 br = new BufferedReader(new FileReader(csvFile));
			String line = "";
			
			// Traduz cada linha do arquivo para um objeto User e insere na Tree
			while ((line = br.readLine()) != null) {
				String[] userData = line.split(",");
				User user = new User(userData[0], userData[1], userData[2], userData[3], userData[4]); 
				this.users.insertUser(user);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//Segue o padrão: [ nome_usuario, id_usuario, dominio, email, cargo ]
	public void saveSuspectsFile(String filename, User[] suspects) {
		if(!filename.endsWith(".csv")){
			filename = filename + ".csv";
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			for(int i = 0; i < suspects.length; i++){
				String userData = suspects[i].getName() + "," + suspects[i].getId() + "," + suspects[i].getDomain() + "," 
								+ suspects[i].getEmail() + "," + suspects[i].getRole() + "\n";
				bw.write(userData);
			}
		} catch (IOException e) {

			e.printStackTrace();

		}
	
	}
	
   /*
	* Lê o arquivo log e joga as informações no usuário correspondente
	* Segue o padrão: [ id_acesso, data, DTAA/id_usuario, pc, atividade ]
	*/
	public void readLogFile(String csvFile) {
		
		BufferedReader br = null;
		try {
			
			/* Lê o arquibo linha por linha e armazena em line */
			 br = new BufferedReader(new FileReader(csvFile));
			String line = "";
			while ((line = br.readLine()) != null) {
				
				/* Separa a linha pela vírgula em um array */
				String[] logData = line.split(",");
				
				/* Separa a string "DTAA/" da id usuários  */
				String userId = logData[2].split("/")[0];
				if (logData[2].split("/").length > 1) {
					userId = logData[2].split("/")[1];
				}
				
				/* Só adiciona as informações ao usuário se tiver sido encontrado na árvore */
				User currentUser = this.users.searchById(userId);
				if (currentUser != null) {
					currentUser.addHistogram(logData);
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * Retorna um array de cargos
	 */
	public String[] getRolesArray(){
		ArrayList<String> roles = new ArrayList<>();
		User[] users = this.users.getUsersArray();
		for(int i = 0; i < users.length; i++){
			 if(!roles.contains(users[i].getRole())){
				 if(!users[i].getRole().equals("Role")) {
					 roles.add(users[i].getRole());
				 }
			 }
		}
		String[] array = new String[roles.size()];
		array = roles.toArray(array);
		return array;
	}
	
	
	/*
	 * Retorna um array de usuários suspeitos
	 */
	public User[] getSuspectUsersArray() {
		ArrayList<User> suspects = new ArrayList<User>();
		String[] roles = this.getRolesArray();
		for(int i = 0;i < roles.length; i++) {
			Tree usersByRole = this.users.getUsersByRole(roles[i]);
			User[] users = usersByRole.getUsersArray();
			int devicesAverage = this.getDevicesPattern(roles[i]);
			double devicesStdDev = this.getDevicesStdDev(users);
			int webPagesAverage = this.getDevicesPattern(roles[i]);
			double webPagesStdDev = this.getDevicesStdDev(users);
			for(User user : users) {
				if(user.getWebPages().length > webPagesAverage + webPagesStdDev){
					suspects.add(user);
					continue;
				}
				if(user.getDevices().length > devicesAverage + devicesStdDev){
					suspects.add(user);
				}
			}
		}
		
		User[] array = new User[suspects.size()];
		array = suspects.toArray(array);
		return array;
	}

	/*
	 * Calcula o desvio padrão
	 * Referência: https://stackoverflow.com/questions/14839056/standard-deviation-of-any-sized-array-java
	 */
	private double getDevicesStdDev(User[] users) {
		int size = users.length;
		if(size == 0){
	        return 0.0;
	    }
	    double sum = 0;
	    double sq_sum = 0;
	    for(int i = 0; i < size; ++i) {
	    	int devices = users[i].getDevices().length;
	    	sum += devices;
	    	sq_sum += devices * devices;
	    }
	    double average = sum / size;
	    double variance = sq_sum / (size - (average * average));
	    return Math.sqrt(variance);
	}
	
	/*
	 * Retorna a média de dispositivos usados por usuário em um cargo específico
	 */
	private int getDevicesPattern(String role) {
		Tree tree = this.users.searchByRole(role);
		int numberOfDevices = 0;
		int numberOfUsers = 0;
		while( tree != null ) {
			if (tree.getLeftTree() == null) {
				if ( role.equals( tree.getUserRole()) ){
					numberOfUsers++;
					numberOfDevices = numberOfDevices + (tree.getRoot().getUser().getDevices().length);
				tree = tree.getRightTree();
				}
			} else {
				/* Find the inorder predecessor of current */
				Tree pre = tree.getLeftTree();
				while(pre.getRightTree() != null && pre.getRightTree() != tree) {
					pre = pre.getRightTree();
				}
				/* Make current as right child of its inorder predecessor */
				if ( pre.getRightTree() == null ) {
					pre.setRightTree(tree);
					tree = tree.getLeftTree();
				}
				
				/* Revert the changes made in if part to restore the original 
				tree i.e., fix the right child of predecessor */   
				else {
					pre.setRightTree(null);
					if ( role.equals( tree.getUserRole()) ){
						numberOfUsers++;
						numberOfDevices = numberOfDevices + (tree.getRoot().getUser().getDevices().length);
					tree = tree.getRightTree();      
					}
				}
			}
		}
		
		return numberOfDevices/numberOfUsers;
	}
	
	/*
	 * Calcula o desvio padrão
	 * Referência: https://stackoverflow.com/questions/14839056/standard-deviation-of-any-sized-array-java
	 */
	public double getWebPagesStdDev(User[] users) {
		int size = users.length;
		if(size == 0){
	        return 0.0;
	    }
	    double sum = 0;
	    double sq_sum = 0;
	    for(int i = 0; i < size; ++i) {
	    	int webPages = users[i].getWebPages().length;
	    	sum += webPages;
	    	sq_sum += webPages * webPages;
	    }
	    double average = sum / size;
	    double variance = sq_sum / (size - (average * average));
	    return Math.sqrt(variance);
	}
	
	/*
	 * Retorna a média de dispositivos usados por usuário em um cargo específico
	 */
	public int getWebPagesPattern(String role) {
		Tree tree = this.users.searchByRole(role);
		int numberOfPages = 0;
		int numberOfUsers = 0;
		while( tree != null ) {
			if (tree.getLeftTree() == null) {
				if ( role.equals( tree.getUserRole()) ){
					numberOfUsers++;
					numberOfPages = numberOfPages + (tree.getRoot().getUser().getWebPages().length);
				tree = tree.getRightTree();
				}
			} else {
				/* Find the inorder predecessor of current */
				Tree pre = tree.getLeftTree();
				while(pre.getRightTree() != null && pre.getRightTree() != tree) {
					pre = pre.getRightTree();
				}
				/* Make current as right child of its inorder predecessor */
				if ( pre.getRightTree() == null ) {
					pre.setRightTree(tree);
					tree = tree.getLeftTree();
				}
				
				/* Revert the changes made in if part to restore the original 
				tree i.e., fix the right child of predecessor */   
				else {
					pre.setRightTree(null);
					if ( role.equals( tree.getUserRole()) ){
						numberOfUsers++;
						numberOfPages = numberOfPages + (tree.getRoot().getUser().getWebPages().length);
					tree = tree.getRightTree();      
					}
				}
			}
		}
		
		return numberOfPages/numberOfUsers;
	}
	
}