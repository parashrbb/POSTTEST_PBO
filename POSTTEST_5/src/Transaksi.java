public interface Transaksi {

    // Mengembalikan kategori pelanggan sebagai String (contoh: "Biasa" atau "VIP")
    String getKategori();

    // Mencetak ringkasan tagihan berdasarkan harga per kg dan berat
    void prosesTagihan(double hargaPerKg, double berat);
}