package de.michl.sterbehilfeclient.communication.socket;

import de.michl.sterbehilfeclient.SterbehilfeClient;

import java.io.IOException;

public class CommunicationThread extends Thread {

    private Socket socket;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = this.socket.readLine()) != null) {
                SterbehilfeClient.getInstance().getCommunicationHandler().processMessage(message);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        super.run();
    }


}
