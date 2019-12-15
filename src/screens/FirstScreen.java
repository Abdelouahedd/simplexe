package screens;

import classes.Simplexe;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
public class FirstScreen extends Stage {
    VBox hb = new VBox();
    Button max = new Button("Maximisation");
    Button min = new Button("Minimisation");
    Text titre = new Text(
            "Ce programe permet de faire la solution d'un"
                    + " probleme Liniare a l'aide de "
                    + "la methode Simplexe");
    public FirstScreen(){
        hb.setAlignment(Pos.CENTER);
        VBox.setMargin(min, new Insets(20));
        VBox.setMargin(titre, new Insets(20));
        titre.setStyle("-fx-font-size:15px;-fx-font:15px Tahoma;");
        min.setPadding(new Insets(20));
        max.setPadding(new Insets(20));

        max.setStyle("-fx-background-color: DarkGray ; -fx-text-fill: white;");
        min.setStyle("-fx-background-color: DarkGray ; -fx-text-fill: white;");

        hb.getChildren().addAll(titre, max, min);
        max.setOnAction(e->{
            Simplexe sm = new Simplexe("MAX");
            new Pls(sm);
            this.close();
        });
        min.setOnAction(e->{
            Simplexe sm = new Simplexe("Min");
            new Pls(sm);
            this.close();
        });
        this.setScene(new Scene(hb));
        this.show();
    }
}
