package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import screens.FirstScreen;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       new FirstScreen();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
