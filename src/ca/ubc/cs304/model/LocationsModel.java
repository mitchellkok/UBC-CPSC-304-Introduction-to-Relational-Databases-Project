package ca.ubc.cs304.model;

public class LocationsModel {
    private final String address;
    private final String postalcode;

    public LocationsModel(String address, String postalcode) {
        this.address = address;
        this.postalcode = postalcode;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalcode() {
        return postalcode;
    }

}
