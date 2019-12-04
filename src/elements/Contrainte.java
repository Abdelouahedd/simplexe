package elements;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Contrainte {

	private int nbrV;// nombre du variable
	private ArrayList<TextField> y;// les textFields chaque un contient coefficient
	private String op;// operation utiliser dans la contrainte

	public Contrainte(int i) {
		this.nbrV = i;
		this.y = new ArrayList<TextField>();
	}

	public ArrayList<TextField> getY() {
		return this.y;
	}

	public String getOp() {
		return this.op;
	}

	public int getNbrV() {
		return this.nbrV;
	}

	// ajouter contrainte dans la stage a l'aide du Class COntaints
	public HBox generateCont(int n) {
		String operateurs[] = { "<=", ">=", "=" };
		HBox hb = new HBox();
		hb.setAlignment(Pos.BOTTOM_CENTER);
		ComboBox<String> ope = new ComboBox<String>(FXCollections.observableArrayList(operateurs));

		for (int i = 1; i <= n; i++) {
			TextField x = new TextField();
			TextField b = new TextField();
			Label y = new Label();
			ope.setPrefWidth(6);
			if (i < n) {
				x.setPromptText("x" + i);
				x.setPrefWidth(50);
				this.y.add(x);
				y.setText("X" + i + "+");
				hb.getChildren().addAll(x, y);
			} else if (i == n) {
				b.setPromptText("b");
				b.setPrefWidth(50);
				x.setPromptText("x" + i);
				x.setPrefWidth(50);
				this.y.add(x);
				this.y.add(b);
				y.setText("X" + i);
				hb.getChildren().addAll(x, y, ope, b);
			}

		}
		// fonction pour ajouter un evenment de changement d'une operation
		ope.getSelectionModel().selectedItemProperty()
				.addListener(((ChangeListener<? super String>) (ov, old_val, new_val) -> {
					this.op = new_val;
				}));

		return hb;
	}

	// funtion to create HBox of Z for add by Contraints(class)
	public HBox addZ(int n) {
		HBox hb = new HBox();
		Label zz = new Label("Z = ");
		hb.getChildren().add(zz);
		for (int i = 1; i <= n; i++) {
			TextField z = new TextField();
			Label a = new Label();
			if (i < n) {
				z.setPromptText("x" + i);
				z.setPrefWidth(50);
				this.y.add(z);
				a.setText("X" + i + "+");
				hb.getChildren().addAll(z, a);
			} else if (i == n) {
				z.setPromptText("x" + i);
				z.setPrefWidth(50);
				this.y.add(z);
				a.setText("X" + i);
				hb.getChildren().addAll(z, a);
			}
		}
		return hb;
	}

}
