package ca.ubc.cs304.model;

public class OfficiatesModel {
    private final int rlicensenumber;
    private final String mid;

    public OfficiatesModel(int rlicensenumber, String mid) {
        this.rlicensenumber = rlicensenumber;
        this.mid = mid;
    }

    public int getRlicensenumber() {
        return rlicensenumber;
    }

    public String getMid() {
        return mid;
    }
}
