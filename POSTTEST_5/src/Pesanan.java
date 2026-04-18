import java.util.ArrayList;
import java.util.Scanner;

public class Pesanan {

    private String id;
    private String namaPelanggan;
    private String namaLayanan;
    private double berat;
    private String status;

    public Pesanan(String id, String namaPelanggan, String namaLayanan, double berat, String status) {
        this.id = id;
        this.namaPelanggan = namaPelanggan;
        this.namaLayanan = namaLayanan;
        this.berat = berat;
        this.status = status;
    }

    public String getId()             { return id; }
    public String getNamaPelanggan()  { return namaPelanggan; }
    public String getNamaLayanan()    { return namaLayanan; }
    public double getBerat()          { return berat; }
    public String getStatus()         { return status; }

    public void setNamaPelanggan(String v) { this.namaPelanggan = v; }
    public void setNamaLayanan(String v)   { this.namaLayanan = v; }
    public void setBerat(double v)         { this.berat = v; }
    public void setStatus(String v)        { this.status = v; }

    // ===== OVERLOADING #1 =====
    // tampilkan() — info lengkap
    public void tampilkan() {
        UI.baris("ID Pesanan", id);
        UI.baris("Pelanggan", namaPelanggan);
        UI.baris("Layanan", namaLayanan);
        UI.baris("Berat", berat + " kg");
        UI.baris("Status", status);
    }

    // ===== OVERLOADING #1 (versi 2) =====
    // tampilkan(boolean, double) — info lengkap + estimasi harga
    public void tampilkan(boolean tampilTotal, double hargaPerKg) {
        tampilkan();
        if (tampilTotal) {
            UI.garisSimple();
            UI.baris("Est. Harga", UI.uang(hitungTotal(hargaPerKg)));
        }
    }

    // ===== OVERLOADING #1 (versi 3) =====
    // tampilkan(String) — satu baris ringkas untuk tabel
    public void tampilkan(String mode) {
        System.out.printf("  %-8s  %-14s  %-14s  %-7s  %s%n",
                id, namaPelanggan, namaLayanan, berat + " kg", status);
    }

    // ===== OVERLOADING #2 =====
    // hitungTotal(double) — harga x berat
    public double hitungTotal(double hargaPerKg) {
        return hargaPerKg * berat;
    }

    // ===== OVERLOADING #2 (versi 2) =====
    // hitungTotal(double, double) — + biaya tambahan
    public double hitungTotal(double hargaPerKg, double biayaTambahan) {
        double subtotal = hargaPerKg * berat;
        double total = subtotal + biayaTambahan;
        UI.baris("Subtotal", UI.uang(subtotal));
        UI.baris("Biaya tambahan", UI.uang(biayaTambahan));
        UI.garisSimple();
        UI.baris("Total", UI.uang(total));
        return total;
    }

    // ===== OVERLOADING #2 (versi 3) =====
    // hitungTotal(double, double, double) — + biaya tambahan + diskon
    public double hitungTotal(double hargaPerKg, double biayaTambahan, double diskonPersen) {
        double subtotal = hargaPerKg * berat;
        double potongan = subtotal * (diskonPersen / 100);
        double total = (subtotal - potongan) + biayaTambahan;
        UI.baris("Subtotal", UI.uang(subtotal));
        UI.baris("Diskon " + (int) diskonPersen + "%", "- " + UI.uang(potongan));
        UI.baris("Biaya tambahan", UI.uang(biayaTambahan));
        UI.garisSimple();
        UI.baris("Total", UI.uang(total));
        return total;
    }

    // ================= CRUD =================

