package classes;

import java.util.*;
import java.util.Map.Entry;
import exceptions.Degenerescence;
import exceptions.NonBornerException;
import exceptions.PivotException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Contraints;

public class Simplexe {

	private Contraints cntrs;
	private ArrayList<ArrayList<Double>> system = new ArrayList<ArrayList<Double>>();
	// Map contient les variables de base
	private Map<Integer, String> varBase = new HashMap<Integer, String>();

	// Constructeur pour la création d'un object Simplexe
	public Simplexe(Contraints cntrs) {
		this.cntrs = cntrs;
	}

	// initailiser Z
	public ArrayList<Double> fonctionObjectif() {
		ArrayList<Double> z = new ArrayList<Double>();
		z.addAll(this.cntrs.z);
		for (int i = z.size(); i < this.system.get(0).size(); i++) {
			z.add((double) 0);
		}
		return z;
	}

	// Constructeur sans paramétre
	public Simplexe() {
	}

	// la creation d'une matrice qui contient des valeurs d'ecarts et variable
	// artificiels
	public ArrayList<ArrayList<Double>> implementMatrice() {
		ArrayList<ArrayList<Double>> matrice = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < this.getNbrLIgne(); i++) {
			ArrayList<Double> ligne = new ArrayList<Double>();
			int nbr = i + 1;
			if (this.getNbrLIgne() - 1 != i) {
				// Si la contrainte contient <=
				if (this.cntrs.getCot().get(i).getOp().equals("<=")) {
					/***********/
					this.varBase.put(i, "e" + nbr);
					/*************/
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
					/**********/
					this.varBase.put(i, "a" + nbr);
					/**********/
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
					/********************/
					this.varBase.put(i, "a" + nbr);
					/***************/
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
	// L'ajout des variables ecart et artificiel en meme temps a notre matrice
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

	// affichage du simplexe table
	public void displaySystem() {
		for (int i = 0; i < this.system.size(); i++) {
			for (int j = 0; j < this.system.get(i).size(); j++) {
				System.out.print(String.format("| %.2f |", this.system.get(i).get(j)));
			}
			System.out.println();
		}
	}

	// creation des lignes
	private ObservableList<ObservableList<Double>> buildData(ArrayList<ArrayList<Double>> dataArray) {
		ObservableList<ObservableList<Double>> data = FXCollections.observableArrayList();
		for (ArrayList<Double> row : this.system) {
			data.add(FXCollections.observableArrayList(row));
		}
		return data;
	}

	// creation du tableau avec les noms des colonnes
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
							return new SimpleStringProperty(p.getValue().get(curCol).toString());
						}
					});
			tableView.getColumns().add(column);
		}
		tableView.setPrefHeight(getNbrLIgne() * 35);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
	}

	// Fonction pour la géneration des noms des colonnes du tableau
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

	// Le nom du variable a l'aide de leur indice dans le tableau
	public String getNameVarEntrant(int i) {
		return createTableView().getColumns().get(i).getText();
	}

	/*
	 * * * * Phase 1 * * * *
	 */

	// la variable entrante dans le system
	public int findColonePivot() throws PivotException {
		int size = this.system.size() - 1;
		Double max = Double.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < this.system.get(size).size() - 1; i++) {
			if (this.system.get(size).get(i) < max && this.system.get(size).get(i) < 0) {
				max = this.system.get(size).get(i);
				index = i;
			}
		}
		if (max >= 0)
			throw new PivotException();
		return index;
	}

	// la variable sortante de la base
	public int findLignePivot() throws PivotException, NonBornerException, Degenerescence {
		int colonpivot = findColonePivot();
		int nbrColone = this.system.get(0).size();
		int index = 0;
		boolean testCas = false;
		Double min = Double.MAX_VALUE;
		ArrayList<Double> racio = new ArrayList<Double>();
		for (int i = 0; i < this.system.size() - 1; i++) {
			racio.add(this.system.get(i).get(nbrColone - 1) / this.system.get(i).get(colonpivot));
			System.out.println("racio " + i + " => " + racio.get(i));
		}
		for (int j = 0; j < this.system.size() - 1; j++) {
			if (racio.get(j) < min && racio.get(j) > 0) {
				min = racio.get(j);
				index = j;
			}
		}
		for (int j = 0; j < racio.size() - 1; j++) {
			if(!Double.isFinite(racio.get(j)) || racio.get(j)<0) {
				testCas = true;
			}else {
				testCas = false;
				break;
			}
		}
		System.out.println("min racio  = " + min);

		if (testCas == true)
			throw new NonBornerException();
		if (min == Double.MAX_VALUE)
			throw new Degenerescence();

		return index;
	}

	// pivot
	public Double getPivot() throws PivotException, NonBornerException, Degenerescence {
		return this.system.get(findLignePivot()).get(findColonePivot());
	}

	// correction de la fonction objectif
	public void correctionZ() {
		int eviter = getNbrVarEc() + this.getCntrs().getCot().get(0).getNbrV();
		// nouvel fonction objectif
		ArrayList<Double> cj = new ArrayList<Double>();
		// les index des fonctions qui contients des variables artificiel
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		// Tout les variables Hors base sont a 0 sauf les variables artifiecl
		for (int i = 0; i < this.system.get(0).size(); i++) {
			if (i >= eviter)
				cj.add(new Double(1));
			else
				cj.add(new Double(0));
		}
		// colonne du B = 0
		cj.set(cj.size() - 1, coutZ());

		// Trouver les contraints qui contients les variables artificiel
		for (int i = 0; i < this.getCntrs().getCot().size() - 1; i++) {
			if (this.getCntrs().getCot().get(i).getOp().equals(">=")
					|| this.getCntrs().getCot().get(i).getOp().equals("=")) {
				indexs.add(i);
			}
		}
		// Correction de CJ
		for (int i = 0; i < indexs.size(); i++) {
			for (int j = 0; j < this.system.get(indexs.get(i)).size() - 1; j++) {
				cj.set(j, cj.get(j) - this.system.get(indexs.get(i)).get(j));
			}
		}

		// supprimer la fonction Z et remplacer le avec Cj
		this.system.remove(this.system.size() - 1);
		this.system.add(cj);
	}

	// Fonction pour faire une itération
	public ArrayList<ArrayList<Double>> iterationPhase1() throws PivotException, NonBornerException, Degenerescence {
		coutZ();
		int indexi = findLignePivot();
		int indexj = findColonePivot();
		showValues(indexi, indexj);
		Double pivot = getPivot();
        System.out.println("pivot est "+pivot);
		ArrayList<Double> lignePivot = new ArrayList<Double>();
		ArrayList<Double> colomnPivot = new ArrayList<Double>();

		// division tout les nombres dans la ligne du pivot par le pivot
		for (int i = 0; i < this.system.get(indexi).size(); i++) {
			this.system.get(indexi).set(i, (double) (this.system.get(indexi).get(i) / pivot));
		}

		// get nouveaux ligne pivot et le colonne de pivot pour la multipilier au
		// nouveau ligne
		for (int i = 0; i < this.system.size(); i++) {
			colomnPivot.add(this.system.get(i).get(indexj));
		}
		lignePivot.addAll(this.system.get(indexi));

		// les nouveaux lignes
		for (int j = 0; j < this.system.size(); j++) {
			for (int k = 0; k < this.system.get(j).size(); k++) {
				if (j != indexi) {
					this.system.get(j).set(k, (this.system.get(j).get(k) - (lignePivot.get(k) * colomnPivot.get(j))));
				}
			}
		}

		return this.system;
	}

	// returner les variables qui sont du base avec le numéro du ligne comme key
	public Map<Integer, String> showValues(int i, int j) throws PivotException, NonBornerException, Degenerescence {
		String NomVarEntrant = getNameVarEntrant(j);
		System.out.println("la variable entre la base est " + NomVarEntrant);
		Iterator<Entry<Integer, String>> iterator = this.varBase.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, String> entry = iterator.next();
			if (entry.getKey() == (i)) {
				this.varBase.replace(i, NomVarEntrant);
			}
		}
		return this.varBase;
	}

	// Le cout du Cj dans la phase 1
	public Double coutZ() {
		Double cout = (double) 0;
		Iterator<Entry<Integer, String>> iterator = this.varBase.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, String> entry = iterator.next();
			if (entry.getValue().startsWith("a")) {
				System.out.println("la valeur du " + entry.getValue() + " est "
						+ this.system.get(entry.getKey()).get(this.system.get(0).size() - 1));
				cout += this.system.get(entry.getKey()).get(this.system.get(0).size() - 1);
			}
		}
		this.system.get(this.system.size() - 1).set(getMaxCol(), cout);
		System.out.println("le cout = " + cout);
		return cout;
	}

	// Fonction qui retourn le tableau initiale du phase 1
	public VBox realiserTable1() {
		VBox hb = new VBox();
		this.ajouterVarEcarArti();
		this.createTableView();
		hb.getChildren().add(this.createTableView());
		this.correctionZ();
		hb.getChildren().add(this.createTableView());
		hb.setPadding(new Insets(15, 12, 15, 12));
		return hb;
	}

	/*
	 * * * * Phase 2 * * * *
	 */

	// suprimer les variables artificiel dans le system
	public ArrayList<ArrayList<Double>> removeVarAr() {
		int debut = getNbrColonne() + getNbrVarEc();
		for (int i = 0; i < this.system.size(); i++) {
			for (int j = (this.system.get(i).size() - 2); j >= debut; j--) {
				this.system.get(i).remove(j);
			}
		}
		System.out.println("---------------- Phase 2 --------");
		displaySystem();
		return this.system;
	}

	// la correction du fonction objectif Z
	public ArrayList<ArrayList<Double>> corectionZ2() {
		int eviter = this.getCntrs().getCot().get(0).getNbrV();
		// nouvel fonction objectif
		ArrayList<Double> cj = new ArrayList<Double>();
		// les index des fonctions qui contients des variables de décisions entrantes
		// dans la phase 1
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		// les coefficients des variables de decisions qui on entrer la base apres la
		// phase 1
		ArrayList<Integer> indexC = new ArrayList<Integer>();
		// Tout les variables Hors base sont 0 sauf les variables de decisions
		for (int i = 0; i < this.system.get(0).size(); i++) {
			if (i < eviter) {
				cj.add(-1 * fonctionObjectif().get(i));
			} else {
				cj.add(new Double(0));
			}
		}
		// Trouver les contraints qui contients les variables entrante (x)
		Iterator<Entry<Integer, String>> iterator = this.varBase.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, String> entry = iterator.next();
			if (entry.getValue().startsWith("x")) {
				int i = generateNameColumn().indexOf(entry.getValue());
				indexs.add(entry.getKey());
				indexC.add(i);
			}
		}
		// Correction de CJ
		for (int i = 0; i < indexs.size(); i++) {
			Double coef = cj.get(indexC.get(i));
			for (int j = 0; j < this.system.get(indexs.get(i)).size() - 1; j++) {
				cj.set(j, cj.get(j) - (coef * this.system.get(indexs.get(i)).get(j)));
			}
		}
		// colonne du B = couZ
		cj.set(cj.size() - 1, coutZ2());
		// supprimer la fonction Z et remplacer le avec Cj
		this.system.remove(this.system.size() - 1);
		this.system.add(cj);
		System.out.println("new Z " + cj);
		return this.system;
	}

	// generer les noms des colonnes du tableau du phase 2
	private ArrayList<String> generateNameColumnPhase2() {
		ArrayList<String> varEc = new ArrayList<String>();
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
			}
		}
		column.addAll(varDec);
		column.addAll(varEc);
		column.add("b");
		return column;
	}

	// Fonction pour la création d'un tableau du phase 2
	public TableView<ObservableList<Double>> createTablePhase2() {
		ArrayList<String> colName = generateNameColumnPhase2();
		TableView<ObservableList<Double>> tableView = new TableView<>();
		tableView.setItems(buildData(this.system));
		for (int i = 0; i < this.system.get(0).size(); i++) {
			final int curCol = i;
			final TableColumn<ObservableList<Double>, String> column = new TableColumn<>(colName.get(i));
			column.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<ObservableList<Double>, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<ObservableList<Double>, String> p) {
							return new SimpleStringProperty(p.getValue().get(curCol).toString());
						}
					});
			tableView.getColumns().add(column);
		}
		tableView.setPrefHeight(getNbrLIgne() * 35);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
	}

	// cout de la fonction objectif dans la phase 2
	public Double coutZ2() {
		Double cout = (double) 0;
		Iterator<Entry<Integer, String>> iterator = this.varBase.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, String> entry = iterator.next();
			if (entry.getValue().startsWith("x")) {
				int i = generateNameColumn().indexOf(entry.getValue());
				cout += fonctionObjectif().get(i) * this.system.get(entry.getKey()).get(this.system.get(0).size() - 1);
			}
		}
		this.system.get(this.system.size() - 1).set((this.getNbrColonne() + this.getNbrVarEc()), cout);
		System.out.println("le cout = " + cout);
		return cout;
	}

	// faire iteration dans la phase 2
	public ArrayList<ArrayList<Double>> iterationPhase2() throws PivotException, NonBornerException, Degenerescence {
		coutZ2();
		int indexi = findLignePivot();
		int indexj = findColonePivot();
		showValues(indexi, indexj);
		Double pivot = getPivot();
		ArrayList<Double> lignePivot = new ArrayList<Double>();
		ArrayList<Double> colomnPivot = new ArrayList<Double>();

		// division tout les nombres dans la ligne du pivot par le pivot
		for (int i = 0; i < this.system.get(indexi).size(); i++) {
			this.system.get(indexi).set(i, (double) (this.system.get(indexi).get(i) / pivot));
		}

		// get nouveaux ligne pivot et le colonne de pivot pour la multipilier au
		// nouveau ligne
		for (int i = 0; i < this.system.size(); i++) {
			colomnPivot.add(this.system.get(i).get(indexj));
		}
		lignePivot.addAll(this.system.get(indexi));

		// les nouveaux lignes
		for (int j = 0; j < this.system.size(); j++) {
			for (int k = 0; k < this.system.get(j).size(); k++) {
				if (j != indexi) {
					this.system.get(j).set(k, (this.system.get(j).get(k) - (lignePivot.get(k) * colomnPivot.get(j))));
				}
			}
		}
		displaySystem();
		
		return this.system;
	}

}
