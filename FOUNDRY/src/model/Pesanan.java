package model;

// ===== MODUL 2 - CLASS =====
public class Pesanan {

    // ===== MODUL 3 - ENCAPSULATION =====
    private String  id;
    private String  namaPelanggan;
    private String  statusPelanggan; // "Biasa" atau "VIP"
    private String  namaLayanan;
    private double  hargaPerKg;
    private double  berat;
    private boolean selesai;
    private double  totalHarga;

    // ===== MODUL 2 - CONSTRUCTOR =====
    public Pesanan(String id, String namaPelanggan, String statusPelanggan,
                   String namaLayanan, double hargaPerKg, double berat,
                   boolean selesai, double totalHarga) {
        this.id              = id;
        this.namaPelanggan   = namaPelanggan;
        this.statusPelanggan = statusPelanggan;
        this.namaLayanan     = namaLayanan;
        this.hargaPerKg      = hargaPerKg;
        this.berat           = berat;
        this.selesai         = selesai;
        this.totalHarga      = totalHarga;
    }

    // ===== MODUL 3 - GETTER & SETTER =====
    public String  getId()              { return id; }
    public String  getNamaPelanggan()   { return namaPelanggan; }
    public String  getStatusPelanggan() { return statusPelanggan; }
    public String  getNamaLayanan()     { return namaLayanan; }
    public double  getHargaPerKg()      { return hargaPerKg; }
    public double  getBerat()           { return berat; }
    public boolean isSelesai()          { return selesai; }
    public double  getTotalHarga()      { return totalHarga; }
    public String  getStatusKerjaan()   { return selesai ? "Selesai" : "Belum Selesai"; }

    public void setSelesai(boolean selesai) { this.selesai = selesai; }

    // ===== MODUL 5 - METHOD OVERLOADING =====
    // getInfo() versi 1 — ringkas untuk tampil di tabel
    public String getInfo() {
        return "ID: " + id +
                " | Pelanggan: " + namaPelanggan +
                " | Layanan: " + namaLayanan +
                " | Berat: " + berat + " kg" +
                " | Total: Rp " + (long) totalHarga +
                " | Status: " + getStatusKerjaan();
    }

    // ===== MODUL 5 - METHOD OVERLOADING =====
    // getInfo(boolean) versi 2 — tampil detail termasuk harga per kg
    public String getInfo(boolean tampilDetail) {
        String info = getInfo();
        if (tampilDetail) {
            info += "\n  Harga/kg: Rp " + (long) hargaPerKg +
                    "\n  Status Pelanggan: " + statusPelanggan;
        }
        return info;
    }
}