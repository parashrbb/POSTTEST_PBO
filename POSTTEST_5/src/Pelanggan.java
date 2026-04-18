import java.util.ArrayList;
import java.util.Scanner;

public abstract class Pelanggan {

    private String id;
    protected String nama;
    private String alamat;
    private String noHp;

    public Pelanggan(String id, String nama, String alamat, String noHp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    public String getId()   { return id; }
    public String getNama() { return nama; }

    public void setNama(String nama)     { this.nama = nama; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setNoHp(String noHp)     { this.noHp = noHp; }

    // ===== ABSTRACT METHOD #1 =====
    // Wajib diimplementasikan child class karena tampilan tiap tipe berbeda
    public abstract void tampilkan();

    // ===== ABSTRACT METHOD #2 =====
    // Wajib diimplementasikan child class karena cara hitung berbeda:
    // PelangganBiasa = harga x berat, PelangganVIP = harga x berat - diskon
    public abstract double hitungTotal(double hargaPerKg, double berat);

    // Method konkrit — tampilan ringkas baris tabel, di-override child class
    public void tampilkanRingkas(int nomor) {
        System.out.printf("  %-4d  %-18s%n", nomor, nama);
    }

    // Method konkrit — data dasar, dipanggil via super.tampilkan() dari child class
    protected void tampilkanDasar() {
        UI.baris("ID", id);
        UI.baris("Nama", nama);
        UI.baris("Alamat", alamat);
        UI.baris("No HP", noHp);
    }

    // ================= CRUD =================

    public static void tambah(ArrayList<Pelanggan> daftarPelanggan, Scanner input) {

        input.nextLine();

        System.out.println();
        UI.sectionOpen("TAMBAH PELANGGAN");

        UI.prompt("ID");
        String id = input.nextLine();

        for (Pelanggan p : daftarPelanggan) {
            if (p.getId().equals(id)) {
                UI.gagal("ID sudah digunakan!");
                return;
            }
        }

        UI.prompt("Nama");
        String nama = input.nextLine();

        UI.prompt("Alamat");
        String alamat = input.nextLine();

        UI.prompt("No HP");
        String noHp = input.nextLine();

        System.out.println();
        UI.info("Jenis: [1] Biasa   [2] VIP");
        UI.prompt("Pilih");
        int jenis = input.nextInt();
        input.nextLine();

        Pelanggan p;
        if (jenis == 2) {
            UI.prompt("Diskon (%)");
            double diskon = input.nextDouble();
            input.nextLine();
            p = new PelangganVIP(id, nama, alamat, noHp, diskon);
        } else {
            p = new PelangganBiasa(id, nama, alamat, noHp);
        }

        daftarPelanggan.add(p);
        UI.sectionClose();
        UI.sukses("Pelanggan berhasil ditambahkan");
    }

    public static void lihat(ArrayList<Pelanggan> daftarPelanggan, Scanner input) {

        if (daftarPelanggan.isEmpty()) {
            UI.gagal("Belum ada pelanggan terdaftar.");
            return;
        }

        System.out.println();
        UI.sectionOpen("DAFTAR PELANGGAN");
        System.out.printf("  %-4s  %-18s  %-6s  %s%n", "No.", "Nama", "Status", "Diskon");
        UI.garisSimple();

        // ===== POLYMORPHISM - DYNAMIC (OVERRIDE) =====
        // tampilkanRingkas() berbeda tiap tipe: Biasa vs VIP
        for (int i = 0; i < daftarPelanggan.size(); i++) {
            daftarPelanggan.get(i).tampilkanRingkas(i + 1);
        }

        UI.garisSimple();
        UI.info("Total: " + daftarPelanggan.size() + " pelanggan");
        UI.sectionClose();

        System.out.println();
        UI.prompt("Pilih nomor untuk detail (0 = kembali)");
        int nomor = input.nextInt();

        if (nomor == 0) return;
        if (nomor < 1 || nomor > daftarPelanggan.size()) {
            UI.gagal("Nomor tidak valid!");
            return;
        }

        System.out.println();
        UI.sectionOpen("DETAIL PELANGGAN");
        // ===== ABSTRACT METHOD #1 dipanggil =====
        // Java jalankan tampilkan() milik PelangganBiasa atau PelangganVIP
        daftarPelanggan.get(nomor - 1).tampilkan();
        UI.sectionClose();
    }

    public static void update(ArrayList<Pelanggan> daftarPelanggan, Scanner input) {

        if (daftarPelanggan.isEmpty()) {
            UI.gagal("Belum ada pelanggan terdaftar.");
            return;
        }

        tampilTabel(daftarPelanggan);
        input.nextLine();

        System.out.println();
        UI.sectionOpen("EDIT PELANGGAN");
        UI.prompt("Masukkan ID");
        String id = input.nextLine();

        for (Pelanggan p : daftarPelanggan) {
            if (p.getId().equals(id)) {
                UI.prompt("Nama baru");
                String namaBaru = input.nextLine();
                UI.prompt("Alamat baru");
                String alamatBaru = input.nextLine();
                UI.prompt("No HP baru");
                String noHpBaru = input.nextLine();
                p.setNama(namaBaru);
                p.setAlamat(alamatBaru);
                p.setNoHp(noHpBaru);
                UI.sectionClose();
                UI.sukses("Data berhasil diupdate");
                return;
            }
        }

        UI.gagal("Data tidak ditemukan");
    }

    public static void hapus(ArrayList<Pelanggan> daftarPelanggan, Scanner input) {

        if (daftarPelanggan.isEmpty()) {
            UI.gagal("Belum ada pelanggan terdaftar.");
            return;
        }

        tampilTabel(daftarPelanggan);
        input.nextLine();

        System.out.println();
        UI.sectionOpen("HAPUS PELANGGAN");
        UI.prompt("Masukkan ID");
        String id = input.nextLine();

        for (Pelanggan p : daftarPelanggan) {
            if (p.getId().equals(id)) {
                daftarPelanggan.remove(p);
                UI.sectionClose();
                UI.sukses("Data berhasil dihapus");
                return;
            }
        }

        UI.gagal("Data tidak ditemukan");
    }

    // Helper: tampilkan tabel ringkas tanpa prompt detail
    private static void tampilTabel(ArrayList<Pelanggan> daftarPelanggan) {
        System.out.println();
        UI.sectionOpen("DAFTAR PELANGGAN");
        System.out.printf("  %-4s  %-18s  %-6s  %s%n", "No.", "Nama", "Status", "Diskon");
        UI.garisSimple();
        for (int i = 0; i < daftarPelanggan.size(); i++) {
            daftarPelanggan.get(i).tampilkanRingkas(i + 1);
        }
        UI.garisSimple();
        UI.sectionClose();
    }
}