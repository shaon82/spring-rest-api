package com.updatetech.SpringRestAPI.ui.model.response;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. please check documentation for this field!"),
    RECORD_ALREADY_EXIST("Record already exist!"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provided id is not found!"),
    AUTHENTICATION_FAILED("Authentication failed!"),
    COULD_NOT_UPDATE_RECORD("Could not update record!"),
    COULD_NOT_DELETE_RECORD("Could not delete record!"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address not verified!");


    private String errorMessages;

    ErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }


    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }
}
