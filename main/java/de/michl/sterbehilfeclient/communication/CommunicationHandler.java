package de.michl.sterbehilfeclient.communication;

import de.michl.sterbehilfeclient.SterbehilfeClient;
import de.michl.sterbehilfeclient.communication.socket.CommunicationThread;
import de.michl.sterbehilfeclient.communication.socket.Socket;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Base64;

public class CommunicationHandler {

    private Socket socket;

    private CommunicationThread communicationThread;

    public CommunicationHandler() {
        try {
            this.socket = new Socket("fjg31.ddns.net", 11831);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (socket.connect()) {
            communicationThread = new CommunicationThread(socket);
            communicationThread.start();
        } else {
            System.err.println("Keine Verbindung möglich!");
        }
    }

    public void processMessage(String msg) {
        String message = new String(Base64.getDecoder().decode(msg));
        if (message.split(";").length > 0) {
            String[] messageParts = message.split(";");

            String command = messageParts[0];
            switch (command) {
                case "chatAppend":
                    SterbehilfeClient.getInstance().getFrameHandler().getChatController().appendChat(SterbehilfeClient.getInstance().getFrameHandler().getPartnerName(), messageParts[1], true);
                    break;
                case "startQueue":
                    Platform.runLater(() -> {
                        SterbehilfeClient.getInstance().getFrameHandler().openFrame("Queue", "Warteschlange");
                    });
                    break;
                case "matchFound":
                    Platform.runLater(() -> {
                        SterbehilfeClient.getInstance().getFrameHandler().setPartnerName(messageParts[1]);
                        SterbehilfeClient.getInstance().getFrameHandler().openChat();
                    });
                    break;
                case "unmatch":
                    Platform.runLater(() -> {
                        SterbehilfeClient.getInstance().getFrameHandler().openFrame("WorkerQueue", "Warteschlange");
                    });
                    break;
                case "connected":
                    System.out.println("Successfully connected!");
                    break;
                default:
                    System.out.println("Something went wrong when processing message: " + message);
            }

        }
    }

    public void sendMessage(String message) {
        String encoded = Base64.getEncoder().encodeToString(message.getBytes());
        try {
            this.socket.write(encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommunicationThread getCommunicationThread() {
        return communicationThread;
    }

    public Socket getSocket() {
        return socket;
    }
}