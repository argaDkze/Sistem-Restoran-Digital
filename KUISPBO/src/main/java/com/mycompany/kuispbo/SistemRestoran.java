/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo;

/**
 *
 * @author Hype AMD
 */
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SistemRestoran implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nama;
    private HashMap<String, Pelanggan> daftarPelanggan = new LinkedHashMap<String, Pelanggan>();
    private HashMap<String, Menu> daftarMenu = new LinkedHashMap<String, Menu>();
    private ArrayList<Transaksi> logTransaksi = new ArrayList<Transaksi>();

    public SistemRestoran(String nama) {
        this.nama = nama;
    }

    public void tambahPelanggan(String id, String nama) {
        daftarPelanggan.put(id, new Pelanggan(id, nama));
    }

    public void tambahMenu(String kode, String nama, int stok) {
        Menu menu;
        if (nama.contains("Premium")) {
            menu = new MenuPremium(kode, nama, stok);
        } else if (nama.contains("Ekonomis")) {
            menu = new MenuEkonomis(kode, nama, stok);
        } else {
            menu = new MenuReguler(kode, nama, stok);
        }
        daftarMenu.put(kode, menu);
    }

    public void jalankanTransaksiDariFile(String namaFile) throws IOException {
        StringBuilder isiFile = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(namaFile));
        try {
            String baris;
            while ((baris = reader.readLine()) != null) {
                isiFile.append(baris).append("|");
            }
        } finally {
            reader.close();
        }

        String[] daftarRecord = isiFile.toString().split("\\|");
        for (int i = 0; i < daftarRecord.length; i++) {
            String record = daftarRecord[i].trim();
            if (record.isEmpty()) {
                continue;
            }
            try {
                prosesRecord(record);
            } catch (TransaksiException e) {
                System.out.println("[TRANSAKSI GAGAL] " + e.getMessage());
            }
        }
    }

    private void prosesRecord(String record) throws TransaksiException {
        String idPelanggan = null;
        String kodeMenu = null;
        String teksJumlah = null;

        String[] daftarPasangan = record.split(",");
        for (int i = 0; i < daftarPasangan.length; i++) {
            String pasangan = daftarPasangan[i].trim();
            if (pasangan.isEmpty()) {
                continue;
            }
            String[] kunciNilai = pasangan.split("=");
            if (kunciNilai.length != 2) {
                throw new TransaksiException("Format data salah: " + record);
            }
            String kunci = kunciNilai[0].trim();
            String nilai = kunciNilai[1].trim();
            if (kunci.equals("ID")) {
                idPelanggan = nilai;
            } else if (kunci.equals("KD")) {
                kodeMenu = nilai;
            } else if (kunci.equals("JML")) {
                teksJumlah = nilai;
            }
        }

        if (idPelanggan == null || kodeMenu == null || teksJumlah == null) {
            throw new TransaksiException("Data tidak lengkap: " + record);
        }

        int jumlah;
        try {
            jumlah = Integer.parseInt(teksJumlah);
        } catch (NumberFormatException e) {
            throw new TransaksiException("Jumlah bukan angka: " + teksJumlah);
        }
        if (jumlah <= 0) {
            throw new TransaksiException("Jumlah harus lebih dari 0: " + record);
        }

        Pelanggan pelanggan = daftarPelanggan.get(idPelanggan);
        if (pelanggan == null) {
            throw new DataTidakDitemukanException("Pelanggan dengan ID " + idPelanggan + " tidak ditemukan");
        }

        Menu menu = daftarMenu.get(kodeMenu);
        if (menu == null) {
            throw new DataTidakDitemukanException("Menu dengan kode " + kodeMenu + " tidak ditemukan");
        }

        menu.kurangiStok(jumlah);

        Transaksi transaksi = new Transaksi(pelanggan, menu, jumlah);
        logTransaksi.add(transaksi);
        pelanggan.tambahBelanja(transaksi.getTotal());

        System.out.println("[TRANSAKSI BERHASIL] " + pelanggan.getNama() + " memesan " + jumlah + " x " + menu.getNama() + " = Rp" + transaksi.getTotal());
    }

    public void simpanLaporanAkhir() {
        try {
            ObjectOutputStream keluaran = new ObjectOutputStream(new FileOutputStream("state.bak"));
            keluaran.writeObject(this);
            keluaran.close();
            System.out.println("Serialisasi berhasil disimpan ke state.bak");
        } catch (IOException e) {
            System.out.println("[ERROR SERIALISASI] " + e.getMessage());
        }

        try {
            FileWriter penulis = new FileWriter("laporan.txt");
            penulis.write("LAPORAN AKHIR - " + nama + "\n\n");

            penulis.write("RIWAYAT TRANSAKSI\n");
            penulis.write(String.format("%-4s %-8s %-12s %-22s %-10s %6s %12s\n", "No", "ID", "Pelanggan", "Menu", "Kategori", "Jumlah", "Total"));
            for (int i = 0; i < logTransaksi.size(); i++) {
                Transaksi t = logTransaksi.get(i);
                penulis.write(String.format("%-4d %-8s %-12s %-22s %-10s %6d %12d\n",
                        i + 1,
                        t.getPelanggan().getId(),
                        t.getPelanggan().getNama(),
                        t.getMenu().getNama(),
                        t.getMenu().getKategori(),
                        t.getJumlah(),
                        t.getTotal()));
            }

            penulis.write("\nSISA STOK MENU\n");
            penulis.write(String.format("%-8s %-22s %-10s %6s\n", "Kode", "Menu", "Kategori", "Stok"));
            for (Menu menu : daftarMenu.values()) {
                penulis.write(String.format("%-8s %-22s %-10s %6d\n",
                        menu.getKode(),
                        menu.getNama(),
                        menu.getKategori(),
                        menu.getStok()));
            }

            penulis.write("\nTOTAL BELANJA PELANGGAN\n");
            penulis.write(String.format("%-8s %-12s %12s\n", "ID", "Nama", "Total"));
            for (Pelanggan p : daftarPelanggan.values()) {
                penulis.write(String.format("%-8s %-12s %12d\n", p.getId(), p.getNama(), p.getTotalBelanja()));
            }

            penulis.close();
            System.out.println("Laporan berhasil ditulis ke laporan.txt");
        } catch (IOException e) {
            System.out.println("[ERROR LAPORAN] " + e.getMessage());
        }
    }
}