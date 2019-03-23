package com.remote.doctor.constant;

public enum Roles {
    CLIENT("ROLE_CLIENT"),  DOCTOR("ROLE_DOCTOR"), ADMIN("ROLE_ADMIN");

    private String val;

    Roles(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}


