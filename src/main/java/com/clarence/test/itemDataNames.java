package com.clarence.test;

public enum itemDataNames {
    CONFIRM("Confirm"),
    BACK("Back");

    private String name;
    itemDataNames(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) {this.name = name; }
}
