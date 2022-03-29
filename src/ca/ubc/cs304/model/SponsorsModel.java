package ca.ubc.cs304.model;

public class SponsorsModel{
    private final String spname;
    private int contact;

    public SponsorsModel(String spname, int contact){
        this.spname = spname;
        this.contact = contact;
    }

    public String getSpname(){ return spname;}

    public int getContact(){ return contact; }

    public void setContact(int contact){ this.contact = contact; }
}
