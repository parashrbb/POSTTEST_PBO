import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    static ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
    static ArrayList<Layanan> daftarLayanan = new ArrayList<>();
    static ArrayList<Pesanan> daftarPesanan = new ArrayList<>();

    public static void main(String[] args) {

        int menu;

        do {

            System.out.println("\n=== SISTEM LAUNDRY ===");
            System.out.println("1. Kelola Pelanggan");
            System.out.println("2. Kelola Layanan");
            System.out.println("3. Kelola Pesanan");
            System.out.println("4. Exit");

            System.out.print("Pilih menu: ");
            menu = input.nextInt();

            switch (menu) {

                case 1:
                    menuPelanggan();
                    break;

                case 2:
                    menuLayanan();
                    break;

                case 3:
                    menuPesanan();
                    break;

                case 4:
                    System.out.println("Program selesai");
                    break;

                default:
                    System.out.println("Menu tidak tersedia");

            }

        } while (menu != 4);
    }

    // ================= MENU PELANGGAN =================
    static void menuPelanggan() {

        int pilih;

        do {

            System.out.println("\n=== MENU PELANGGAN ===");
            System.out.println("1. Tambah");
            System.out.println("2. Lihat");
            System.out.println("3. Update");
            System.out.println("4. Hapus");
            System.out.println("5. Kembali");

            System.out.print("Pilih: ");
            pilih = input.nextInt();

            switch (pilih) {

                case 1:
                    tambahPelanggan();
                    break;

                case 2:
                    System.out.println("==== DAFTAR PELANGGAN ====");
                    lihatPelanggan();
                    break;

                case 3:
                    updatePelanggan();
                    break;

                case 4:
                    hapusPelanggan();
                    break;

            }

        } while (pilih != 5);
    }

    static void tambahPelanggan() {

        input.nextLine();

        System.out.println("======= TAMBAH PELANGGAN =======");
        System.out.print("ID: ");
        String id = input.nextLine();

        for (Pelanggan p : daftarPelanggan) {
            if (p.getId().equals(id)) {
                System.out.println("ID sudah ada!");
                return;
            }
        }

        System.out.print("Nama: ");
        String nama = input.nextLine();

        System.out.print("Alamat: ");
        String alamat = input.nextLine();

        System.out.print("No HP: ");
        String noHp = input.nextLine();

        Pelanggan p = new Pelanggan(id, nama, alamat, noHp);
        daftarPelanggan.add(p);

        System.out.println("Data berhasil ditambahkan");
    }

    static void lihatPelanggan() {

        for (Pelanggan p : daftarPelanggan) {
            p.tampilkan();
        }
    }

    static void updatePelanggan() {

        input.nextLine();

        System.out.println("======= EDIT PELANGGAN =======");
        System.out.print("Masukkan ID: ");
        String id = input.nextLine();

        for (Pelanggan p : daftarPelanggan) {

            if (p.getId().equals(id)) {

                System.out.print("Nama baru: ");
                String namaBaru = input.nextLine();

                System.out.print("Alamat baru: ");
                String alamatBaru = input.nextLine();

                System.out.print("No HP baru: ");
                String noHpBaru = input.nextLine();

                p.setNama(namaBaru);
                p.setAlamat(alamatBaru);
                p.setNoHp(noHpBaru);

                System.out.println("Data berhasil diupdate");
                return;
            }
        }

        System.out.println("Data tidak ditemukan");
    }

    static void hapusPelanggan() {

        input.nextLine();

        System.out.println("======= HAPUS PELANGGAN =======");
        System.out.print("Masukkan ID: ");
        String id = input.nextLine();

        for (Pelanggan p : daftarPelanggan) {

            if (p.getId().equals(id)) {

                daftarPelanggan.remove(p);
                System.out.println("Data berhasil dihapus");
                return;
            }
        }

        System.out.println("Data tidak ditemukan");
    }

    // ================= MENU LAYANAN =================
    static void menuLayanan() {

        int pilih;

        do {

            System.out.println("\n=== MENU LAYANAN ===");
            System.out.println("1. Tambah");
            System.out.println("2. Lihat");
            System.out.println("3. Update");
            System.out.println("4. Hapus");
            System.out.println("5. Kembali");

            System.out.print("Pilih: ");
            pilih = input.nextInt();

            switch (pilih) {

                case 1:
                    tambahLayanan();
                    break;

                case 2:
                    System.out.println("==== DAFTAR LAYANAN ====");
                    lihatLayanan();
                    break;

                case 3:
                    updateLayanan();
                    break;

                case 4:
                    hapusLayanan();
                    break;

            }

        } while (pilih != 5);
    }

    static void tambahLayanan() {

        input.nextLine();

        System.out.println("======= TAMBAH LAYANAN =======");
        System.out.print("ID: ");
        String id = input.nextLine();

        System.out.print("Nama layanan: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        double harga = input.nextDouble();

        Layanan l = new Layanan(id, nama, harga);
        daftarLayanan.add(l);

        System.out.println("Layanan ditambahkan");
    }

    static void lihatLayanan() {

        for (Layanan l : daftarLayanan) {
            l.tampilkan();
        }
    }

    static void updateLayanan() {

        input.nextLine();

        System.out.println("======= EDIT LAYANAN =======");
        System.out.print("ID layanan: ");
        String id = input.nextLine();

        for (Layanan l : daftarLayanan) {

            if (l.getId().equals(id)) {

                System.out.print("Nama baru: ");
                String namaBaru = input.nextLine();

                System.out.print("Harga baru: ");
                double hargaBaru = input.nextDouble();

                l.setNama(namaBaru);
                l.setHarga(hargaBaru);

                System.out.println("Data diperbarui");
                return;
            }
        }

        System.out.println("Data tidak ditemukan");
    }

    static void hapusLayanan() {

        input.nextLine();

        System.out.println("======= HAPUS LAYANAN =======");
        System.out.print("ID layanan: ");
        String id = input.nextLine();

        for (Layanan l : daftarLayanan) {

            if (l.getId().equals(id)) {

                daftarLayanan.remove(l);
                System.out.println("Data dihapus");
                return;
            }
        }
    }

    // ================= PESANAN =================
    static void menuPesanan() {

        int pilih;

        do {

            System.out.println("\n=== MENU PESANAN ===");
            System.out.println("1. Tambah");
            System.out.println("2. Lihat");
            System.out.println("3. Update");
            System.out.println("4. Hapus");
            System.out.println("5. Kembali");

            System.out.print("Pilih: ");
            pilih = input.nextInt();

            switch (pilih) {

                case 1:
                    tambahPesanan();
                    break;

                case 2:
                    System.out.println("==== DAFTAR PESANAN ====");
                    lihatPesanan();
                    break;

                case 3:
                    updatePesanan();
                    break;

                case 4:
                    hapusPesanan();
                    break;

            }

        } while (pilih != 5);
    }

    static void tambahPesanan() {

        input.nextLine();

        System.out.println("======= TAMBAH PESANAN =======");
        System.out.print("ID Pesanan: ");
        String id = input.nextLine();

        for (Pesanan p : daftarPesanan) {
            if (p.getId().equals(id)) {
                System.out.println("ID sudah digunakan!");
                return;
            }
        }

        System.out.print("Nama Pelanggan: ");
        String namaPelanggan = input.nextLine();

        System.out.print("Nama Layanan: ");
        String namaLayanan = input.nextLine();

        System.out.print("Berat (kg): ");
        double berat = input.nextDouble();

        Pesanan ps = new Pesanan(id, namaPelanggan, namaLayanan, berat, "Proses");
        daftarPesanan.add(ps);

        System.out.println("Pesanan berhasil ditambahkan!");
    }

    static void lihatPesanan() {
        for (Pesanan p : daftarPesanan) {
            p.tampilkan();
        }
    }

    static void updatePesanan() {

        input.nextLine();

        System.out.println("======= EDIT PESANAN =======");
        System.out.print("Masukkan ID Pesanan: ");
        String id = input.nextLine();

        for (Pesanan p : daftarPesanan) {

            if (p.getId().equals(id)) {
                System.out.print("Nama Pelanggan: ");
                String namaPelangganBaru = input.nextLine();

                System.out.print("Nama Layanan: ");
                String namaLayananBaru = input.nextLine();

                System.out.print("Berat baru: ");
                double beratBaru = input.nextDouble();
                input.nextLine();

                System.out.print("Status baru: ");
                String statusBaru = input.nextLine();

                p.setBerat(beratBaru);
                p.setStatus(statusBaru);
                p.setNamaPelanggan(namaPelangganBaru);
                p.setNamaLayanan(namaLayananBaru);

                System.out.println("Pesanan berhasil diupdate!");
                return;
            }
        }

        System.out.println("Pesanan tidak ditemukan!");
    }

    static void hapusPesanan() {
        input.nextLine();
        System.out.println("======= HAPUS PESANAN =======");
        System.out.print("Masukkan ID Pesanan: ");
        String id = input.nextLine();

        for (Pesanan p : daftarPesanan) {

            if (p.getId().equals(id)) {
                daftarPesanan.remove(p);
                System.out.println("Pesanan berhasil dihapus!");
                return;
            }
        }

        System.out.println("Pesanan tidak ditemukan!");
    }
}