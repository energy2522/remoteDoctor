package com.remote.doctor.constant;

public enum Roles {
    CLIENT("ROLE_client"),  DOCTOR("ROLE_doctor"), ADMIN("ROLE_admin");

    private String val;

    Roles(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}


