package classes;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import models.Contraints;

public class Simplexe {
	private Contraints cntrs;
	private ArrayList<ArrayList<Double>> system = new ArrayList<ArrayList<Double>>();

	public Simplexe(Contraints cntrs) {
		this.cntrs = cntrs;
	}
    
	//la creation d'une matrice qui contient des valeurs d'ecarts et variable artificiels
	public ArrayList<ArrayList<Double>> implementMatrice() {
		ArrayList<ArrayList<Double>> matrice = new ArrayList<ArrayList<Double>>();

		for (int i = 0; i < this.getNbrLIgne(); i++) {
			ArrayList<Double> ligne = new ArrayList<Double>();
			if (this.getNbrLIgne() - 1 != i) {
				//Si la contrainte contient <=
				if (this.cntrs.getCot().get(i).getOp().equals("<=")) {
					for (int j = 0; j < this.getMaxColVar(); j++) {
						int posi = 0;
						for(int k=0;k<i;k++) {
							if(this.cntrs.getCot().get(k).getOp().equals(">=")) {
								posi += 1;
							}else if(this.cntrs.getCot().get(k).getOp().equals("<=")) {
								posi += 1;
							}
						}
						if (j == posi) {
							ligne.add(new Double(1));
						} else {
							ligne.add(new Double(0));
						}
					}
				}
				//---------------------------------------------------
				
				//si la contrainte contient >=
				if (this.cntrs.getCot().get(i).getOp().equals(">=")) {
					for (int j = 0; j < this.getMaxColVar(); j++) {
					int posi = 0;
					int posE = 0;
						for(int k=0;k<i;k++) {
							if(this.cntrs.getCot().get(k).getOp().equals(">=")) {
								posi += 1;
								posE +=1;
							}else if(this.cntrs.getCot().get(k).getOp().equals("=")) {
								posi += 1;
							}else {
								posE +=1;
							}
						}
						if (j == posE) {
							ligne.add(new Double(-1));
						} else if ((posi+getNbrVarEc()) == j) {
							ligne.add(new Double(1));
						} else {
							ligne.add(new Double(0));
						}
					}
				}
				//---------------------------------------------------
				
				//si la contrainte contient =
				if (this.cntrs.getCot().get(i).getOp().equals("=")) {
					for (int j = 0; j < this.getMaxColVar(); j++) {
						int posi = 0;
						for(int k=0;k<i;k++) {
							if(this.cntrs.getCot().get(k).getOp().equals(">=")) {
								posi += 1;
							}else if(this.cntrs.getCot().get(k).getOp().equals("=")) {
								posi += 1;
							}
						}
						if (j == (this.getNbrVarEc()+posi)) {
							ligne.add(new Double(1));
						} else {
							ligne.add(new Double(0));
						}

					}
				}
			} 
				else {
				 for (int j = this.getNbrColonne()+1; j <=getMaxCol(); j++)
				 ligne.add(new Double(0));
			}
			matrice.add(ligne);
		}
		return matrice;
	}
	//-------------------------------Fin de la fonction implementMatrice -------------------------------------//

	//-------------------------------debut de la fonction ajouterVarEcarArti -------------------------------------//

	public ArrayList<ArrayList<Double>> ajouterVarEcarArti() {
		ArrayList<ArrayList<Double>> values = this.cntrs.getAllV();
        
		//L'ajout des variables ecart et artificiel au system
        for(int i=0;i<getNbrLIgne();i++) {
			for (int j = 0; j <getMaxColVar() ; j++) {
				values.get(i).add(implementMatrice().get(i).get(j));
			}
		}
		this.system = values;
		
		//L'ajout du vecteur B au system
		for (int i = 0; i < getNbrLIgne(); i++) {
			this.system.get(i).add(this.cntrs.b.get(i));
		}

		return this.system;
	}
	//-------------------------------fin de la fonction ajouterVarEcarArti -------------------------------------//


	// le nombre du ligne du system
	private int getNbrLIgne() {
		return this.cntrs.getCot().size();
	}

