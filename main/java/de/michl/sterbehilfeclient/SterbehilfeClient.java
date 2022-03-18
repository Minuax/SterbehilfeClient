package de.michl.sterbehilfeclient;

import de.michl.sterbehilfeclient.communication.CommunicationHandler;
import de.michl.sterbehilfeclient.communication.web.WebHandler;
import de.michl.sterbehilfeclient.gui.FrameHandler;
import de.michl.sterbehilfeclient.person.PersonHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;

public class SterbehilfeClient extends Application {

    private static SterbehilfeClient instance;

    private Stage stage;

    private CommunicationHandler communicationHandler;
    private WebHandler webHandler;
    private FrameHandler frameHandler;
    private PersonHandler personHandler;

    public SterbehilfeClient() {
        instance = this;

        this.webHandler = new WebHandler();
        this.frameHandler = new FrameHandler();
        this.personHandler = new PersonHandler();
        this.communicationHandler = new CommunicationHandler();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                communicationHandler.sendMessage("disconnect");

                if (personHandler.getPerson() != null)
                    if (personHandler.getRole().name().equalsIgnoreCase("client")) {
                        ArrayList<BasicNameValuePair> basicNameValuePairArrayList = new ArrayList<>();
                        basicNameValuePairArrayList.add(new BasicNameValuePair("action", "kunde"));
                        basicNameValuePairArrayList.add(new BasicNameValuePair("kundeAction", "remove"));
                        basicNameValuePairArrayList.add(new BasicNameValuePair("args", personHandler.getPerson().getiD() + ";"));

                        try {
                            webHandler.sendHTTPRequest("http://minuax.de/index.php", basicNameValuePairArrayList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        this.frameHandler.openFrame("SelectRole", "Rolle wählen!");
    }

    public static SterbehilfeClient getInstance() {
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public CommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }

    public WebHandler getWebHandler() {
        return webHandler;
    }

    public FrameHandler getFrameHandler() {
        return frameHandler;
    }

    public PersonHandler getPersonHandler() {
        return personHandler;
    }
}
