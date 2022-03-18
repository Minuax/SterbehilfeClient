package de.michl.sterbehilfeclient.person.impl;

import de.michl.sterbehilfeclient.person.Person;

public class Worker extends Person {

    private String name, sirname;

    public Worker(int iD, String username, String name, String sirname) {
        super(iD, username);

        this.name = name;
        this.sirname = sirname;
    }

    public String getName() {return name;}

    public String getSirname() {return sirname;}
}
