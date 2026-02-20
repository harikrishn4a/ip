package tanka;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI window.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Tanka tanka;

    private Image userImage = loadImage("/images/User.png", "/images/DaUser.png");
    private Image dukeImage = loadImage("/images/Tanka.png", "/images/DaDuke.png");

    private static Image loadImage(String primary, String fallback) {
        if (MainWindow.class.getResource(primary) != null) {
            return new Image(MainWindow.class.getResourceAsStream(primary));
        }
        return new Image(MainWindow.class.getResourceAsStream(fallback));
    }

    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /** Injects the Tanka instance and shows the initial welcome message. */
    public void setTanka(Tanka t) {
        tanka = t;
        dialogContainer.getChildren().add(DialogBox.getDukeWelcomeDialog(tanka.getWelcomeMessage(), dukeImage));
    }

    /**
     * Creates two dialog boxes (user and Tanka reply) and appends them to the dialog container.
     * Error responses are shown in a distinct error-style bubble.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        Tanka.GuiResponse response = tanka.getResponseWithStatus(input);
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
        if (response.isError()) {
            dialogContainer.getChildren().add(DialogBox.getDukeErrorDialog(response.getMessage(), dukeImage));
        } else {
            dialogContainer.getChildren().add(DialogBox.getDukeDialog(response.getMessage(), dukeImage));
        }
        userInput.clear();
    }
}
