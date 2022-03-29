package ca.ubc.cs304.model;

public class TVModel{
    private final String bname;
    private final String country;
    private int contact;
    private int channelnumber;

    public TVModel(String bname, String country, int contact, int channelnumber) {
        this.bname = bname;
        this.country = country;
        this.contact = contact;
        this.channelnumber = channelnumber;
    }

    public String getBname(){ return bname; }

    public String getCountry(){ return country; }

    public int getContact(){ return contact; }

    public void setContact(int contact){ this.contact = contact; }

    public int getChannelnumber(){ return channelnumber; }

    public void setChannelnumber(int channelnumber){ this.channelnumber = channelnumber; }
}
