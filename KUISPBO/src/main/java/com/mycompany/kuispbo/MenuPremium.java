/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo;

/**
 *
 * @author Hype AMD
 */
public class MenuPremium extends Menu {
    private static final long serialVersionUID = 1L;

    public MenuPremium(String kode, String nama, int stok) {
        super(kode, nama, stok);
    }

    public int getHarga() {
        return 150000;
    }

    public String getKategori() {
        return "Premium";
    }
}