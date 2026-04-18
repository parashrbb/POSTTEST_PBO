import java.util.ArrayList;
import java.util.Scanner;

public class Layanan {

    private String id;
    private String nama;
    private double harga;

    public Layanan(String id, String nama, double harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public String getId()    { return id; }
    public String getNama()  { return nama; }
    public double getHarga() { return harga; }

    public void setNama(String nama)   { this.nama = nama; }
    public void setHarga(double harga) { this.harga = harga; }

    public void tampilkan() {
        UI.baris("ID", id);
        UI.baris("Nama", nama);
        UI.baris("Harga/kg", UI.uang(harga));
    }

    // ================= CRUD =================

    public static void tambah(ArrayList<Layanan> daftarLayanan, Scanner input) {

        input.nextLine();

        System.out.println();
        UI.sectionOpen("TAMBAH LAYANAN");

        UI.prompt("ID");
        String id = input.nextLine();

        for (Layanan l : daftarLayanan) {
            if (l.getId().equals(id)) {
                UI.gagal("ID layanan sudah ada!");
                return;
            }
        }

        UI.prompt("Nama layanan");
        String nama = input.nextLine();

        UI.prompt("Harga/kg");
        double harga = input.nextDouble();

        daftarLayanan.add(new Layanan(id, nama, harga));
        UI.sectionClose();
        UI.sukses("Layanan berhasil ditambahkan");
    }

    public static void lihat(ArrayList<Layanan> daftarLayanan) {

        if (daftarLayanan.isEmpty()) {
            UI.gagal("Belum ada layanan terdaftar.");
            return;
        }

        System.out.println();
        UI.sectionOpen("DAFTAR LAYANAN");
        System.out.printf("  %-4s  %-6s  %-18s  %s%n", "No.", "ID", "Nama", "Harga/kg");
        UI.garisSimple();

        for (int i = 0; i < daftarLayanan.size(); i++) {
            Layanan l = daftarLayanan.get(i);
            System.out.printf("  %-4d  %-6s  %-18s  %s%n",
                    i + 1, l.getId(), l.getNama(), UI.uang(l.getHarga()));
        }

        UI.garisSimple();
        UI.info("Total: " + daftarLayanan.size() + " layanan");
        UI.sectionClose();
    }

    public static void update(ArrayList<Layanan> daftarLayanan, Scanner input) {

        if (daftarLayanan.isEmpty()) {
            UI.gagal("Belum ada layanan terdaftar.");
            return;
        }

        lihat(daftarLayanan);

        input.nextLine();

        System.out.println();
        UI.sectionOpen("EDIT LAYANAN");
        UI.prompt("ID layanan");
        String id = input.nextLine();

        for (Layanan l : daftarLayanan) {
            if (l.getId().equals(id)) {
                UI.prompt("Nama baru");
                String namaBaru = input.nextLine();
                UI.prompt("Harga baru");
                double hargaBaru = input.nextDouble();
                l.setNama(namaBaru);
                l.setHarga(hargaBaru);
                UI.sectionClose();
                UI.sukses("Data berhasil diperbarui");
                return;
            }
        }

        UI.gagal("Data tidak ditemukan");
    }

    public static void hapus(ArrayList<Layanan> daftarLayanan, Scanner input) {

        if (daftarLayanan.isEmpty()) {
            UI.gagal("Belum ada layanan terdaftar.");
            return;
        }

        lihat(daftarLayanan);

        input.nextLine();

        System.out.println();
        UI.sectionOpen("HAPUS LAYANAN");
        UI.prompt("ID layanan");
        String id = input.nextLine();

        for (Layanan l : daftarLayanan) {
            if (l.getId().equals(id)) {
                daftarLayanan.remove(l);
                UI.sectionClose();
                UI.sukses("Data berhasil dihapus");
                return;
            }
        }

        UI.gagal("Data tidak ditemukan");
    }
}