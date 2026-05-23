package controller;

import model.Layanan;
import model.Pelanggan;
import model.PelangganBiasa;
import model.PelangganVIP;

import java.util.ArrayList;
import java.util.List;

// ===== MODUL 2 - CLASS =====
public class PelangganController {

    // ===== MODUL 7 - JAVA COLLECTION =====
    // ArrayList untuk menyimpan semua pelanggan
    private ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
    private int counterID = 1; // untuk generate ID otomatis

    // ===== MODUL 2 - CONSTRUCTOR =====
    // Data awal hardcode saat program pertama dijalankan
    public PelangganController() {
        daftarPelanggan.add(new PelangganBiasa(generateId(), "Andi Saputra", "081234567890", "Jl. Merdeka No. 5"));
        daftarPelanggan.add(new PelangganBiasa(generateId(), "Dewi Lestari",  "082345678901", "Jl. Mawar No. 12"));
        daftarPelanggan.add(new PelangganVIP  (generateId(), "Rudi Hartono",  "083456789012", "Jl. Kenanga No. 3"));
        daftarPelanggan.add(new PelangganVIP  (generateId(), "Siti Rahayu",   "084567890123", "Jl. Melati No. 7"));
    }

    // ===== GENERATE ID OTOMATIS =====
    private String generateId() {
        return "P" + String.format("%03d", counterID++);
    }

    // ===== MODUL 7 - COLLECTION: add() =====
    // Tambah pelanggan baru, tipe ditentukan dari parameter isVIP
    public String tambah(String nama, String noHp, String alamat, boolean isVIP) {
        // Cek duplikat nomor HP
        for (Pelanggan p : daftarPelanggan) {
            if (p.getNoHp().equals(noHp)) {
                return "GAGAL: Nomor HP sudah terdaftar.";
            }
        }

        String id = generateId();
        Pelanggan baru;

        // ===== MODUL 4 - POLYMORPHISM =====
        // Objek yang dibuat beda tipe tapi disimpan sebagai Pelanggan
        if (isVIP) {
            baru = new PelangganVIP(id, nama, noHp, alamat);
        } else {
            baru = new PelangganBiasa(id, nama, noHp, alamat);
        }

        daftarPelanggan.add(baru);
        return "SUKSES: Pelanggan " + nama + " berhasil ditambahkan dengan ID " + id + ".";
    }

    // ===== MODUL 7 - COLLECTION: get(), size() =====
    // Ambil semua pelanggan untuk ditampilkan di frontend
    public List<Pelanggan> getAll() {
        return daftarPelanggan;
    }

    // ===== MODUL 7 - COLLECTION: iterasi =====
    // Cari pelanggan berdasarkan nomor HP
    public Pelanggan cariByNoHp(String noHp) {
        for (Pelanggan p : daftarPelanggan) {
            if (p.getNoHp().equals(noHp)) {
                return p;
            }
        }
        return null;
    }

    // Cari pelanggan berdasarkan ID (dipakai controller lain)
    public Pelanggan cariById(String id) {
        for (Pelanggan p : daftarPelanggan) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // ===== MODUL 7 - COLLECTION: iterasi + setter =====
    // Update data pelanggan berdasarkan ID
    public String update(String id, String nama, String noHp, String alamat) {
        Pelanggan target = cariById(id);
        if (target == null) {
            return "GAGAL: Pelanggan dengan ID " + id + " tidak ditemukan.";
        }

        // Cek duplikat nomor HP (kecuali milik pelanggan itu sendiri)
        for (Pelanggan p : daftarPelanggan) {
            if (p.getNoHp().equals(noHp) && !p.getId().equals(id)) {
                return "GAGAL: Nomor HP sudah digunakan pelanggan lain.";
            }
        }

        target.setNama(nama);
        target.setNoHp(noHp);
        target.setAlamat(alamat);
        return "SUKSES: Data pelanggan " + nama + " berhasil diupdate.";
    }

    // ===== MODUL 7 - COLLECTION: remove() =====
    // Hapus pelanggan berdasarkan ID
    public String hapus(String id) {
        Pelanggan target = cariById(id);
        if (target == null) {
            return "GAGAL: Pelanggan dengan ID " + id + " tidak ditemukan.";
        }
        daftarPelanggan.remove(target);
        return "SUKSES: Pelanggan " + target.getNama() + " berhasil dihapus.";
    }

    // ===== MODUL 7 - COLLECTION: isEmpty() =====
    public boolean isEmpty() {
        return daftarPelanggan.isEmpty();
    }
}