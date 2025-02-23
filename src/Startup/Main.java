package Startup;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/StartView.fxml"));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}