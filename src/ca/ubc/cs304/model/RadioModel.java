package ca.ubc.cs304.model;

public class RadioModel{
    private final String bname;
    private final String country;
    private final int contact;
    private final int frequency;

    public RadioModel(String bname, String country, int contact, int frequency) {
        this.bname = bname;
        this.country = country;
        this.contact = contact;
        this.frequency = frequency;
    }

    public String getBname(){ return bname; }
    public String getCountry(){ return country; }
    public int getContact(){ return contact; }
    public int getFrequency(){ return frequency; }
}
