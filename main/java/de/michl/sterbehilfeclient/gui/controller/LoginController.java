package de.michl.sterbehilfeclient.gui.controller;

import de.michl.sterbehilfeclient.SterbehilfeClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Label loginAsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    performLogin();
                }
            }
        });

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                performLogin();
            }
        });
    }

    public void performLogin() {
        if (usernameField.getText().length() > 0 && passwordField.getText().length() > 0) {
            if (SterbehilfeClient.getInstance().getPersonHandler().getRole().name().equalsIgnoreCase("client")) {
                ArrayList<BasicNameValuePair> basicNameValuePairArrayList = new ArrayList<>();
                basicNameValuePairArrayList.add(new BasicNameValuePair("action", "kunde"));
                basicNameValuePairArrayList.add(new BasicNameValuePair("kundeAction", "login"));
                basicNameValuePairArrayList.add(new BasicNameValuePair("args", usernameField.getText() + ";" + passwordField.getText()));

                String result = null;
                try {
                    result = IOUtils.toString(SterbehilfeClient.getInstance().getWebHandler().sendHTTPRequest("http://minuax.de/index.php", basicNameValuePairArrayList));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] split = result.split(";");
                if (split.length == 2) {
                    if (split[0].equalsIgnoreCase("client")) {
                        SterbehilfeClient.getInstance().getPersonHandler().registerClient(Integer.parseInt(split[1]), usernameField.getText());
                    } else {
                        showLoginError("Der Nutzer konnte nicht gefunden werden. Bitte korrigieren Sie Ihre Eingaben!");
                    }
                } else {
                    if (result.contains("nouser")) {
                        showLoginError("Der Nutzer konnte nicht gefunden werden. Bitte korrigieren Sie Ihre Eingaben!");
                    } else if (result.contains("wrongcreds")) {
                        showLoginError("Falscher Nutzername/Passwort. Bitte korrigieren Sie Ihre Eingaben!");
                    } else {
                        showLoginError("Fehler bei der Kommunikation mit dem Server, versuchen Sie es später erneut!");
                    }
                }
            } else {
                ArrayList<BasicNameValuePair> basicNameValuePairArrayList = new ArrayList<>();
                basicNameValuePairArrayList.add(new BasicNameValuePair("action", "mitarbeiter"));
                basicNameValuePairArrayList.add(new BasicNameValuePair("mitarbeiterAction", "login"));
                basicNameValuePairArrayList.add(new BasicNameValuePair("args", usernameField.getText() + ";" + passwordField.getText()));
                String result = null;

                try {
                    result = IOUtils.toString(SterbehilfeClient.getInstance().getWebHandler().sendHTTPRequest("http://minuax.de/index.php", basicNameValuePairArrayList));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] split = result.split(";");
                if (split.length == 4) {
                    if (split[0].equalsIgnoreCase("worker")) {
                        SterbehilfeClient.getInstance().getPersonHandler().registerWorker(Integer.parseInt(split[1]), usernameField.getText(), split[2], split[3]);

                        SterbehilfeClient.getInstance().getFrameHandler().openFrame("WorkerQueue", "Warteschlange");
                    } else {
                        showLoginError("Der Nutzer konnte nicht gefunden werden. Bitte korrigieren Sie Ihre Eingaben!");
                    }
                } else {
                    if (result.contains("nouser")) {
                        showLoginError("Der Nutzer konnte nicht gefunden werden. Bitte korrigieren Sie Ihre Eingaben!");
                    } else if (result.contains("wrongcreds")) {
                        showLoginError("Falscher Nutzername/Passwort. Bitte korrigieren Sie Ihre Eingaben!");
                    } else {
                        showLoginError("Fehler bei der Kommunikation mit dem Server, versuchen Sie es später erneut!");
                    }
                }
            }
        }
    }

    public void showLoginError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, errorMessage, ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            this.usernameField.clear();
            this.passwordField.clear();
            alert.close();
        }
    }

}
