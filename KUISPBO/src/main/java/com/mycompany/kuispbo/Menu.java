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

public abstract class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    private String kode;
    private String nama;
    private int stok;

    public Menu(String kode, String nama, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.stok = stok;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getStok() {
        return stok;
    }

    public void kurangiStok(int jumlah) throws StokTidakCukupException {
        if (jumlah > stok) {
            throw new StokTidakCukupException("Stok " + nama + " tidak cukup (sisa " + stok + ", diminta " + jumlah + ")");
        }
        stok = stok - jumlah;
    }

    public int hitungTotal(int jumlah) {
        return getHarga() * jumlah;
    }

    public abstract int getHarga();

    public abstract String getKategori();
}
