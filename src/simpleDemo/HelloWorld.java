package simpleDemo;

import classes.Simplexe;
import classes.Solver;
import exceptions.Degenerescence;
import exceptions.NonBornerException;
import exceptions.PivotException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Contraints;
import javafx.scene.control.*;

public class HelloWorld extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ScrollPane sp = new ScrollPane();
		Scene sc = new Scene(sp, 500, 500);
		GridPane root = new GridPane();
		root.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 5;" +
				"-fx-border-radius: 5;" +
				"-fx-border-color: blue;");
		sp.setContent(root);
		root.setVgap(10);
		root.setHgap(10);
		root.setAlignment(Pos.TOP_CENTER);
		Label nbrV = new Label("Nombre de variable");
		Label nbrC = new Label("Nombre de contrainte");
		TextField tnbrV = new TextField();
		TextField tnbrC = new TextField();
		Label msg = new Label();
		Button next = new Button("next");
		Button dis = new Button("display");
		Button go = new Button("next");
		Button phase2 = new Button("Phase 2");
        Popup pop = new Popup();
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
				next.setVisible(false);
		});
		
		Simplexe sx = new Simplexe(co);
		dis.setOnAction(e -> {
			root.add(sx.realiserTable1(), 1, 4);
			root.add(go, 2, 4);
		});
		
		HBox phase = new HBox();
		go.setOnAction(e->{
				try {
					do {
						sx.iterationPhase1();
						Thread.sleep(20);
					}while(true);	
				} catch (PivotException | NonBornerException | Degenerescence | InterruptedException e1) {
					e1.getMessage();
					System.out.println(e1);
				}
				phase.getChildren().addAll(sx.createTableView(),phase2);
				if(sx.coutZ()==(0)) {
					System.out.println("phase 2 -->");
				}else {
					System.out.println("Le probleme n'admit pas de solutions");
				}
				go.setVisible(false);
				root.add(phase, 1, 6);
		});
		phase2.setOnAction(e->{
			sx.removeVarAr();
			root.add(sx.createTablePhase2(), 1,7);
		});

		primaryStage.setTitle("Simplexe Methode");
		primaryStage.setScene(sc);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
