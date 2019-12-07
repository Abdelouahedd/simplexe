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
	private ArrayList<Double>z = new ArrayList<Double>();
	private ArrayList<ArrayList<Double>> system = new ArrayList<ArrayList<Double>>();

	public Simplexe(Contraints cntrs) {
		this.cntrs = cntrs;
	}
	
	public Simplexe() {}

	// la creation d'une matrice qui contient des valeurs d'ecarts et variable
	// artificiels
	public ArrayList<ArrayList<Double>> implementMatrice() {
		ArrayList<ArrayList<Double>> matrice = new ArrayList<ArrayList<Double>>();

		for (int i = 0; i < this.getNbrLIgne(); i++) {
			ArrayList<Double> ligne = new ArrayList<Double>();
			if (this.getNbrLIgne() - 1 != i) {
				// Si la contrainte contient <=
				if (this.cntrs.getCot().get(i).getOp().equals("<=")) {
					for (int j = 0; j < this.getMaxColVar(); j++) {
						int posi = 0;
						for (int k = 0; k < i; k++) {
							if (this.cntrs.getCot().get(k).getOp().equals(">=")) {
								posi += 1;
							} else if (this.cntrs.getCot().get(k).getOp().equals("<=")) {
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
				// ---------------------------------------------------

				// si la contrainte contient >=
				if (this.cntrs.getCot().get(i).getOp().equals(">=")) {
					for (int j = 0; j < this.getMaxColVar(); j++) {
						int posi = 0;
						int posE = 0;
						for (int k = 0; k < i; k++) {
							if (this.cntrs.getCot().get(k).getOp().equals(">=")) {
								posi += 1;
								posE += 1;
							} else if (this.cntrs.getCot().get(k).getOp().equals("=")) {
								posi += 1;
							} else {
								posE += 1;
							}
						}
						if (j == posE) {
							ligne.add(new Double(-1));
						} else if ((posi + getNbrVarEc()) == j) {
							ligne.add(new Double(1));
						} else {
							ligne.add(new Double(0));
						}
					}
				}
				// ---------------------------------------------------

				// si la contrainte contient =
				if (this.cntrs.getCot().get(i).getOp().equals("=")) {
					for (int j = 0; j < this.getMaxColVar(); j++) {
						int posi = 0;
						for (int k = 0; k < i; k++) {
							if (this.cntrs.getCot().get(k).getOp().equals(">=")) {
								posi += 1;
							} else if (this.cntrs.getCot().get(k).getOp().equals("=")) {
								posi += 1;
							}
						}
						if (j == (this.getNbrVarEc() + posi)) {
							ligne.add(new Double(1));
						} else {
							ligne.add(new Double(0));
						}

					}
				}
			} else {
				for (int j = this.getNbrColonne() + 1; j <= getMaxCol(); j++)
					ligne.add(new Double(0));
			}
			matrice.add(ligne);
		}
		return matrice;
	}
	// -------------------------------Fin de la fonction implementMatrice
	// -------------------------------------//

	// -------------------------------debut de la fonction ajouterVarEcarArti
	// -------------------------------------//

	public ArrayList<ArrayList<Double>> ajouterVarEcarArti() {
		ArrayList<ArrayList<Double>> values = this.cntrs.getAllV();

		// L'ajout des variables ecart et artificiel au system
		for (int i = 0; i < getNbrLIgne(); i++) {
			for (int j = 0; j < getMaxColVar(); j++) {
				values.get(i).add(implementMatrice().get(i).get(j));
			}
		}
		this.system = values;

		// L'ajout du vecteur B au system
		for (int i = 0; i < getNbrLIgne(); i++) {
			this.system.get(i).add(this.cntrs.b.get(i));
		}

		return this.system;
	}
	// -------------------------------fin de la fonction ajouterVarEcarArti
	// -------------------------------------//

	// le nombre du ligne du system
	private int getNbrLIgne() {
		return this.cntrs.getCot().size();
	}

	// le nombre du colonne du system
	private int getNbrColonne() {
		return this.cntrs.getCot().get(1).getNbrV();
	}

	// le nombre du colonne apres l'ajout des variables d'ecarts ou artificiels
	public int getMaxCol() {
		return this.getNbrColonne() + this.getNbrVarEc() + this.getNbrVarAr();
	}

	// le nombre du colonne apres l'ajout des variables d'ecarts ou artificiels
	private int getMaxColVar() {
		return this.getNbrVarAr() + this.getNbrVarEc();
	}

	// le nombre de variable d'ecart
	public int getNbrVarEc() {
		int nbrCol = 0;
		for (int i = 0; i < this.cntrs.getCot().size() - 1; i++) {
			if (this.cntrs.getCot().get(i).getOp().equals("<=") || this.cntrs.getCot().get(i).getOp().equals(">=")) {
				nbrCol += 1;
			}
		}
		return nbrCol;
	}

	// le nombre des variables artifieciels dans le system
	public int getNbrVarAr() {
		int nbrCol = 0;
		for (int i = 0; i < this.cntrs.getCot().size() - 1; i++) {
			if (this.cntrs.getCot().get(i).getOp().equals(">=") || this.cntrs.getCot().get(i).getOp().equals("=")) {
				nbrCol += 1;
			}
		}
		return nbrCol;
	}

	// getter for all contraints
	public Contraints getCntrs() {
		return cntrs;
	}

	// getter for get simplexe table
	public ArrayList<ArrayList<Double>> getSystem() {
		return system;
	}

	// setter sipmlexe table
	public void setSystem(ArrayList<ArrayList<Double>> system) {
		this.system = system;
	}

	// display all values of simplexe table
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
		for (ArrayList<Double> row : this.system) {
			data.add(FXCollections.observableArrayList(row));
		}
		return data;
	}

	public TableView<ObservableList<Double>> createTableView() {
		ArrayList<String> colName = generateNameColumn();
		TableView<ObservableList<Double>> tableView = new TableView<>();
		tableView.setItems(buildData(this.system));
		
		for (int i = 0; i < this.system.get(0).size(); i++) {
			final int curCol = i;
			final TableColumn<ObservableList<Double>, String> column = new TableColumn<>(colName.get(i));

			column.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<ObservableList<Double>, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<ObservableList<Double>, String> p) {
							return new SimpleStringProperty((p.getValue().get(curCol).toString()));
						}
					});
			tableView.getColumns().add(column);
		}
		
		tableView.setPrefHeight(getNbrLIgne() * 35);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
	}
	//-********************************
	private ArrayList<String> generateNameColumn() {
		ArrayList<String> varEc = new ArrayList<String>();
		ArrayList<String> varAr = new ArrayList<String>();
		ArrayList<String> varDec = new ArrayList<String>();
		ArrayList<String> column = new ArrayList<String>();
		for (int i = 0; i < this.cntrs.getCot().get(0).getNbrV(); i++) {
			int c = i + 1;
			varDec.add("x" + c);
		}
		for (int i = 0; i < getNbrLIgne() - 1; i++) {
			int c = i + 1;
			if (this.cntrs.getCot().get(i).getOp().equals("<=")) {
				varEc.add("e" + c);
			} else if (this.cntrs.getCot().get(i).getOp().equals(">=")) {
				varEc.add("e" + c);
				varAr.add("a" + c);
			} else if (this.cntrs.getCot().get(i).getOp().equals("=")) {
				varAr.add("a" + c);
			}
		}
		column.addAll(varDec);
		column.addAll(varEc);
		column.addAll(varAr);
		column.add("b");
		return column;
	}
	public <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
	    for (TableColumn<T, ?> col : tableView.getColumns())
	        if (col.getText().equals(name)) return col ;
	    return null ;
	}
	

	
	/*               *
	 *               * 
	 *               *
	 *    Phase 1    * 
	 *               *
	 *               *
	 *               *
	 *               */
	
	//la variable entrante dans le system
		public int findColonePivot() {
			int size = this.system.size()-1;
			Double max = new Double(0);
			int index = 0;
			for(int i = 0;i<this.system.get(size).size();i++) {
			  	if(Math.abs(this.system.get(size).get(i)) > max && this.system.get(size).get(i) < 0 ) {
			  		max = this.system.get(size).get(i);
			  		index  = i;
			  	}
			} 
			return index;
		}
		//la variable sortante de la base
		public int findLignePivot() {
			int colonpivot = findColonePivot();
			int nbrColone = this.system.get(0).size();
			int index = 0;
			Double min = Double.MAX_VALUE;
			ArrayList<Double>racio = new ArrayList<Double>();
	         for(int i=0;i<this.system.size()-1;i++) {
					racio.add(this.system.get(i).get(nbrColone-1)/this.system.get(i).get(colonpivot));
	         }
	         for(int j = 0;j<this.system.size()-1;j++) {
	        	 if(racio.get(j)<min && racio.get(j)>0) {
	        		 min = racio.get(j);
	        		 index = j;
	        	 }
	         }
			return index;
		}
		//pivot
		public Double getPivot() {
			return this.system.get(findLignePivot()).get(findColonePivot());
		}
		//correction de la fonction objectif
		public void correctionZ() {
			int eviter = getNbrVarEc()+this.getCntrs().getCot().get(0).getNbrV();
			//nouvel fonction objectif 
			ArrayList<Double>cj = new ArrayList<Double>();
			//les index des fonctions qui contients des variables artificiel
			ArrayList<Integer> indexs= new ArrayList<Integer>();
			//Tout les variables Hors base sont a 0 sauf les variables artifiecl
			for(int i=0;i<this.system.get(0).size();i++) {
				if(i==eviter)
					cj.add(new Double(1));
				else
					cj.add(new Double(0));
			}
			//Trouver les contraints qui contients les variables artificiel
			for(int i = 0;i<this.getCntrs().getCot().size()-1;i++) {
				if(this.getCntrs().getCot().get(i).getOp().equals(">=") || this.getCntrs().getCot().get(i).getOp().equals(">=")) {
					indexs.add(i);
				}
			}
			//Correction de CJ
			for(int i=0;i<indexs.size();i++) {
				for (int j = 0; j < this.system.get(indexs.get(i)).size()-1; j++) {
					cj.set(j, cj.get(j)-this.system.get(indexs.get(i)).get(j));
				}
			}
			for (int j = 0; j < cj.size(); j++) {
				System.out.println("--> "+cj.get(j));
			}
			//supprimer la fonction Z et remplacer le avec Cj
			this.system.remove(this.system.size()-1);
			this.system.add(cj);
		}
		
		//pahase 1
				public ArrayList<ArrayList<Double>>phase1(){
					
					return this.system;
				}

}