    public static void tambah(ArrayList<Pesanan> daftarPesanan,
                              ArrayList<Pelanggan> daftarPelanggan,
                              ArrayList<Layanan> daftarLayanan,
                              Scanner input) {

        input.nextLine();

        System.out.println();
        UI.sectionOpen("TAMBAH PESANAN");
        UI.prompt("ID Pesanan");
        String id = input.nextLine();

        for (Pesanan p : daftarPesanan) {
            if (p.getId().equals(id)) {
                UI.gagal("ID sudah digunakan!");
                return;
            }
        }

        if (daftarPelanggan.isEmpty()) {
            UI.gagal("Belum ada pelanggan. Tambah pelanggan terlebih dahulu!");
            return;
        }

        // Pilih pelanggan
        System.out.println();
        UI.sectionOpen("PILIH PELANGGAN");
        System.out.printf("  %-4s  %-18s  %-6s  %s%n", "No.", "Nama", "Status", "Diskon");
        UI.garisSimple();
        for (int i = 0; i < daftarPelanggan.size(); i++) {
            daftarPelanggan.get(i).tampilkanRingkas(i + 1);
        }
        UI.garisSimple();
        UI.sectionClose();
        UI.prompt("Pilih nomor");
        int noPelanggan = input.nextInt();
        input.nextLine();

        if (noPelanggan < 1 || noPelanggan > daftarPelanggan.size()) {
            UI.gagal("Nomor tidak valid!");
            return;
        }

        if (daftarLayanan.isEmpty()) {
            UI.gagal("Belum ada layanan. Tambah layanan terlebih dahulu!");
            return;
        }

        // Pilih layanan
        System.out.println();
        UI.sectionOpen("PILIH LAYANAN");
        System.out.printf("  %-4s  %-6s  %-18s  %s%n", "No.", "ID", "Nama", "Harga/kg");
        UI.garisSimple();
        for (int i = 0; i < daftarLayanan.size(); i++) {
            Layanan l = daftarLayanan.get(i);
            System.out.printf("  %-4d  %-6s  %-18s  %s%n",
                    i + 1, l.getId(), l.getNama(), UI.uang(l.getHarga()));
        }
        UI.garisSimple();
        UI.sectionClose();
        UI.prompt("Pilih nomor");
        int noLayanan = input.nextInt();
        input.nextLine();

        if (noLayanan < 1 || noLayanan > daftarLayanan.size()) {
            UI.gagal("Nomor tidak valid!");
            return;
        }

        System.out.println();
        UI.prompt("Berat (kg)");
        double berat = input.nextDouble();

        String namaPelanggan = daftarPelanggan.get(noPelanggan - 1).nama;
        String namaLayanan   = daftarLayanan.get(noLayanan - 1).getNama();

        daftarPesanan.add(new Pesanan(id, namaPelanggan, namaLayanan, berat, "Proses"));
        UI.sukses("Pesanan berhasil ditambahkan!");
    }

    public static void lihatRingkas(ArrayList<Pesanan> daftarPesanan) {

        if (daftarPesanan.isEmpty()) {
            UI.gagal("Belum ada pesanan terdaftar.");
            return;
        }

        System.out.println();
        UI.sectionOpen("DAFTAR PESANAN");
        System.out.printf("  %-8s  %-14s  %-14s  %-7s  %s%n",
                "ID", "Pelanggan", "Layanan", "Berat", "Status");
        UI.garisSimple();

        // ===== OVERLOADING #1 versi 3 =====
        for (Pesanan p : daftarPesanan) {
            p.tampilkan("ringkas");
        }

        UI.garisSimple();
        UI.info("Total: " + daftarPesanan.size() + " pesanan");
        UI.sectionClose();
    }

    public static void lihatDenganHarga(ArrayList<Pesanan> daftarPesanan,
                                        ArrayList<Layanan> daftarLayanan) {

        if (daftarPesanan.isEmpty()) {
            UI.gagal("Belum ada pesanan terdaftar.");
            return;
        }

        System.out.println();
        UI.sectionOpen("PESANAN + ESTIMASI HARGA");

        // ===== OVERLOADING #1 versi 2 =====
        for (int i = 0; i < daftarPesanan.size(); i++) {
            Pesanan p = daftarPesanan.get(i);
            double harga = cariHargaLayanan(p, daftarLayanan);
            if (i > 0) UI.garisSimple();
            p.tampilkan(true, harga);
        }

        UI.sectionClose();
    }

