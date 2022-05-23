package com.example.cefe_bisa_ngopi;

public class DBHelperTransaksi {
    private String idTransaksi;
    private String menu;
    private String total;
    private String idKasir;
    private String timeDate;
    public DBHelperTransaksi(String idTransaksi, String menu, String total, String idKasir, String timeDate) {
        this.idTransaksi = idTransaksi;
        this.menu = menu;
        this.total = total;
        this.idKasir = idKasir;
        this.timeDate = timeDate;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }
    public String getMenu() {
        return menu;
    }
    public String getTotal() {
        return total;
    }
    public String getIdKasir() {
        return idKasir;
    }
    public String getTimeDate() {
        return timeDate;
    }
}
