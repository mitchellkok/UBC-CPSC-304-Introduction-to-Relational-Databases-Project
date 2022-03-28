package ca.ubc.cs304.model;

public class TeamsModel {
    private final String tname;
    private final City city;
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
