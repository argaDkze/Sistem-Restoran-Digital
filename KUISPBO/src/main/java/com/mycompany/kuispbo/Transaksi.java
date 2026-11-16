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

public class Transaksi implements Serializable {
    private static final long serialVersionUID = 1L;
    private Pelanggan pelanggan;
    private Menu menu;
    private int jumlah;
    private int total;

    public Transaksi(Pelanggan pelanggan, Menu menu, int jumlah) {
        this.pelanggan = pelanggan;
        this.menu = menu;
        this.jumlah = jumlah;
        this.total = menu.hitungTotal(jumlah);
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getTotal() {
        return total;
    }
}