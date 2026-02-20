package tanka;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Custom control for a chat dialog: an HBox with a label and an image (avatar).
 * Loads layout from DialogBox.fxml.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        getStyleClass().add("dialog-box");
    }

    /**
     * Flips the dialog box so the ImageView is on the left and text on the right (for Tanka's replies).
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * User dialog: image on the right, text on the left; aligned right for asymmetry.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.getStyleClass().add("user");
        db.setMaxWidth(Double.MAX_VALUE);
        return db;
    }

    /**
     * Tanka/Duke dialog: image on the left, text on the right.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.getStyleClass().add("duke");
        db.flip();
        return db;
    }

    /**
     * Tanka error dialog: same layout as duke but with error styling (e.g. red/warning).
     */
    public static DialogBox getDukeErrorDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.getStyleClass().add("duke");
        db.getStyleClass().add("error");
        db.flip();
        return db;
    }

    /**
     * Tanka welcome dialog with logo: uses monospace so the ASCII logo displays correctly,
     * and "welcome" style for wider bubble so message lines fit on one line.
     */
    public static DialogBox getDukeWelcomeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.getStyleClass().add("duke");
        db.getStyleClass().add("welcome");
        db.dialog.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
        db.setMinWidth(400);
        db.setPrefWidth(720);
        db.flip();
        return db;
    }
}
