package de.michl.sterbehilfeclient.person;

import de.michl.sterbehilfeclient.Role;
import de.michl.sterbehilfeclient.SterbehilfeClient;
import de.michl.sterbehilfeclient.person.impl.Client;
import de.michl.sterbehilfeclient.person.impl.Worker;

public class PersonHandler {

    private Role role;
    private Person person;

    public PersonHandler() {
        this.role = Role.CLIENT;
    }

    public void registerWorker(int id, String username, String name, String sirname) {
        this.role = Role.SUPPORTER;
        this.person = new Worker(id, username, name, sirname);

        SterbehilfeClient.getInstance().getCommunicationHandler().sendMessage("connect;worker;" + id + ";" + username + ";" + name + ";" + sirname);
    }

    public void registerClient(int id, String username) {
        this.role = Role.CLIENT;
        this.person = new Client(id, username);

        SterbehilfeClient.getInstance().getCommunicationHandler().sendMessage("connect;client;" + id + ";" + username);
    }

    public Role getRole() {return role;}

    public Person getPerson() {return person;}

    public void setRole(Role role) {this.role = role;}
}
