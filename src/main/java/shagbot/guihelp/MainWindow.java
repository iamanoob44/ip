package shagbot.guihelp;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import shagbot.Shagbot;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    private static final int MAX_MESSAGES_IN_DIALOGUE = 7;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Shagbot shagbot;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/users.png"));
    private Image shagBotImage = new Image(this.getClass().getResourceAsStream("/images/shagbots.png"));

    /**
     * Initialise the GUI
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.setStyle("-fx-background-color: #0A192F;");
        scrollPane.setStyle("-fx-background-color: #0A192F;");
        this.setStyle("-fx-background-color: #0A192F;");
        dialogContainer.getChildren().add(
                DialogBox.getShagBotDialog("Hello! Shagbot at your service. What can I do for you?", shagBotImage)
        );
    }

    /**
     * Injects the Shagbot instance
     */
    protected void setShagbot(Shagbot s) {
        shagbot = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return; // Ignore empty input
        }

        String response = shagbot.getResponse(input);

        // Add new dialog nodes
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getShagBotDialog(response, shagBotImage)
        );

        // Remove older messages if there is too many of them, since it will affect the performance of Shagbot
        if (dialogContainer.getChildren().size() > MAX_MESSAGES_IN_DIALOGUE) {
            dialogContainer.getChildren().remove(0,
                    dialogContainer.getChildren().size() - MAX_MESSAGES_IN_DIALOGUE);
        }

        userInput.clear();

        // Exit GUI if 'bye' command is detected
        if (input.equalsIgnoreCase("bye")) {
            closeApplication();
        }
    }

    /**
     * Closes the application safely.
     */
    private void closeApplication() {
        Stage stage = (Stage) userInput.getScene().getWindow();
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> stage.close());
        delay.play();
    }
}

