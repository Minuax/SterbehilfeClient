package de.michl.sterbehilfeclient.gui;

import de.michl.sterbehilfeclient.SterbehilfeClient;
import de.michl.sterbehilfeclient.gui.controller.ChatController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class FrameHandler {

    private ChatController chatController;

    private String partnerName;

    public void openFrame(String fileName, String title) {
        Parent p;
        try {
            p = FXMLLoader.load(getClass().getResource("/" + fileName + ".fxml"));
            Scene scene = new Scene(p);

            scene.getStylesheets().add("style.css");

            SterbehilfeClient.getInstance().getStage().getIcons().add(new Image("icon.png"));
            SterbehilfeClient.getInstance().getStage().setTitle(title);
            SterbehilfeClient.getInstance().getStage().setScene(scene);
            SterbehilfeClient.getInstance().getStage().setResizable(false);
            SterbehilfeClient.getInstance().getStage().setScene(scene);
            SterbehilfeClient.getInstance().getStage().show();

            SterbehilfeClient.getInstance().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(-1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openChat() {
        SterbehilfeClient.getInstance().getFrameHandler().openFrame("Chat", "Chat: " + SterbehilfeClient.getInstance().getFrameHandler().getPartnerName());
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    public ChatController getChatController() {
        return chatController;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }
}
