public class PelangganBiasa extends Pelanggan implements Transaksi {

    public PelangganBiasa(String id, String nama, String alamat, String noHp) {
        super(id, nama, alamat, noHp);
    }

    // ===== ABSTRACT METHOD #1 - implementasi =====
    @Override
    public void tampilkan() {
        tampilkanDasar();
        UI.baris("Status", "Biasa");
        UI.baris("Diskon", "Tidak ada");
    }

    @Override
    public void tampilkanRingkas(int nomor) {
        System.out.printf("  %-4d  %-18s  %-6s  %s%n", nomor, nama, "Biasa", "-");
    }

    // ===== ABSTRACT METHOD #2 - implementasi =====
    // Pelanggan biasa tidak mendapat diskon
    @Override
    public double hitungTotal(double hargaPerKg, double berat) {
        double total = hargaPerKg * berat;
        UI.baris("Harga/kg", UI.uang(hargaPerKg));
        UI.baris("Berat", berat + " kg");
        UI.baris("Total", UI.uang(total) + "  (tanpa diskon)");
        return total;
    }

    // ===== INTERFACE Transaksi - METHOD #1 =====
    @Override
    public String getKategori() {
        return "Biasa";
    }

    // ===== INTERFACE Transaksi - METHOD #2 =====
    // Cetak ringkasan tagihan untuk pelanggan biasa
    @Override
    public void prosesTagihan(double hargaPerKg, double berat) {
        double total = hargaPerKg * berat;
        UI.info("Kategori  : " + getKategori());
        UI.info("Tagihan   : " + UI.uang(total) + " (tanpa diskon)");
    }
}