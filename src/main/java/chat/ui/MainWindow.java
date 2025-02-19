package chat.ui;

import chat.parser.Job;
import chat.parser.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
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

    private Chat chat;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/cat.png"));
    private final Image chatImage = new Image(this.getClass().getResourceAsStream("/images/dog.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getChatDialog("Welcome to Chat Chatbot!", chatImage)
        );
    }

    /** Injects the Duke instance */
    public void setChat(Chat c) {
        this.chat = c;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        Job job = Parser.parseInput(input);
        String response = chat.getResponse(job);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getChatDialog(response, chatImage)
        );
        userInput.clear();
    }
}
