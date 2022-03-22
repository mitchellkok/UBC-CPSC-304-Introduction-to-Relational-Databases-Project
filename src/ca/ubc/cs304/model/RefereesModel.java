package ca.ubc.cs304.model;

public class RefereesModel {
    private final int rlicencenumber;
    private final String rname;
    private final String gender;
    private int age;

    public RefereesModel(int rlicencenumber, String rname, String gender, int age) {
        this.rlicencenumber = rlicencenumber;
        this.rname = rname;
        this.gender = gender;
        this.age = age;
    }

    public int getRlicencenumber() {return this.rlicencenumber;}

    public String getRname() {
        return rname;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
