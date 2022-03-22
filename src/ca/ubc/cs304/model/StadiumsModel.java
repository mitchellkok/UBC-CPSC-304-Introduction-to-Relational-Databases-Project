package ca.ubc.cs304.model;

public class StadiumsModel {
    private final String stname;
    private final String address;
    private final String postalcode;
    private final int capacity;

    public StadiumsModel(String stname, String address, String postalcode, int capacity) {
        this.stname = stname;
        this.address = address;
        this.postalcode = postalcode;
        this.capacity = capacity;
    }

    public String getStname() {
        return stname;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public int getCapacity() {
        return capacity;
    }
}
