package screens;

import classes.SimplexePrimal;
import exceptions.Degenerescence;
import exceptions.NonBornerException;
import exceptions.PivotException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Map;

public class SolutionUnePhase extends Stage {
    VBox phase = new VBox();
    Button iteration = new Button("iteration");

    public SolutionUnePhase(SimplexePrimal smp){
        phase.setAlignment(Pos.TOP_CENTER);
        smp.corectionZ2();
        try {
            do {
                smp.iterationPhase2();
                Thread.sleep(20);
                phase.getChildren().add(smp.createTablePhase2());
            }while (true);
        }catch (NonBornerException e) {
            System.out.println(e.toString());
        } catch (PivotException e) {
            System.out.println(e.toString());
        } catch (Degenerescence degenerescence) {
            System.out.println(degenerescence.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
           /* Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Final des iterations !");
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(e.toString())));
            alert.showAndWait();*/
        }
        Iterator<Map.Entry<Integer, String>> iterator = smp.getVarBase().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (entry.getValue().startsWith("x")) {
                System.out.println("la valeur du " + entry.getValue() + " est "
                        + smp.getSystem().get(entry.getKey()).get( smp.getSystem().get(0).size() - 1));
            }
        }
        this.setScene(new Scene(phase,500,500));
        this.show();
    }
}
