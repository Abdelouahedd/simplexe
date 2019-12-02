package classes;

import java.util.ArrayList;

import models.Contraints;

public class Simplexe {
	private Contraints cntrs;
	private ArrayList<ArrayList<Double>>system =  new ArrayList<ArrayList<Double>>();
	
	public Simplexe(Contraints cntrs){
		this.cntrs = cntrs;
	}
	
	public ArrayList<ArrayList<Double>>ajouterVarEcarArti(){
		ArrayList<ArrayList<Double>>values = this.cntrs.getAllV();
		System.out.println("le nombre du col "+this.getMaxCol());
//		for(int i= 0;i<this.getNbrLIgne();i++) {
//			for (int j = this.getNbrColonne(); j <getMaxCol(); j++) {
//
//			}
//
//		}
		return this.system;
	}
	//le nombre du ligne du system
	private int getNbrLIgne() {
		return this.cntrs.getCot().size();
	}
	//le nombre du colonne du system
	private int getNbrColonne() {
		return this.cntrs.getCot().get(1).getNbrV()+1;
	}	
	//le nombre du colonne apres l'ajout des variables d'ecarts ou artificiels
	private int getMaxCol() {
		int nbrCol = this.getNbrColonne();
		for (int i = 0; i < this.cntrs.getCot().size()-1; i++) {
			System.out.println("operation equation 1"+this.cntrs.getCot().get(i).getOp());
			if(this.cntrs.getCot().get(i).getOp().equals("<=")) {
				nbrCol++;
			}else if(this.cntrs.getCot().get(i).getOp().equals(">=")) {
				nbrCol+=2;
			}else {
				nbrCol++;
			}
		}
		return nbrCol;
	}
	
	
	public Contraints getCntrs() {
		return cntrs;
	}

	public void setCntrs(Contraints cntrs) {
		this.cntrs = cntrs;
	}

	public ArrayList<ArrayList<Double>> getSystem() {
		return system;
	}

	public void setSystem(ArrayList<ArrayList<Double>> system) {
		this.system = system;
	}

	
	

}
