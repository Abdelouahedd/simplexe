package classes;

import java.util.ArrayList;

import exceptions.Degenerescence;
import exceptions.NonBornerException;
import exceptions.PivotException;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

public class Solver extends Simplexe{
	private ArrayList<ArrayList<Double>> matrice;

	public Solver(ArrayList<ArrayList<Double>> matrice ) {
     this.matrice =matrice;
    } 
	


	
	public 	VBox solve() {
		VBox hb = new VBox();
		super.ajouterVarEcarArti();
		  super.createTableView();
		  hb.getChildren().add( super.createTableView());
		  super.correctionZ();
		  hb.getChildren().add( super.createTableView());
		do {
		  try {
			super.iterationPhase1();
			  hb.getChildren().add( super.createTableView());
		} catch (PivotException | NonBornerException | Degenerescence e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		}while(true);
	}

}
