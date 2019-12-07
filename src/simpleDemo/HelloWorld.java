package simpleDemo;

import classes.Simplexe;
import classes.Solver;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Contraints;
import javafx.scene.control.*;

public class HelloWorld extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ScrollPane sp = new ScrollPane();
		GridPane root = new GridPane();
		sp.setContent(root);
		root.setVgap(10);
		root.setHgap(10);
		root.setAlignment(Pos.TOP_CENTER);
		Label nbrV = new Label("Nombre de variable");
		Label nbrC = new Label("Nombre de contrainte");
		TextField tnbrV = new TextField();
		TextField tnbrC = new TextField();
		Button next = new Button("next");
		Button dis = new Button("display");
		Button go = new Button("go");
		
		root.add(nbrV, 0, 1);
		root.add(tnbrV, 1, 1);
		root.add(nbrC, 0, 2);
		root.add(tnbrC, 1, 2);
		HBox hb = new HBox();
		hb.setAlignment(Pos.BOTTOM_CENTER);
		hb.getChildren().addAll(next, dis);
		root.add(hb, 2, 2);
		Contraints co = new Contraints();
		next.setOnAction(e -> {
				root.add(co.generateConts(Integer.parseInt(tnbrV.getText()), 
						 Integer.parseInt(tnbrC.getText())), 
						1,3);
		});
		
		Simplexe sx = new Simplexe(co);
		
		dis.setOnAction(e -> {
			sx.ajouterVarEcarArti();
			sx.createTableView().prefWidthProperty().bind(primaryStage.widthProperty());
			root.add(sx.createTableView(),1,4);
			root.add(go, 2, 4);
		});
		
		go.setOnAction(e->{
//			Solver sl = new Solver(sx.getSystem());
//			sl.findLignePivot();
			sx.correctionZ();
			sx.findLignePivot();
			sx.createTableView().prefWidthProperty().bind(primaryStage.widthProperty());
			root.add(sx.createTableView(),1,5);
			System.out.println("Pivot est : "+sx.getPivot());
		});
		

		Scene sc = new Scene(sp, 500, 500);
		primaryStage.setTitle("Simplexe Methode");
		primaryStage.setScene(sc);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
