package com.example.netmedsassignment.Model;

class Medicines {
    private long id;
    private String name;
    private String type;
    private String company;
    private String strength;
    private String strengthtype;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getStrengthtype() {
        return strengthtype;
    }

    public void setStrengthtype(String strengthtype) {
        this.strengthtype = strengthtype;
    }
}
