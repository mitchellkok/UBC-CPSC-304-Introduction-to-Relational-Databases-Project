package ca.ubc.cs304.model;

import java.util.Date;

public class MatchesModel {
    private final String mid;
    private final String oname;
    private final String stname;
    private final String cityA;
    private final String teamA;
    private final String cityB;
    private final String teamB;
    private final int rentalfee;
    private String result = "0-0";
    private Date date;

    public MatchesModel(String mid, String oname, String stname, String cityA, String teamA, String cityB, String teamB, int rentalfee, Date date, String result) {
        this.mid = mid;
        this.oname = oname;
        this.stname = stname;
        this.cityA = cityA;
        this.teamA = teamA;
        this.cityB = cityB;
        this.teamB = teamB;
        this.rentalfee = rentalfee;
        this.result = result;
        this.date = date;
    }

    public String getMid() {
        return mid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String setResult() {
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

    public int getRentalfee() {
        return rentalfee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
