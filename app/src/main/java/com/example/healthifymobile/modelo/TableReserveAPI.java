package com.example.healthifymobile.modelo;

public class TableReserveAPI {
    public String state,message;

    public TableReserveAPI(String state, String message){
        this.state = state;
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
