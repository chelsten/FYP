package com.example.fyp.Adapter;

public class adapterList {
    private String  date;
    private String pt;
    private String tp;

    public adapterList( String plantType, String datePrice, String totalPrice) {

        this.pt = plantType;
        this.tp = totalPrice;
        this.date = datePrice;
    }

    public String getPt() {
        return pt;
    }
    public String getDate(){
        return date;
    }
    public String getPrice() {
        return tp;
    }

}