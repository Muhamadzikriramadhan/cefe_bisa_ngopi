package com.example.cefe_bisa_ngopi;

public class DBHelper {
    private String idMenu;
    private String namaMenu;
    private String hargaMenu;

    public DBHelper(String idMenu, String namaMenu, String hargaMenu) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.hargaMenu = hargaMenu;
    }

    public String getIdMenu() {
        return idMenu;
    }
    public String getNamaMenu() {
        return namaMenu;
    }
    public String getHargaMenu() {
        return hargaMenu;
    }
}
