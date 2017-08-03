package br.imd.model;

import java.util.ArrayList;

public class Tree {
	
	private No root;
	private Tree leftTree;
	private Tree rightTree;
	
	/*
	 * Construtor
	 */
	public Tree(){
		this.root = null;
		this.leftTree = null;
		this.rightTree = null;
	}
	
	/*
	 * Insere um usuário no nó root
	 */
	public void insertUser( User user ) {
		No no = new No(user);
		this.insert(no);
	}
	private void insert(No no) {
		if ( this.root == null ) {
			this.setRoot(no);
		} else {
			if ( no.getUser().getId().compareTo( this.root.getUser().getId() ) > 0 ) {
				if (this.rightTree == null){
					this.setRightTree(new Tree());
				}
				this.rightTree.insert(no);
			} else if ( no.getUser().getId().compareTo(this.root.getUser().getId()) < 0 ) {
				if (this.leftTree == null) {
					this.setLeftTree(new Tree());
				}
				this.leftTree.insert(no);
			}
		}
	}
	
	/*
	 * Busca o usuário pela ID e o retorna
	 */
	public User searchById(String id) {
		Tree tree = this;
		while( tree != null ) {
			if (tree.leftTree == null) {
				if ( id.equals( tree.getUserId()) )
					return tree.getUser();
				tree = tree.rightTree;
			} else {
				/* Find the inorder predecessor of current */
				Tree pre = tree.leftTree;
				while(pre.rightTree != null && pre.rightTree != tree) {
					pre = pre.rightTree;
				}
				/* Make current as right child of its inorder predecessor */
				if ( pre.rightTree == null ) {
					pre.rightTree = tree;
					tree = tree.leftTree;
				}
				
				/* Revert the changes made in if part to restore the original 
				tree i.e., fix the right child of predecessor */   
				else {
					pre.rightTree = null;
					if ( id.equals( tree.getUserId()) )
						return tree.getUser();
					tree = tree.rightTree;      
				}
			}
		}
		return null;
	}
	
	/*
	 * Retorna uma Tree com os User que possuem a role passada (inorder)
	 * Referência: http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion-and-without-stack/
	 */
	public Tree searchByRole(String role) {
		Tree users = new Tree();
		Tree tree = this;
		while( tree != null ) {
			if (tree.leftTree == null) {
				if ( role.equals( tree.getUserRole()) )
					users.insertUser( tree.getUser() );
				tree = tree.rightTree;
			} else {
				/* Find the inorder predecessor of current */
				Tree pre = tree.leftTree;
				while(pre.rightTree != null && pre.rightTree != tree) {
					pre = pre.rightTree;
				}
				/* Make current as right child of its inorder predecessor */
				if ( pre.rightTree == null ) {
					pre.rightTree = tree;
					tree = tree.leftTree;
				}
				
				/* Revert the changes made in if part to restore the original 
				tree i.e., fix the right child of predecessor */   
				else {
					pre.rightTree = null;
					if ( role.equals( tree.getUserRole()) )
						users.insertUser( tree.getUser() );
					tree = tree.rightTree;      
				}
			}
		}
		return users;
	}
	
	
	/*
	 * Retorna um array com todos os usuários
	 * Referência: http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion-and-without-stack/
	 */
	public User[] getUsersArray() {
		Tree tree = this;
		ArrayList<User> users = new ArrayList<User>();
		while( tree != null ) {
			if (tree.leftTree == null) {
				users.add(tree.getUser());
				tree = tree.rightTree;
			} else {
				/* Find the inorder predecessor of current */
				Tree pre = tree.leftTree;
				while(pre.rightTree != null && pre.rightTree != tree) {
					pre = pre.rightTree;
				}
				/* Make current as right child of its inorder predecessor */
				if ( pre.rightTree == null ) {
					pre.rightTree = tree;
					tree = tree.leftTree;
				}
				/* Revert the changes made in if part to restore the original 
				tree i.e., fix the right child of predecessor */   
				else {
					pre.rightTree = null;
					users.add(tree.getUser());
					tree = tree.rightTree;      
				}
			}
		}
		
		User[] array = new User[users.size()];
		array = users.toArray(array);
		return array;
	}
	
	public Tree getUsersByRole(String role) {
		Tree users = new Tree();
		Tree tree = this;
		while( tree != null ) {
			if (tree.leftTree == null) {
				if ( role.equals( tree.getUserRole()) ){
					users.insertUser( tree.getUser());
				}
					
				tree = tree.rightTree;
			} else {
				/* Find the inorder predecessor of current */
				Tree pre = tree.leftTree;
				while(pre.rightTree != null && pre.rightTree != tree) {
					pre = pre.rightTree;
				}
				/* Make current as right child of its inorder predecessor */
				if ( pre.rightTree == null ) {
					pre.rightTree = tree;
					tree = tree.leftTree;
				}
				
				/* Revert the changes made in if part to restore the original 
				tree i.e., fix the right child of predecessor */   
				else {
					pre.rightTree = null;
					if ( role.equals( tree.getUserRole()) ){
						users.insertUser( tree.getUser() );
					}
					tree = tree.rightTree;      
				}
			}
		}
		return users;
	}
	
	/*
	 * Retorna o número de usuários de um cargo especifico
	 */
	public int getRoleSize(String role) {
		Tree tree = this;
		int cont = 0;
		while( tree != null ) {
			if (tree.leftTree == null) {
				if ( role.equals( tree.getUserRole()) )
					cont++;
				tree = tree.rightTree;
			} else {
				/* Find the inorder predecessor of current */
				Tree pre = tree.leftTree;
				while(pre.rightTree != null && pre.rightTree != tree) {
					pre = pre.rightTree;
				}
				/* Make current as right child of its inorder predecessor */
				if ( pre.rightTree == null ) {
					pre.rightTree = tree;
					tree = tree.leftTree;
				}
				
				/* Revert the changes made in if part to restore the original 
				tree i.e., fix the right child of predecessor */   
				else {
					pre.rightTree = null;
					if ( role.equals( tree.getUserRole()) )
						cont++;
					tree = tree.rightTree;      
				}
			}
		}
		return cont;
	}
	

	/*
	 * Getters e Setters
	 */
	private User getUser() {return this.root.getUser();}
	private String getUserId() {return this.root.getUser().getId();}
	public String getUserName() {return this.root.getUser().getName();}
	public String getUserDomain() {return this.root.getUser().getDomain();}
	public String getUserEmail() {return this.root.getUser().getEmail();}
	public String getUserRole() {return this.root.getUser().getRole();}
	public Tree getRightTree() {return this.rightTree;}
	public Tree getLeftTree() {return this.leftTree;}
	public No getRoot() {return this.root;}
	
	public void setRightTree(Tree rightTree) {this.rightTree = rightTree;}
	public void setLeftTree(Tree leftTree) {this.leftTree = leftTree;}
	public void setRoot(No root) {this.root = root;}
}