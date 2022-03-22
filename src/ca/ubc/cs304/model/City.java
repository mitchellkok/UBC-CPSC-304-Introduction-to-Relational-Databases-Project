package ca.ubc.cs304.model;

public class City {
    private final String cityName;
    private final String country;

    public City(String cityName, String country) {
        this.cityName = cityName;
        this.country = country;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }
}
