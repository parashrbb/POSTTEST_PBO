public class Pelanggan {

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

    public String getId() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void tampilkan() {
        System.out.println("ID        : " + id);
        System.out.println("Nama      : " + nama);
        System.out.println("Alamat    : " + alamat);
        System.out.println("No HP     : " + noHp);
        System.out.println("------------------------");
    }
}