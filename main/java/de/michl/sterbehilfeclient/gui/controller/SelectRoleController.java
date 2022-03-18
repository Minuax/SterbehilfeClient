package de.michl.sterbehilfeclient.gui.controller;

import de.michl.sterbehilfeclient.Role;
import de.michl.sterbehilfeclient.SterbehilfeClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectRoleController implements Initializable {


    public Button customerButton;
    public Button supporterButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SterbehilfeClient.getInstance().getPersonHandler().setRole(Role.CLIENT);
                SterbehilfeClient.getInstance().getFrameHandler().openFrame("Login", "Einloggen: Betroffene(r)");
            }
        });

        supporterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SterbehilfeClient.getInstance().getPersonHandler().setRole(Role.SUPPORTER);
                SterbehilfeClient.getInstance().getFrameHandler().openFrame("Login", "Einloggen: MitarbeiterIn");
            }
        });
    }
}

