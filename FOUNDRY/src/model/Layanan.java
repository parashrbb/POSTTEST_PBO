package model;

// ===== MODUL 2 - CLASS =====
public class Layanan {

    // ===== MODUL 3 - ENCAPSULATION =====
    private String id;
    private String nama;
    private double hargaPerKg;

    // ===== MODUL 2 - CONSTRUCTOR =====
    public Layanan(String id, String nama, double hargaPerKg) {
        this.id         = id;
        this.nama       = nama;
        this.hargaPerKg = hargaPerKg;
    }

    // ===== MODUL 3 - GETTER =====
    public String getId()         { return id; }
    public String getNama()       { return nama; }
    public double getHargaPerKg() { return hargaPerKg; }
}