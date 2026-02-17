package tanka;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Tanka using JavaFX and FXML.
 */
public class Main extends Application {

    private Tanka tanka = new Tanka();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            scene.getStylesheets().add(Main.class.getResource("/view/application.css").toExternalForm());
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTanka(tanka);
            stage.setTitle("Tanka");
            stage.setResizable(true);
            stage.setMinWidth(380.0);
            stage.setMinHeight(400.0);
            stage.setWidth(480.0);
            stage.setHeight(720.0);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
