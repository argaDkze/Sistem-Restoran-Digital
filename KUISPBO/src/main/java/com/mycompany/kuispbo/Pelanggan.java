/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo;

/**
 *
 * @author Hype AMD
 */
public class Pelanggan extends Entitas {
    private static final long serialVersionUID = 1L;
    private int totalBelanja;

    public Pelanggan(String id, String nama) {
        super(id, nama);
        this.totalBelanja = 0;
    }

    public void tambahBelanja(int nilai) {
        totalBelanja = totalBelanja + nilai;
    }

    public int getTotalBelanja() {
        return totalBelanja;
    }

    public String getPeran() {
        return "Pelanggan";
    }
}