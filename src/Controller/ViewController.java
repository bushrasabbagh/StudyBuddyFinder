package Controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class ViewController {

    private final static String imageFilePath = "images/logo.png";

    public ViewController() {

    }

    public static void cancelButton(Button cancelButton) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public static void backButton(Button backButton) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public static void loadIcon(ImageView view) {
        File imageFile = new File(imageFilePath);
        Image brandingImage = new Image(imageFile.toURI().toString());
        view.setImage(brandingImage);
    }


}
