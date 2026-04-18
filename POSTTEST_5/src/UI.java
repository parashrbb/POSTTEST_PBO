public class UI {

    private static final int LEBAR = 36;

    // ===== BORDER =====
    public static void headerBox(String judul) {
        String garis = "═".repeat(LEBAR);
        int sisaSpasi = LEBAR - judul.length();
        int kiri = sisaSpasi / 2;
        int kanan = sisaSpasi - kiri;
        System.out.println("╔" + garis + "╗");
        System.out.println("║" + " ".repeat(kiri) + judul + " ".repeat(kanan) + "║");
        System.out.println("╚" + garis + "╝");
    }

    public static void sectionOpen(String judul) {
        int sisa = LEBAR - judul.length() - 3;
        System.out.println("┌─ " + judul + " " + "─".repeat(Math.max(1, sisa)) + "┐");
    }

    public static void sectionClose() {
        System.out.println("└" + "─".repeat(LEBAR + 2) + "┘");
    }

    public static void garis() {
        System.out.println("│  " + "─".repeat(LEBAR - 2) + "  │");
    }

    public static void garisSimple() {
        System.out.println("  " + "─".repeat(LEBAR));
    }

    // ===== FORMAT =====
    public static String uang(double nominal) {
        long bulat = Math.round(nominal);
        String str = String.valueOf(bulat);
        StringBuilder hasil = new StringBuilder();
        int hitung = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (hitung > 0 && hitung % 3 == 0) hasil.insert(0, ".");
            hasil.insert(0, str.charAt(i));
            hitung++;
        }
        return "Rp " + hasil;
    }

    // ===== MENU =====
    public static void menu(String judul, String[] opsi) {
        System.out.println();
        headerBox(judul);
        for (int i = 0; i < opsi.length; i++) {
            int nomor = (i == opsi.length - 1) ? 0 : (i + 1);
            System.out.printf("  [%d] %s%n", nomor, opsi[i]);
        }
        System.out.println();
        System.out.print("» Pilih: ");
    }

    // ===== OUTPUT =====
    public static void baris(String label, String nilai) {
        System.out.printf("  %-14s: %s%n", label, nilai);
    }

    public static void prompt(String teks) {
        System.out.print("  " + teks + ": ");
    }

    public static void sukses(String pesan) {
        System.out.println();
        System.out.println("  ✓ " + pesan);
    }

    public static void gagal(String pesan) {
        System.out.println();
        System.out.println("  ✗ " + pesan);
    }

    public static void info(String pesan) {
        System.out.println("  " + pesan);
    }
}