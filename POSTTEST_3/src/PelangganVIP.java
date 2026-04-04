public class PelangganVIP extends Pelanggan {

    private double diskon;

    public PelangganVIP(String id, String nama, String alamat, String noHp, double diskon) {
        super(id, nama, alamat, noHp);
        this.diskon = diskon;
    }

    @Override
    public void tampilkan() {
        super.tampilkan();
        System.out.println("Status    : VIP");
        System.out.println("Diskon    : " + diskon + "%");
        System.out.println("------------------------");
    }
}