    public static void update(ArrayList<Pesanan> daftarPesanan,
                              ArrayList<Pelanggan> daftarPelanggan,
                              ArrayList<Layanan> daftarLayanan,
                              Scanner input) {

        if (daftarPesanan.isEmpty()) {
            UI.gagal("Belum ada pesanan terdaftar.");
            return;
        }

        lihatRingkas(daftarPesanan);

        input.nextLine();

        System.out.println();
        UI.sectionOpen("EDIT PESANAN");
        UI.prompt("Masukkan ID Pesanan");
        String id = input.nextLine();

        for (Pesanan p : daftarPesanan) {
            if (p.getId().equals(id)) {

                // Pilih pelanggan baru
                System.out.println();
                UI.sectionOpen("PILIH PELANGGAN BARU");
                System.out.printf("  %-4s  %-18s  %-6s  %s%n", "No.", "Nama", "Status", "Diskon");
                UI.garisSimple();
                for (int i = 0; i < daftarPelanggan.size(); i++) {
                    daftarPelanggan.get(i).tampilkanRingkas(i + 1);
                }
                UI.garisSimple();
                UI.sectionClose();
                UI.prompt("Pilih nomor");
                int noPelanggan = input.nextInt();
                input.nextLine();
                if (noPelanggan < 1 || noPelanggan > daftarPelanggan.size()) {
                    UI.gagal("Nomor tidak valid!");
                    return;
                }

                // Pilih layanan baru
                System.out.println();
                UI.sectionOpen("PILIH LAYANAN BARU");
                System.out.printf("  %-4s  %-6s  %-18s  %s%n", "No.", "ID", "Nama", "Harga/kg");
                UI.garisSimple();
                for (int i = 0; i < daftarLayanan.size(); i++) {
                    Layanan l = daftarLayanan.get(i);
                    System.out.printf("  %-4d  %-6s  %-18s  %s%n",
                            i + 1, l.getId(), l.getNama(), UI.uang(l.getHarga()));
                }
                UI.garisSimple();
                UI.sectionClose();
                UI.prompt("Pilih nomor");
                int noLayanan = input.nextInt();
                input.nextLine();
                if (noLayanan < 1 || noLayanan > daftarLayanan.size()) {
                    UI.gagal("Nomor tidak valid!");
                    return;
                }

                System.out.println();
                UI.prompt("Berat baru (kg)");
                double beratBaru = input.nextDouble();
                input.nextLine();

                UI.prompt("Status baru");
                String statusBaru = input.nextLine();

                p.setNamaPelanggan(daftarPelanggan.get(noPelanggan - 1).nama);
                p.setNamaLayanan(daftarLayanan.get(noLayanan - 1).getNama());
                p.setBerat(beratBaru);
                p.setStatus(statusBaru);

                UI.sukses("Pesanan berhasil diupdate!");
                return;
            }
        }

        UI.gagal("Pesanan tidak ditemukan!");
    }

    public static void hapus(ArrayList<Pesanan> daftarPesanan, Scanner input) {

        if (daftarPesanan.isEmpty()) {
            UI.gagal("Belum ada pesanan terdaftar.");
            return;
        }

        lihatRingkas(daftarPesanan);

        input.nextLine();

        System.out.println();
        UI.sectionOpen("HAPUS PESANAN");
        UI.prompt("Masukkan ID Pesanan");
        String id = input.nextLine();

        for (Pesanan p : daftarPesanan) {
            if (p.getId().equals(id)) {
                daftarPesanan.remove(p);
                UI.sectionClose();
                UI.sukses("Pesanan berhasil dihapus!");
                return;
            }
        }

        UI.gagal("Pesanan tidak ditemukan!");
    }

