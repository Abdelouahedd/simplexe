package screens;

import classes.Contraints;
import classes.Simplexe;
import classes.SimplexePrimal;
import classes.Solveur;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Pls extends Stage {
    //private Simplexe simplexe;
    ScrollPane sp = new ScrollPane();
    GridPane root = new GridPane();
    VBox content = new VBox();
    Label nbrV = new Label("Nombre de variable");
    Label nbrC = new Label("Nombre de contrainte");
    TextField tnbrV = new TextField();
    TextField tnbrC = new TextField();
    Button next = new Button("next");
    Button dis = new Button("display");
    HBox gb = new HBox();
    Button go = new Button("Iteration");
    HBox hb = new HBox();

    //Size of screen
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    public Pls(Simplexe sm){
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: red;");
        //GridPane pour les inputes
        root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

        root.setAlignment(Pos.TOP_CENTER);
        root.add(nbrV,0,1);
        root.add(tnbrV, 1, 1);
        root.add(nbrC, 0, 2);
        root.add(tnbrC, 1, 2);
        //Hbox pour next et Display
        hb.setAlignment(Pos.BOTTOM_CENTER);
        next.setStyle("-fx-background-color: Gray ; -fx-text-fill: white;");
        dis.setStyle("-fx-background-color: Gray ; -fx-text-fill: white;");
        hb.getChildren().addAll(next);
        hb.setSpacing(10);
        root.add(next, 2, 2);
        content.getChildren().add(root);

        Contraints co = new Contraints();
        next.setOnAction(e -> {
            content.getChildren().addAll(co.generateConts(Integer.parseInt(tnbrV.getText()), Integer.parseInt(tnbrC.getText())),dis);
            next.setVisible(false);
        });
        sm.setCntrs(co);
        SimplexePrimal smp = new SimplexePrimal(co,sm.getType());
        gb.getChildren().add(go);
        gb.setAlignment(Pos.CENTER);
        go.setStyle("-fx-background-color: Gray ; -fx-text-fill: white;");
        Solveur solveur = new Solveur(co);
        dis.setOnAction(e -> {
            dis.setVisible(false);
            if( solveur.decision()){
                content.getChildren().add(sm.realiserTable1());
                content.getChildren().add(gb);
           }else{
                smp.ajouterVarEcarArti();
                smp.displaySystem();
                content.getChildren().add(smp.createTablePhase2());
                content.getChildren().add(gb);
           }
        });
        go.setOnAction(e->{
            if( solveur.decision()){
                new SolutionDeuxPhase(sm);
            }else{
                new SolutionUnePhase(smp);
            }
            go.setVisible(false);
            this.close();
        });
        sp.setContent(content);
        this.setScene(new Scene(sp, 500, bounds.getHeight() / 2));
        this.show();
    }
}



