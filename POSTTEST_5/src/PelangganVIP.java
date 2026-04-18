public class PelangganVIP extends Pelanggan implements Transaksi {

    private double diskon;

    public PelangganVIP(String id, String nama, String alamat, String noHp, double diskon) {
        super(id, nama, alamat, noHp);
        this.diskon = diskon;
    }

    public double getDiskon() { return diskon; }

    // ===== ABSTRACT METHOD #1 - implementasi =====
    @Override
    public void tampilkan() {
        tampilkanDasar();
        UI.baris("Status", "VIP");
        UI.baris("Diskon", (int) diskon + "%");
    }

    @Override
    public void tampilkanRingkas(int nomor) {
        System.out.printf("  %-4d  %-18s  %-6s  %s%n", nomor, nama, "VIP", (int) diskon + "%");
    }

    // ===== ABSTRACT METHOD #2 - implementasi =====
    // Pelanggan VIP mendapat potongan diskon otomatis
    @Override
    public double hitungTotal(double hargaPerKg, double berat) {
        double subtotal = hargaPerKg * berat;
        double potongan = subtotal * (diskon / 100);
        double total    = subtotal - potongan;
        UI.baris("Harga/kg", UI.uang(hargaPerKg));
        UI.baris("Berat", berat + " kg");
        UI.baris("Subtotal", UI.uang(subtotal));
        UI.baris("Diskon " + (int) diskon + "%", "- " + UI.uang(potongan));
        UI.baris("Total", UI.uang(total) + "  (setelah diskon)");
        return total;
    }

    // ===== INTERFACE Transaksi - METHOD #1 =====
    @Override
    public String getKategori() {
        return "VIP " + (int) diskon + "%";
    }

    // ===== INTERFACE Transaksi - METHOD #2 =====
    // Cetak ringkasan tagihan untuk pelanggan VIP dengan diskon
    @Override
    public void prosesTagihan(double hargaPerKg, double berat) {
        double subtotal = hargaPerKg * berat;
        double potongan = subtotal * (diskon / 100);
        double total    = subtotal - potongan;
        UI.info("Kategori  : " + getKategori());
        UI.info("Tagihan   : " + UI.uang(total) + " (hemat " + UI.uang(potongan) + ")");
    }
}