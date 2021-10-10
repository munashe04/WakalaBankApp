package com.wakala.wakalabankapp1.entity;

public enum MenuEnum {
    REGISTRATION("Registration"),
    LOAN_APPLICATION("LOAN");

    private String value;
    MenuEnum(String value){
        this.value = value;

    }


    public String getValue() {
        return value;
    }

}
