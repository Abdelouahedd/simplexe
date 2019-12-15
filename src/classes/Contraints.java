package classes;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class Contraints {
	//attribut d'un contraint
	private ArrayList<Contrainte>cont;
	//les coefficient du system
	private ArrayList<ArrayList<Double>>coeffcient;
	public ArrayList<Double>b;
	public ArrayList<Double>z;
	public Contraints(){
		this.cont = new ArrayList<Contrainte>();
		this.coeffcient = new ArrayList<ArrayList<Double>>();
		this.b = new ArrayList<Double>();
		this.z = new ArrayList<Double>();
	}

	public ArrayList<Contrainte>getCot(){
		return this.cont;
	}

	public ArrayList<ArrayList<Double>> getCoeffcient() {
		return coeffcient;
	}

	public void setCoeffcient(ArrayList<ArrayList<Double>> coeffcient) {
		this.coeffcient = coeffcient;
	}
    
	//les contraintes dans le stage avec la fonction objectif Z
	public VBox generateConts(int nbrV,int nbrC) {
		VBox vb = new VBox();
		vb.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: orange;");
		vb.setAlignment(Pos.CENTER);
		for (int i = 0; i < nbrC; i++) {
			try {
				Thread.sleep(10);
				Contrainte co = new Contrainte(nbrV);
				this.cont.add(co);
				vb.getChildren().add(co.generateCont(nbrV));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		vb.setSpacing(20);
		Contrainte z = new Contrainte(nbrV);
		this.cont.add(z);
		vb.getChildren().add(z.addZ(nbrV));
		return vb;
	}
	
	//get All values from the TextField and generate a Table
	public ArrayList<ArrayList<Double>> getAllV(){
		int i,j;
		ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
		for(i=0;i<this.cont.size();i++) {
			ArrayList<Double>ligne = new ArrayList<Double>();
			for( j=0;j<this.cont.get(i).getY().size();j++) {
				if(j == this.cont.get(i).getY().size()-1 && i==this.cont.size()-1 ) {
					b.add(new Double(0));
				}
				if(j == this.cont.get(i).getY().size()-1 &&  i != this.cont.size()-1) {
					b.add(Double.parseDouble(this.cont.get(i).getY().get(j).getText()));
					
				}
				else if(j < this.cont.get(i).getY().size() &&  i < this.cont.size())  {
					ligne.add(Double.parseDouble(this.cont.get(i).getY().get(j).getText()));
				}
			}
			values.add(i, ligne);
			if(i == this.cont.size()-1) {
				z.addAll(ligne);
			}
		}
		this.coeffcient.addAll(values);
		return this.coeffcient;
	}
	

	public void displayValc() {
		 for (int i = 0; i < this.coeffcient.size(); i++) {
			 for (int j = 0; j < this.coeffcient.get(i).size(); j++) {
				System.out.printf(String.format("%.0f", this.coeffcient.get(i).get(j)));
			}
			 System.out.println();
		}
	}
	
	
}
