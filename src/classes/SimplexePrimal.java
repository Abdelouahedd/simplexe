package classes;

import exceptions.Degenerescence;
import exceptions.NonBornerException;
import exceptions.PivotException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class SimplexePrimal extends Simplexe{

	public SimplexePrimal(Contraints cntrs,String ty) {
		super(cntrs,ty);
	}



	@Override
	public ArrayList<ArrayList<Double>> implementMatrice() {
		ArrayList<ArrayList<Double>> matrice = new ArrayList<ArrayList<Double>>();
        for(int i =0;i<super.getNbrLIgne();i++) {
        	int nbr = i+1;
			ArrayList<Double> ligne = new ArrayList<Double>();
			if (this.getNbrLIgne() - 1 != i) {
				/***********/
				getVarBase().put(i, "e" + nbr);
				/*************/
				for(int j =0;j<getMaxColVar();j++) {
					if (this.getNbrLIgne() - 1 != i) {
						if(i == j) {
							ligne.add((double)1);
						}else {
							ligne.add((double)0);
						}
					}
				}
			} else {
				for (int j = this.getNbrColonne() + 1; j <= getMaxCol(); j++)
					ligne.add((double)0);
			}
        	matrice.add(ligne);
        }
		return matrice;
	}
	
	@Override
	public ArrayList<ArrayList<Double>> ajouterVarEcarArti() {
		ArrayList<ArrayList<Double>> values = super.getCntrs().getAllV();
		// L'ajout des variables ecart et artificiel au system
		for (int i = 0; i < getNbrLIgne(); i++) {
			for (int j = 0; j < getMaxColVar(); j++) {
				values.get(i).add(this.implementMatrice().get(i).get(j));
			}
		}
		super.setSystem(values);
		// L'ajout du vecteur B au system
		for (int i = 0; i < getNbrLIgne(); i++) {
			super.getSystem().get(i).add(super.getCntrs().b.get(i));
		}
		return getSystem();
	}

	@Override
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
		for (int i = 0; i < getSystem().get(0).size(); i++) {
			if (i < eviter) {
				//Si le probleme est du max on lui transferer au MIN
				if(this.getType().equals("MAX")){
					cj.add(-1 * fonctionObjectif().get(i));
				}else{
					cj.add(fonctionObjectif().get(i));
				}

			} else {
				cj.add(new Double(0));
			}
		}
		// Trouver les contraints qui contients les variables entrante (x)
		Iterator<Map.Entry<Integer, String>> iterator = getVarBase().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, String> entry = iterator.next();
			if (entry.getValue().startsWith("x")) {
				int i = generateNameColumn().indexOf(entry.getValue());
				indexs.add(entry.getKey());
				indexC.add(i);
			}
		}

		// colonne du B = couZ
		cj.set(cj.size() - 1, coutZ2());
		// supprimer la fonction Z et remplacer le avec Cj
		getSystem().remove(this.getSystem().size() - 1);
		this.getSystem().add(cj);
		System.out.println("new Z " + cj);
		return this.getSystem();
	}
}

