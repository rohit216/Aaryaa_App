package com.example.kanchan.aaryaa_app;

/**
 * Created by gdevop on 31/3/18.
 */

public class WomenViewCases {

    String caseId,name,status,type_case,registered_by, registration_date;

    public WomenViewCases(String caseId, String name, String status, String type_case, String registered_by, String registration_date) {
        this.caseId = caseId;
        this.name = name;
        this.status = status;
        this.type_case = type_case;
        this.registered_by = registered_by;
        this.registration_date = registration_date;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType_case() {
        return type_case;
    }

    public void setType_case(String type_case) {
        this.type_case = type_case;
    }

    public String getRegistered_by() {
        return registered_by;
    }

    public void setRegistered_by(String registered_by) {
        this.registered_by = registered_by;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }
}
