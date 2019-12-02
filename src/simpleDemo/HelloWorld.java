package simpleDemo;

import classes.Simplexe;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Contraints;
import javafx.scene.control.*;

public class HelloWorld extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	GridPane root = new GridPane();
    	root.setVgap(10);
    	root.setHgap(10);
    	root.setAlignment(Pos.TOP_CENTER);
    	Label nbrV = new Label("Nombre de variable");
    	Label nbrC = new Label("Nombre de contrainte");
    	TextField tnbrV = new TextField();
    	TextField tnbrC = new TextField();
    	Button next = new Button("next");
    	Button dis = new Button("display");
    	root.add(nbrV,0,1);
    	root.add(tnbrV,1,1);
    	root.add(nbrC,0,2);
    	root.add(tnbrC,1,2);
    	HBox hb = new HBox();
    	hb.setAlignment(Pos.BOTTOM_CENTER);
    	hb.getChildren().addAll(next,dis);
    	root.add(hb, 2, 2);
    	Contraints co = new Contraints();
    	
    	next.setOnAction(e->{    		
    		root.add(
    			co.generateConts(Integer.parseInt(tnbrV.getText()), Integer.parseInt(tnbrC.getText()))
 				,1, 3);
    	});
    	
    	dis.setOnAction(e->{
    		Simplexe sx = new Simplexe(co);
    		sx.ajouterVarEcarArti();
    		co.displayValc();
    	});
        Scene sc = new Scene(root, 500, 500);
        primaryStage.setTitle("Simplexe Methode");
        primaryStage.setScene(sc);
        primaryStage.show();
    }
    
    


    public static void main(String[] args) {
        launch(args);
    }	
}
