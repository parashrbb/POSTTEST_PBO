package interfaces;

// ===== MODUL 6 - INTERFACE =====
// Kontrak yang wajib diimplementasikan oleh setiap tipe pelanggan
// Memastikan semua pelanggan bisa menghitung total dan mengembalikan kategorinya
public interface Transaksi {

    // Mengembalikan kategori pelanggan: "Biasa" atau "VIP"
    String getKategori();

    // Menghitung dan mengembalikan total tagihan berdasarkan harga/kg dan berat
    double hitungTagihan(double hargaPerKg, double berat);
}