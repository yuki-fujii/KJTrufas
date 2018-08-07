package com.br.kjtrufas.entidades;

import android.database.sqlite.SQLiteDatabase;

public class EntidadePost {

    private String service;
    private String json;
    private SQLiteDatabase conn;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public SQLiteDatabase getConn() {
        return conn;
    }

    public void setConn(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
