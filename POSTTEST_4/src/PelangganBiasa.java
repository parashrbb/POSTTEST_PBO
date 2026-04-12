public class PelangganBiasa extends Pelanggan {

    public PelangganBiasa(String id, String nama, String alamat, String noHp) {
        super(id, nama, alamat, noHp);
    }

    // ===== OVERRIDE #1 =====
    @Override
    public void tampilkan() {
        super.tampilkan();
        UI.baris("Status", "Biasa");
        UI.baris("Diskon", "Tidak ada");
    }

    @Override
    public void tampilkanRingkas(int nomor) {
        System.out.printf("  %-4d  %-18s  %-6s  %s%n", nomor, nama, "Biasa", "-");
    }

    // ===== OVERRIDE #2 =====
    @Override
    public double hitungTotal(double hargaPerKg, double berat) {
        double total = hargaPerKg * berat;
        UI.baris("Harga/kg", UI.uang(hargaPerKg));
        UI.baris("Berat", berat + " kg");
        UI.baris("Total", UI.uang(total) + "  (tanpa diskon)");
        return total;
    }
}