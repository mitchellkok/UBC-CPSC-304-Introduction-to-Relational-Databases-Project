package ca.ubc.cs304.model;

public class TeamsModel {
    private String tname;
    private City city;
//    private String country;
    private int winpercent;

    public TeamsModel(String tname, City city, int winpercent) {
        this.tname = tname;
        this.city = city;
        this.winpercent = winpercent;
    }

    public String getTname() {
        return tname;
    }

    public City getCity() {
        return city;
    }

    public int getWinpercent() {
        return winpercent;
    }

    public void setWinpercent(int winpercent) {
        this.winpercent = winpercent;
    }
}