	// le nombre du colonne du system
	private int getNbrColonne() {
		return this.cntrs.getCot().get(1).getNbrV();
	}

	// le nombre du colonne apres l'ajout des variables d'ecarts ou artificiels
	private int getMaxCol() {
		return this.getNbrColonne()+this.getNbrVarEc()+this.getNbrVarAr();
	}

	// le nombre du colonne apres l'ajout des variables d'ecarts ou artificiels
	private int getMaxColVar() {
		return this.getNbrVarAr() + this.getNbrVarEc();
	}

	// le nombre de variable d'ecart
	private int getNbrVarEc() {
		int nbrCol = 0;
		for (int i = 0; i < this.cntrs.getCot().size() - 1; i++) {
			if (this.cntrs.getCot().get(i).getOp().equals("<=") || this.cntrs.getCot().get(i).getOp().equals(">=")) {
				nbrCol += 1;
			}
		}
		return nbrCol;
	}

	// le nombre des variables artifieciels dans le system
	private int getNbrVarAr() {
		int nbrCol = 0;
		for (int i = 0; i < this.cntrs.getCot().size() - 1; i++) {
			if (this.cntrs.getCot().get(i).getOp().equals(">=") || this.cntrs.getCot().get(i).getOp().equals("=")) {
				nbrCol += 1;
			}
		}
		return nbrCol;
	}
    //getter for all contraints
	public Contraints getCntrs() {
		return cntrs;
	}
    //getter for get simplexe table
	public ArrayList<ArrayList<Double>> getSystem() {
		return system;
	}
    // setter sipmlexe table
	public void setSystem(ArrayList<ArrayList<Double>> system) {
		this.system = system;
	}
    //display all values of simplexe table
	public void displaySystem() {
		for (int i = 0; i < this.system.size(); i++) {
			for (int j = 0; j < this.system.get(i).size(); j++) {
				System.out.print(String.format("%.0f", this.system.get(i).get(j)));
			}
			System.out.println();
		}
	}
	
	
	  private ObservableList<ObservableList<Double>> buildData(ArrayList<ArrayList<Double>> dataArray) {
	        ObservableList<ObservableList<Double>> data = FXCollections.observableArrayList();
	        for (ArrayList<Double>row : this.system) {
	            data.add(FXCollections.observableArrayList(row));
	        }
	        return data;
	    }
	 
	    public TableView<ObservableList<Double>> createTableView() {
	        TableView<ObservableList<Double>> tableView = new TableView<>();
	        tableView.setItems(buildData(this.system));
	        
	        for (int i = 0; i < this.system.get(0).size(); i++) {
	        	final int curCol = i;
	            final TableColumn<ObservableList<Double>, String> column = new TableColumn<>("Col " + (curCol + 1));
	          
	            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<Double>,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<ObservableList<Double>, String> p) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty((p.getValue().get(curCol).toString()));
					}
				});
	            tableView.getColumns().add(column);
	        }
	        tableView.setPrefHeight(getNbrLIgne()*35);
	        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        return tableView;
	    }
	    private String[] generateNameColumn() {
	    	  	ArrayList<String> varEc = new ArrayList<String>();
	    	  	ArrayList<String>  varAr = new ArrayList<String> ();
	    	  	ArrayList<String> varDec = new ArrayList<String>();
	    	  	for (int i = 1; i <= this.cntrs.getCot().get(0).getNbrV(); i++) {
					varDec.add("x"+i);
				}
	    	  	for (int i = 1; i <= getNbrLIgne(); i++) {
					if(this.cntrs.getCot().get(i).getOp().equals("<=")) {
						varEc.add("e"+i);
					}else if(this.cntrs.getCot().get(i).getOp().equals(">=")) {
						varEc.add("e"+i);
						varAr.add("a"+i);
					}else if(this.cntrs.getCot().get(i).getOp().equals("=")) {
						varEc.add("a"+i);
					} 
				}
	    	  	
	    	  	
	    	return null;
	    }

}
