package model;
import interfaces.Transaksi;

// ===== MODUL 4 - INHERITANCE =====
// PelangganBiasa adalah turunan dari Pelanggan (relasi is-a)
// ===== MODUL 6 - IMPLEMENT INTERFACE =====
public class PelangganBiasa extends Pelanggan implements Transaksi {

    // ===== MODUL 2 - CONSTRUCTOR =====
    // Memanggil constructor superclass via super()
    public PelangganBiasa(String id, String nama, String noHp, String alamat) {
        // ===== MODUL 4 - KEYWORD SUPER =====
        super(id, nama, noHp, alamat);
    }

    // ===== MODUL 5 - METHOD OVERRIDING =====
    // Override abstract method dari Pelanggan
    // Pelanggan biasa tidak mendapat diskon
    @Override
    public double hitungTotal(double hargaPerKg, double berat) {
        return hargaPerKg * berat;
    }

    // ===== MODUL 5 - METHOD OVERRIDING =====
    @Override
    public String getStatus() {
        return "Biasa";
    }

    // ===== MODUL 6 - IMPLEMENT INTERFACE Transaksi =====
    @Override
    public String getKategori() {
        return "Biasa";
    }

    // ===== MODUL 6 - IMPLEMENT INTERFACE Transaksi =====
    // Hitung tagihan tanpa diskon
    @Override
    public double hitungTagihan(double hargaPerKg, double berat) {
        return hargaPerKg * berat;
    }
}