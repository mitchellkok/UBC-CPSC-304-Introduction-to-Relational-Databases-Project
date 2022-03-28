package ca.ubc.cs304.model;

public class FinancesModel {
    private final String spname;
    private final String mid;
    private int amount;

    public FinancesModel(String spname, String mid, int amount) {
        this.spname = spname;
        this.mid = mid;
        this.amount = amount;
    }

    public String getSpname() {
        return spname;
    }

    public String getMid() {
        return mid;
    }

    public int getAmount() {
        return amount;
    }
}