    public static void hitungTagihan(ArrayList<Pesanan> daftarPesanan,
                                     ArrayList<Pelanggan> daftarPelanggan,
                                     ArrayList<Layanan> daftarLayanan,
                                     Scanner input) {

        if (daftarPesanan.isEmpty()) {
            UI.gagal("Belum ada pesanan terdaftar.");
            return;
        }

        lihatRingkas(daftarPesanan);

        input.nextLine();

        System.out.println();
        UI.sectionOpen("HITUNG TAGIHAN");
        UI.prompt("Pilih nomor pesanan");
        int noPesanan = input.nextInt();
        input.nextLine();

        if (noPesanan < 1 || noPesanan > daftarPesanan.size()) {
            UI.gagal("Nomor tidak valid!");
            return;
        }

        Pesanan target = daftarPesanan.get(noPesanan - 1);
        double hargaPerKg = cariHargaLayanan(target, daftarLayanan);

        if (hargaPerKg == 0) {
            UI.gagal("Layanan tidak ditemukan!");
            return;
        }

        System.out.println();
        UI.info("Jenis Tagihan:");
        UI.info("  [1] Harga normal");
        UI.info("  [2] + Biaya tambahan");
        UI.info("  [3] + Biaya tambahan + diskon");
        UI.prompt("Pilih");
        int jenis = input.nextInt();
        input.nextLine();

        System.out.println();
        UI.sectionOpen("RINCIAN TAGIHAN");
        UI.baris("ID Pesanan", target.getId());
        UI.baris("Pelanggan", target.getNamaPelanggan());
        UI.baris("Layanan", target.getNamaLayanan());
        UI.garisSimple();

        if (jenis == 1) {
            // ===== OVERLOADING #2 versi 1 =====
            double total = target.hitungTotal(hargaPerKg);
            UI.baris("Harga/kg", UI.uang(hargaPerKg));
            UI.baris("Berat", target.getBerat() + " kg");
            UI.garisSimple();
            UI.baris("Total", UI.uang(total));

            // ===== ABSTRACT METHOD #2 + INTERFACE Transaksi =====
            // Cari pelanggan lalu panggil:
            // - hitungTotal() dari abstract method (Biasa vs VIP beda hasil)
            // - prosesTagihan() dari interface Transaksi (ringkasan tagihan)
            for (Pelanggan p : daftarPelanggan) {
                if (p.nama.equalsIgnoreCase(target.getNamaPelanggan())) {
                    System.out.println();
                    UI.info("--- Kalkulasi berdasarkan tipe pelanggan ---");
                    UI.garisSimple();
                    p.hitungTotal(hargaPerKg, target.getBerat());
                    System.out.println();
                    // Panggil prosesTagihan() via interface Transaksi
                    if (p instanceof Transaksi) {
                        Transaksi t = (Transaksi) p;
                        t.prosesTagihan(hargaPerKg, target.getBerat());
                    }
                    break;
                }
            }

        } else if (jenis == 2) {
            // ===== OVERLOADING #2 versi 2 =====
            UI.prompt("Biaya tambahan (Rp)");
            double biayaTambahan = input.nextDouble();
            UI.baris("Harga/kg", UI.uang(hargaPerKg));
            UI.baris("Berat", target.getBerat() + " kg");
            target.hitungTotal(hargaPerKg, biayaTambahan);

        } else if (jenis == 3) {
            // ===== OVERLOADING #2 versi 3 =====
            UI.prompt("Biaya tambahan (Rp)");
            double biayaTambahan = input.nextDouble();
            UI.prompt("Diskon (%)");
            double diskon = input.nextDouble();
            UI.baris("Harga/kg", UI.uang(hargaPerKg));
            UI.baris("Berat", target.getBerat() + " kg");
            target.hitungTotal(hargaPerKg, biayaTambahan, diskon);

        } else {
            UI.gagal("Pilihan tidak valid!");
        }

        UI.sectionClose();
    }

    private static double cariHargaLayanan(Pesanan pesanan, ArrayList<Layanan> daftarLayanan) {
        for (Layanan l : daftarLayanan) {
            if (l.getNama().equalsIgnoreCase(pesanan.getNamaLayanan())) {
                return l.getHarga();
            }
        }
        return 0;
    }
}