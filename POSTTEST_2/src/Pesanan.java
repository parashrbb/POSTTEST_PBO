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

    public String getId() {
        return id;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void tampilkan() {
        System.out.println("ID Pesanan     : " + id);
        System.out.println("Nama Pelanggan : " + namaPelanggan);
        System.out.println("Nama Layanan   : " + namaLayanan);
        System.out.println("Berat (kg)     : " + berat);
        System.out.println("Status         : " + status);
        System.out.println("------------------------");
    }
}