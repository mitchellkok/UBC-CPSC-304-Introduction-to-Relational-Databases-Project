package ca.ubc.cs304.model;

public class LivestreamsModel {
    private final String bname;
    private final String country;
    private final String mid;

    public LivestreamsModel(String bname, String country, String mid) {
        this.bname = bname;
        this.country = country;
        this.mid = mid;
    }

    public String getBname() {
        return bname;
    }

    public String getCountry() {
        return country;
    }

    public String getMid() {
        return mid;
    }
}
