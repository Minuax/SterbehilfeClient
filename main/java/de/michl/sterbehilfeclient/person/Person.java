package de.michl.sterbehilfeclient.person;

public abstract class Person {

    private int iD;
    private String username;

    public Person(int iD, String username) {
        this.iD = iD;
        this.username = username;
    }

    public int getiD() {return iD;}

    public String getUsername() {return username;}
}
