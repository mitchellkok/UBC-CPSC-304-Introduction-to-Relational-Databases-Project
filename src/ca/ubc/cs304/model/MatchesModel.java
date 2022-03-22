package ca.ubc.cs304.model;

import java.util.Date;

public class MatchesModel {
    private final String mid;
    private final String result;
    private final String oname;
    private final String stname;
    private final String cityA;
    private final String teamA;
    private final String cityB;
    private final String teamB;
    private final Date date;

    public MatchesModel(String mid, String result, String oname, String stname, String cityA, String teamA, String cityB, String teamB, Date date) {
        this.mid = mid;
        this.result = result;
        this.oname = oname;
        this.stname = stname;
        this.cityA = cityA;
        this.teamA = teamA;
        this.cityB = cityB;
        this.teamB = teamB;
        this.date = date;
    }

    public String getMid() {
        return mid;
    }

    public String getResult() {
        return result;
    }

    public String getOname() {
        return oname;
    }

    public String getStname() {
        return stname;
    }

    public String getCityA() {
        return cityA;
    }

    public String getTeamA() {
        return teamA;
    }

    public String getCityB() {
        return cityB;
    }

    public String getTeamB() {
        return teamB;
    }

    public Date getDate() {
        return date;
    }
}
