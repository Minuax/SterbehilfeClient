package de.michl.sterbehilfeclient.gui.controller;

import de.michl.sterbehilfeclient.SterbehilfeClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    public Label chatWithLabel;
    public TextArea chatArea;
    public TextField messageField;
    public Button sendButton;
    public Button zoomIn;
    public Button zoomOut;

    public CheckBox muteBox;

    private int fontSize = 12;
    private boolean mute = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SterbehilfeClient.getInstance().getFrameHandler().setChatController(this);

        chatWithLabel.setText("Sie chatten mit " + SterbehilfeClient.getInstance().getFrameHandler().getPartnerName());
        muteBox.setSelected(true);

        messageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    if (messageField.getText().length() > 0 && messageField.getText().replace(" ", "").length() > 0) {
                        SterbehilfeClient.getInstance().getCommunicationHandler().sendMessage("chatMessage;" + messageField.getText());
                        appendChat(SterbehilfeClient.getInstance().getPersonHandler().getPerson().getUsername(), messageField.getText(), false);
                        messageField.clear();
                    }
                }
            }
        });

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (messageField.getText().length() > 0 && messageField.getText().replace(" ", "").length() > 0) {
                    SterbehilfeClient.getInstance().getCommunicationHandler().sendMessage("chatMessage;" + messageField.getText());
                    appendChat(SterbehilfeClient.getInstance().getPersonHandler().getPerson().getUsername(), messageField.getText(), false);
                    messageField.clear();
                }
            }
        });

        muteBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(muteBox.isSelected());
                mute = !muteBox.isSelected();
            }
        });

        zoomIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fontSize += 2;
                chatArea.setStyle("-fx-font-size: " + fontSize);
            }
        });

        zoomOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fontSize -= 2;
                chatArea.setStyle("-fx-font-size: " + fontSize);
            }
        });
    }

    public void appendChat(String from, String message, boolean sound) {
        if (sound && !mute) {
            AudioClip buzzer = new AudioClip(getClass().getResource("/notification.wav").toString());
            buzzer.play();
        }

        chatArea.setText(chatArea.getText() + "\n" + from + "> " + message);
    }
}
