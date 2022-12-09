package CS230;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * does this need comments ??
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}