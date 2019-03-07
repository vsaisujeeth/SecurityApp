package com.example.android.securityapp;

import java.util.Date;

public class Log_List_Item {
    public String vehicleno;
    //public String type;
    public String date;
    public String time;

    public Log_List_Item(String date, String time, String type, String vehicleno) {
        this.vehicleno = vehicleno;
       // this.type = type;
        this.date = date;
        this.time = time;

    }
    public Log_List_Item(){

    }
}
