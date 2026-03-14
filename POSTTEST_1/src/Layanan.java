public class Layanan {

    private String id;
    private String nama;
    private double harga;

    public Layanan(String id, String nama, double harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void tampilkan() {
        System.out.println("ID Layanan : " + id);
        System.out.println("Nama       : " + nama);
        System.out.println("Harga      : " + harga);
        System.out.println("------------------------");
    }
}