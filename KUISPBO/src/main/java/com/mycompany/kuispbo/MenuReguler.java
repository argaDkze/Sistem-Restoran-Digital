/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo;

/**
 *
 * @author Hype AMD
 */
public class MenuReguler extends Menu {
    private static final long serialVersionUID = 1L;

    public MenuReguler(String kode, String nama, int stok) {
        super(kode, nama, stok);
    }

    public int getHarga() {
        return 25000;
    }

    public String getKategori() {
        return "Reguler";
    }
}