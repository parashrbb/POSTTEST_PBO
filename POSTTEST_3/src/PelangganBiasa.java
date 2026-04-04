public class PelangganBiasa extends Pelanggan {

    public PelangganBiasa(String id, String nama, String alamat, String noHp) {
        super(id, nama, alamat, noHp);
    }

    @Override
    public void tampilkan() {
        super.tampilkan();
        System.out.println("Status    : Biasa");
        System.out.println("------------------------");
    }
}