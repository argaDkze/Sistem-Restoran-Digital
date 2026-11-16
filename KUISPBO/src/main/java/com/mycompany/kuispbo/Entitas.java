/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo;

/**
 *
 * @author Hype AMD
 */
import java.io.Serializable;

public abstract class Entitas implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nama;

    public Entitas(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public abstract String getPeran();
}