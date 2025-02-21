package shagbot.guihelp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shagbot.Shagbot;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Shagbot shagbot = new Shagbot("shagbot");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(400);
            stage.setMinWidth(700);
            stage.setTitle("Shagbot");
            fxmlLoader.<MainWindow>getController().setShagbot(shagbot); // inject the Shagbot instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





