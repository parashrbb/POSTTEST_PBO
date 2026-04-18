import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    static ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
    static ArrayList<Layanan>   daftarLayanan   = new ArrayList<>();
    static ArrayList<Pesanan>   daftarPesanan   = new ArrayList<>();

    public static void main(String[] args) {

        // ===== DATA AWAL =====
        daftarPelanggan.add(new PelangganBiasa("P001", "Andi Saputra",  "Jl. Merdeka No. 5",    "081234567890"));
        daftarPelanggan.add(new PelangganBiasa("P002", "Dewi Lestari",  "Jl. Mawar No. 12",     "082345678901"));
        daftarPelanggan.add(new PelangganVIP  ("P003", "Rudi Hartono",  "Jl. Kenanga No. 3",    "083456789012", 15));
        daftarPelanggan.add(new PelangganVIP  ("P004", "Siti Rahayu",   "Jl. Melati No. 7",     "084567890123", 20));

        daftarLayanan.add(new Layanan("L001", "Cuci Kering",    5000));
        daftarLayanan.add(new Layanan("L002", "Cuci Setrika",   8000));
        daftarLayanan.add(new Layanan("L003", "Express 1 Hari", 12000));

        daftarPesanan.add(new Pesanan("PS001", "Andi Saputra", "Cuci Kering",    3.5, "Proses"));
        daftarPesanan.add(new Pesanan("PS002", "Rudi Hartono", "Cuci Setrika",   2.0, "Selesai"));
        daftarPesanan.add(new Pesanan("PS003", "Dewi Lestari", "Express 1 Hari", 1.5, "Proses"));

        int menu;

        do {
            UI.menu("SISTEM LAUNDRY", new String[]{
                    "Kelola Pelanggan",
                    "Kelola Layanan",
                    "Kelola Pesanan",
                    "Exit"
            });
            menu = input.nextInt();

            switch (menu) {
                case 1: menuPelanggan(); break;
                case 2: menuLayanan();   break;
                case 3: menuPesanan();   break;
                case 4: System.out.println(); UI.info("Sampai jumpa!"); break;
                default: UI.gagal("Menu tidak tersedia");
            }

        } while (menu != 4);
    }

    // ================= MENU PELANGGAN =================
    static void menuPelanggan() {
        int pilih;
        do {
            UI.menu("MENU PELANGGAN", new String[]{
                    "Tambah",
                    "Lihat",
                    "Update",
                    "Hapus",
                    "Kembali"
            });
            pilih = input.nextInt();

            switch (pilih) {
                case 1: Pelanggan.tambah(daftarPelanggan, input);   break;
                case 2: Pelanggan.lihat(daftarPelanggan, input);    break;
                case 3: Pelanggan.update(daftarPelanggan, input);   break;
                case 4: Pelanggan.hapus(daftarPelanggan, input);    break;
                case 0: break;
                default: UI.gagal("Menu tidak tersedia");
            }
        } while (pilih != 0);
    }

    // ================= MENU LAYANAN =================
    static void menuLayanan() {
        int pilih;
        do {
            UI.menu("MENU LAYANAN", new String[]{
                    "Tambah",
                    "Lihat",
                    "Update",
                    "Hapus",
                    "Kembali"
            });
            pilih = input.nextInt();

            switch (pilih) {
                case 1: Layanan.tambah(daftarLayanan, input); break;
                case 2: Layanan.lihat(daftarLayanan);         break;
                case 3: Layanan.update(daftarLayanan, input); break;
                case 4: Layanan.hapus(daftarLayanan, input);  break;
                case 0: break;
                default: UI.gagal("Menu tidak tersedia");
            }
        } while (pilih != 0);
    }

    // ================= MENU PESANAN =================
    static void menuPesanan() {
        int pilih;
        do {
            UI.menu("MENU PESANAN", new String[]{
                    "Tambah",
                    "Lihat Ringkas",
                    "Lihat + Estimasi Harga",
                    "Update",
                    "Hapus",
                    "Hitung Tagihan",
                    "Kembali"
            });
            pilih = input.nextInt();

            switch (pilih) {
                case 1: Pesanan.tambah(daftarPesanan, daftarPelanggan, daftarLayanan, input); break;
                case 2: Pesanan.lihatRingkas(daftarPesanan);                                  break;
                case 3: Pesanan.lihatDenganHarga(daftarPesanan, daftarLayanan);               break;
                case 4: Pesanan.update(daftarPesanan, daftarPelanggan, daftarLayanan, input); break;
                case 5: Pesanan.hapus(daftarPesanan, input);                                  break;
                case 6: Pesanan.hitungTagihan(daftarPesanan, daftarPelanggan, daftarLayanan, input); break;
                case 0: break;
                default: UI.gagal("Menu tidak tersedia");
            }
        } while (pilih != 0);
    }
}