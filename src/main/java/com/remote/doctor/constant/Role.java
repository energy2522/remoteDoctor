package com.remote.doctor.constant;

public enum Role {
    ADMIN("ROLE_admin"), CLIENT("ROLE_client"), DOCTOR("ROLE_doctor");

    private String val;

    Role(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}


