package com.example.register.bean;

public class User {
    private int id;
    private String name;
    private String password;
    private String gender;
    private String fakeName;
    private String birthTime;
    private String city;
    private String school;
    private String edit;

    public User(int id, String name, String password, String gender, String fakeName, String birthTime, String city, String school, String edit) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.fakeName = fakeName;
        this.birthTime = birthTime;
        this.city = city;
        this.school = school;
        this.edit = edit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFakeName() {
        return fakeName;
    }

    public void setFakeName(String fakeName) {
        this.fakeName = fakeName;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public User(String name, String password, String gender) {
        this.fakeName = name;
        this.birthTime = null;
        this.city = null;
        this.school = null;
        this.edit = null;
        this.name = name;
        this.password = password;
        this.gender = gender;
    }

    public User() {
    }

    public User(String name, String password) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", fName='" + fakeName + '\'' +
                ", birthTime='" + birthTime + '\'' +
                ", city='" + city + '\'' +
                ", school='" + school + '\'' +
                ", edit='" + edit + '\'' +
                '}';
    }

    public User(String name, String password, String gender, String fakeName, String birthTime, String city, String school, String edit) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.fakeName = fakeName;
        this.birthTime = birthTime;
        this.city = city;
        this.school = school;
        this.edit = edit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
