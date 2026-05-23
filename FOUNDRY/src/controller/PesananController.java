package controller;

import interfaces.Transaksi;
import model.Layanan;
import model.Pelanggan;
import model.Pesanan;

import java.util.ArrayList;
import java.util.List;

// ===== MODUL 2 - CLASS =====
public class PesananController {

    // ===== MODUL 7 - JAVA COLLECTION =====
    private ArrayList<Pesanan>  daftarPesanan  = new ArrayList<>();
    private ArrayList<Layanan>  daftarLayanan  = new ArrayList<>();
    private int counterID = 1;

    // ===== MODUL 2 - CONSTRUCTOR =====
    // Layanan hardcode — owner sudah punya layanan tetap
    public PesananController() {
        daftarLayanan.add(new Layanan("L001", "Cuci Kering",           5000));
        daftarLayanan.add(new Layanan("L002", "Cuci Setrika",          8000));
        daftarLayanan.add(new Layanan("L003", "Express 1 Hari",       12000));
        daftarLayanan.add(new Layanan("L005", "Cuci Selimut / Bed Cover", 20000));
        daftarLayanan.add(new Layanan("L006", "Dry Cleaning",         25000));
        daftarLayanan.add(new Layanan("L007", "Setrika Saja",          4000));
        daftarLayanan.add(new Layanan("L008", "Cuci + Parfum Premium", 10000));
    }

    // ===== GENERATE ID OTOMATIS =====
    private String generateId() {
        return "PS" + String.format("%03d", counterID++);
    }

    // ===== MODUL 7 - COLLECTION: get() =====
    public List<Layanan> getAllLayanan() {
        return daftarLayanan;
    }

    // ===== MODUL 7 - COLLECTION: add() =====
    // Tambah pesanan baru
    // Hitung total harga otomatis berdasarkan tipe pelanggan (Biasa/VIP)
    public String tambah(Pelanggan pelanggan, String idLayanan, double berat) {

        if (pelanggan == null) {
            return "GAGAL: Pelanggan tidak ditemukan.";
        }

        // Cari layanan
        Layanan layanan = cariLayananById(idLayanan);
        if (layanan == null) {
            return "GAGAL: Layanan tidak ditemukan.";
        }

        // ===== MODUL 6 - ABSTRACT METHOD =====
        // hitungTotal() dipanggil via referensi abstract class Pelanggan
        // Java otomatis jalankan versi PelangganBiasa atau PelangganVIP
        double totalHarga = pelanggan.hitungTotal(layanan.getHargaPerKg(), berat);

        // ===== MODUL 6 - INTERFACE TRANSAKSI =====
        // Panggil hitungTagihan() via interface untuk verifikasi hasil
        if (pelanggan instanceof Transaksi) {
            Transaksi t = (Transaksi) pelanggan;
            totalHarga = t.hitungTagihan(layanan.getHargaPerKg(), berat);
        }

        String id = generateId();
        Pesanan baru = new Pesanan(
                id,
                pelanggan.getNama(),
                pelanggan.getStatus(),
                layanan.getNama(),
                layanan.getHargaPerKg(),
                berat,
                false,
                totalHarga
        );

        daftarPesanan.add(baru);
        return "SUKSES:" +
                "|id=" + id +
                "|pelanggan=" + pelanggan.getNama() +
                "|layanan=" + layanan.getNama() +
                "|berat=" + berat +
                "|total=" + (long) totalHarga +
                "|status=" + pelanggan.getStatus();
    }

    // ===== MODUL 7 - COLLECTION: get(), size() =====
    public List<Pesanan> getAll() {
        return daftarPesanan;
    }

    // Cari pesanan berdasarkan ID
    public Pesanan cariById(String id) {
        for (Pesanan p : daftarPesanan) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // ===== MODUL 7 - COLLECTION: iterasi + setter =====
    // Update status selesai/belum saja
    public String updateStatus(String id, boolean selesai) {
        Pesanan target = cariById(id);
        if (target == null) {
            return "GAGAL: Pesanan dengan ID " + id + " tidak ditemukan.";
        }
        target.setSelesai(selesai);
        String statusBaru = selesai ? "Selesai" : "Belum Selesai";
        return "SUKSES: Status pesanan " + id + " diubah menjadi " + statusBaru + ".";
    }

    // ===== MODUL 7 - COLLECTION: remove() =====
    public String hapus(String id) {
        Pesanan target = cariById(id);
        if (target == null) {
            return "GAGAL: Pesanan dengan ID " + id + " tidak ditemukan.";
        }
        daftarPesanan.remove(target);
        return "SUKSES: Pesanan " + id + " berhasil dihapus.";
    }

    // ===== MODUL 7 - COLLECTION: isEmpty() =====
    public boolean isEmpty() {
        return daftarPesanan.isEmpty();
    }

    // Helper: cari layanan berdasarkan ID
    private Layanan cariLayananById(String id) {
        for (Layanan l : daftarLayanan) {
            if (l.getId().equals(id)) {
                return l;
            }
        }
        return null;
    }
}