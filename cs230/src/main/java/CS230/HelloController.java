package CS230;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * To display the message of the day
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}