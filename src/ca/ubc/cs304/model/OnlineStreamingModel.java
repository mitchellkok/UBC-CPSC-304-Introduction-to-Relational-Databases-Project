package ca.ubc.cs304.model;

public class OnlineStreamingModel {
    private final String bname;
    private final String country;
    private final int contact;
    private final String url;

    public OnlineStreamingModel(String bname, String country, int contact, String url) {
        this.bname = bname;
        this.country = country;
        this.contact = contact;
        this.url = url;
    }

    public String getBname(){ return bname; }
    public String getCountry(){ return country; }
    public int getContact(){ return contact; }
    public String getUrl(){ return url; }
}
