package com.example.fyp.Adapter;

public class adapter3List {
    private String  date;
    private String pt;
    private String tp;
    private String saveid;
    public adapter3List( String saveid, String jumlahPendapatantemp, String jumlahPerbelanjaantemp, String netProfittemp) {
        this.pt = jumlahPendapatantemp;
        this.tp = jumlahPerbelanjaantemp;
        this.date = netProfittemp;
        this.saveid = saveid;
    }

    public String getSaveid() {
        return saveid;
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