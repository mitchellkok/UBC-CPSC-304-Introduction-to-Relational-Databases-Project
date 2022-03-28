package ca.ubc.cs304.model;

public class StadiumsModel {
    private final String stname;
    private final String address;
    private int capacity;

    public StadiumsModel(String stname, String address, int capacity) {
        this.stname = stname;
        this.address = address;
        this.capacity = capacity;
    }

    public String getStname() {
        return stname;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }
}
