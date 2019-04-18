package com.example.fyp.Adapter;

public class adapter2List {
    private String  date;
    private String pt;
    private String tp;
    private String market_id;
    public adapter2List(String market_id, String plantType, String datePrice, String totalPrice) {
        this.market_id = market_id;
        this.pt = plantType;
        this.tp = totalPrice;
        this.date = datePrice;
    }

    public String getmarketId() {
        return market_id;
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