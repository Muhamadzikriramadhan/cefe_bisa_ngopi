package com.example.cefe_bisa_ngopi;

public class DBHelperAkun {
    private String idUser;
    private String namaUser;
    private String passwordUser;
    private String typeUser;

    public DBHelperAkun(String idUser, String namaUser, String passwordUser, String typeUser) {
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.passwordUser = passwordUser;
        this.typeUser = typeUser;
    }

    public String getIdUser() {
        return idUser;
    }
    public String getNamaUser() {
        return namaUser;
    }
    public String getPasswordUser() {
        return passwordUser;
    }
    public String getTypeUser() {
        return typeUser;
    }
}
