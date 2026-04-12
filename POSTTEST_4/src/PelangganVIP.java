public class PelangganVIP extends Pelanggan {

    private double diskon;

    public PelangganVIP(String id, String nama, String alamat, String noHp, double diskon) {
        super(id, nama, alamat, noHp);
        this.diskon = diskon;
    }

    public double getDiskon() { return diskon; }

    // ===== OVERRIDE #1 =====
    @Override
    public void tampilkan() {
        super.tampilkan();
        UI.baris("Status", "VIP");
        UI.baris("Diskon", (int) diskon + "%");
    }

    @Override
    public void tampilkanRingkas(int nomor) {
        System.out.printf("  %-4d  %-18s  %-6s  %s%n", nomor, nama, "VIP", (int) diskon + "%");
    }

    // ===== OVERRIDE #2 =====
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
}