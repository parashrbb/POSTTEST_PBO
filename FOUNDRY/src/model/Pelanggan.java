package model;

// ===== MODUL 6 - ABSTRACT CLASS =====
// Pelanggan tidak bisa diinstansiasi langsung karena setiap tipe
// pelanggan punya perilaku berbeda (Biasa vs VIP)
public abstract class Pelanggan {

    // ===== MODUL 3 - ENCAPSULATION =====
    // Private: hanya bisa diakses dari dalam class ini
    private String id;
    private String noHp;
    private String alamat;

    // Protected: bisa diakses child class langsung
    protected String nama;

    // ===== MODUL 2 - CONSTRUCTOR =====
    public Pelanggan(String id, String nama, String noHp, String alamat) {
        this.id     = id;
        this.nama   = nama;
        this.noHp   = noHp;
        this.alamat = alamat;
    }

    // ===== MODUL 3 - GETTER & SETTER =====
    public String getId()     { return id; }
    public String getNama()   { return nama; }
    public String getNoHp()   { return noHp; }
    public String getAlamat() { return alamat; }

    public void setNama(String nama)     { this.nama   = nama; }
    public void setNoHp(String noHp)     { this.noHp   = noHp; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    // ===== MODUL 6 - ABSTRACT METHOD =====
    // Wajib diimplementasikan child class karena:
    // - PelangganBiasa: total = harga x berat (tanpa diskon)
    // - PelangganVIP  : total = harga x berat - 10%
    public abstract double hitungTotal(double hargaPerKg, double berat);

    // ===== MODUL 6 - ABSTRACT METHOD =====
    // Wajib diimplementasikan child class karena tampilan tiap tipe berbeda
    public abstract String getStatus();
}