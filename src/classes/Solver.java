package classes;

import java.util.ArrayList;

public class Solver extends Simplexe{
	private ArrayList<ArrayList<Double>> matrice;
	private int [] indexs;

	public Solver(ArrayList<ArrayList<Double>> matrice ) {
     this.matrice =matrice;
    } 
	

	public ArrayList<ArrayList<Double>> getMatrice() {
		return matrice;
	}

	public void setMatrice(ArrayList<ArrayList<Double>> matrice) {
		this.matrice = matrice;
	}

//	//la variable entrante dans le system
//	public int findColonePivot() {
//		int size = this.matrice.size()-1;
//		Double max = new Double(0);
//		int index = 0;
//		for(int i = 0;i<this.matrice.get(size).size();i++) {
//		  	if(this.matrice.get(size).get(i) > max) {
//		  		max = this.matrice.get(size).get(i);
//		  		index  = i;
//		  	}
//		}  
//		return index;
//	}
//	//la variable sortante de la base
//	public int findLignePivot() {
//		int colonpivot = findColonePivot();
//		int nbrColone = this.matrice.get(0).size();
//		int index = 0;
//		Double min = Double.MAX_VALUE;
//		ArrayList<Double>racio = new ArrayList<Double>();
//         for(int i=0;i<this.matrice.size()-1;i++) {
//				racio.add(this.matrice.get(i).get(nbrColone-1)/this.matrice.get(i).get(colonpivot));
//         }
//         for(int j = 0;j<this.matrice.size()-1;j++) {
//        	 if(racio.get(j)<min) {
//        		 min = racio.get(j);
//        		 index = j;
//        	 }
//         }
//         correctionZ();
//		return index;
//	}
//	//pivot
//	public Double getPivot() {
//		return this.matrice.get(findLignePivot()).get(findColonePivot());
//	}
//	//pahase 1
//	public ArrayList<ArrayList<Double>>phase1(){
//		return this.matrice;
//	}
//	//correction de la fonction objectif
//	public void correctionZ() {
//		int eviter = super.getNbrVarEc()+super.getCntrs().getCot().get(0).getNbrV();
//		ArrayList<Double>cj = new ArrayList<Double>();
//		ArrayList<Integer> indexs= new ArrayList<Integer>();
//		for(int i=0;i<this.matrice.get(0).size();i++) {
//			if(i==eviter)
//				cj.add(new Double(0));
//			else
//				cj.add(new Double(1));
//		}
//		for(int i = 0;i<super.getCntrs().getCot().size();i++) {
//			if(super.getCntrs().getCot().get(i).getOp().equals(">=") || super.getCntrs().getCot().get(i).getOp().equals(">=")) {
//				indexs.add(i);
//			}
//		}
//		
//		for(int i=0;i<indexs.size();i++) {
//			for (int j = 0; j < this.matrice.get(indexs.get(i)).size()-2; j++) {
//				System.out.println("--> "+this.matrice.get(indexs.get(i)).get(j));
//			}
//		}
//		
//	}

}
