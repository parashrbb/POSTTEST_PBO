package model;
import interfaces.Transaksi;

// ===== MODUL 4 - INHERITANCE =====
// PelangganVIP adalah turunan dari Pelanggan (relasi is-a)
// ===== MODUL 6 - IMPLEMENT INTERFACE =====
public class PelangganVIP extends Pelanggan implements Transaksi {

    // ===== MODUL 3 - ENCAPSULATION =====
    private static final double DISKON = 10.0; // diskon fix 10%

    // ===== MODUL 2 - CONSTRUCTOR =====
    public PelangganVIP(String id, String nama, String noHp, String alamat) {
        // ===== MODUL 4 - KEYWORD SUPER =====
        super(id, nama, noHp, alamat);
    }

    // ===== MODUL 3 - GETTER =====
    public double getDiskon() { return DISKON; }

    // ===== MODUL 5 - METHOD OVERRIDING =====
    // Override abstract method dari Pelanggan
    // Pelanggan VIP mendapat potongan diskon 10% otomatis
    @Override
    public double hitungTotal(double hargaPerKg, double berat) {
        double subtotal = hargaPerKg * berat;
        double potongan = subtotal * (DISKON / 100);
        return subtotal - potongan;
    }

    // ===== MODUL 5 - METHOD OVERRIDING =====
    @Override
    public String getStatus() {
        return "VIP (diskon " + (int) DISKON + "%)";
    }

    // ===== MODUL 6 - IMPLEMENT INTERFACE Transaksi =====
    @Override
    public String getKategori() {
        return "VIP";
    }

    // ===== MODUL 6 - IMPLEMENT INTERFACE Transaksi =====
    // Hitung tagihan dengan diskon 10%
    @Override
    public double hitungTagihan(double hargaPerKg, double berat) {
        double subtotal = hargaPerKg * berat;
        double potongan = subtotal * (DISKON / 100);
        return subtotal - potongan;
    }
}