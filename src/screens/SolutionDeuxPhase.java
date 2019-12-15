package screens;

import classes.Simplexe;
import exceptions.Degenerescence;
import exceptions.NonBornerException;
import exceptions.PivotException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Iterator;
import java.util.Map;

public class SolutionDeuxPhase extends Stage {
    VBox phase = new VBox();
    Button phase2 = new Button("Phase 2");
    Label sol = new Label();
    public SolutionDeuxPhase(Simplexe sx){
        try {
            do {
                sx.iterationPhase1();
                Thread.sleep(20);
            } while (true);
        } catch (PivotException | NonBornerException | Degenerescence | InterruptedException e1) {
            e1.getMessage();
            System.out.println(e1);
        }
        phase2.setStyle("-fx-background-color: Gray ; -fx-text-fill: white;");
        phase.getChildren().addAll(sx.createTableView(), phase2);
        phase.setAlignment(Pos.TOP_CENTER);
        phase.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        if (sx.coutZ() == (0)) {
            System.out.println("phase 2 -->");
        } else {
            System.out.println("Le probleme n'admit pas de solutions");
        }
        phase2.setOnAction(e -> {
            sx.removeVarAr();
            sx.corectionZ2();
            try {
                do {
                    sx.iterationPhase2();
                    Thread.sleep(20);
                } while (true);
            } catch (PivotException | NonBornerException | Degenerescence | InterruptedException e1) {
                e1.getMessage();
                System.out.println(e1);
                phase.getChildren().add(sx.createTablePhase2());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Final des iterations !");
                alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e1.toString())));
                alert.showAndWait();
            }
            Iterator<Map.Entry<Integer, String>> iterator = sx.getVarBase().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, String> entry = iterator.next();
                if (entry.getValue().startsWith("x")) {
                    System.out.println("la valeur du " + entry.getValue() + " est "
                            + sx.getSystem().get(entry.getKey()).get( sx.getSystem().get(0).size() - 1));
                }
            }
        });
        this.setScene(new Scene(phase,500,500));
        this.show();
    }
}
