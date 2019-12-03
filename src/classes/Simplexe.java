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
		
//		ArrayList<Double>ligne = new ArrayList<Double>();
//				
//		for(int i= 0;i<this.getNbrLIgne();i++) {
//			if(this.getNbrLIgne()-1!=i) {
//				if(this.cntrs.getCot().get(i).getOp().equals("<=")) {
//					for (int j = this.getNbrColonne()+1; j <=getMaxCol(); j++) {
//	                  if(j == this.getNbrColonne()+1+i) {
//	                      ligne.add(new Double(1));
//	                  }else {
//	                      ligne.add(new Double(0));
//	                  }
//	                }
//				}
//			}else{
//				
//				for (int j = this.getNbrColonne()+1; j <=getMaxCol(); j++) {
//					ligne.add(new Double(0));
//					}
//			}
//			
//			values.add(ligne);
//		}
//		this.system= values;
		System.out.println("affichage des coef du system");
		this.cntrs.displayValc();
		System.out.println("------------------------");
		for(int i =0;i<values.size();i++) {
			for(int j = 0;j<values.get(i).size();j++) {
				System.out.print(String.format("%.0f",values.get(i).get(j)));
			}
			System.out.println();
		}
		System.out.println("------------------------");
		for(int i =0;i<this.cntrs.b.size();i++) {
				System.out.println(String.format("%.0f",this.cntrs.b.get(i)));
			
		}
		
		return this.system;
	}
	
	
	
	//le nombre du ligne du system
	private int getNbrLIgne() {
		return this.cntrs.getCot().size();
	}
	
	//le nombre du colonne du system
	private int getNbrColonne() {
		return this.cntrs.getCot().get(1).getNbrV();
	}	
	
	//le nombre du colonne apres l'ajout des variables d'ecarts ou artificiels
	private int getMaxCol() {
		int nbrCol = this.getNbrColonne();
		for (int i = 0; i < this.cntrs.getCot().size()-1; i++) {
			if(this.cntrs.getCot().get(i).getOp().equals("<=")) {
				nbrCol+=1;
			}else if(this.cntrs.getCot().get(i).getOp().equals(">=")) {
				nbrCol+=2;
			}else {
				nbrCol+=1;
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
	
	public void displaySystem() {
		for(int i =0;i<this.system.size();i++) {
			for(int j = 0;j<this.system.get(i).size();j++) {
				System.out.print(String.format("%.0f",this.system.get(i).get(j)));
			}
			System.out.println();
		}
	}

	
}
