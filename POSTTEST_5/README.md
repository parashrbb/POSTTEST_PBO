# Sistem Laundry — Praktikum PBO Modul 6

Melanjutkan project posttest sebelumnya, dengan menerapkan konsep **Abstraction** menggunakan Abstract Class dan Interface pada sistem manajemen laundry berbasis CLI.

---

## Struktur File

```
├── Main.java
├── UI.java
├── Pelanggan.java        ← abstract class
├── PelangganBiasa.java   ← extends Pelanggan + implements Transaksi
├── PelangganVIP.java     ← extends Pelanggan + implements Transaksi
├── Transaksi.java        ← interface
├── Layanan.java
└── Pesanan.java
```

---

## Fitur Program

| Menu | Fitur |
|------|-------|
| Kelola Pelanggan | Tambah, Lihat, Update, Hapus |
| Kelola Layanan | Tambah, Lihat, Update, Hapus |
| Kelola Pesanan | Tambah, Lihat Ringkas, Lihat + Estimasi Harga, Update, Hapus, Hitung Tagihan |

---

## Penerapan Abstraction

### 1. Abstract Class — `Pelanggan`

Class `Pelanggan` diubah menjadi abstract class karena objek "pelanggan" secara generik tidak pernah dibuat langsung — yang dibuat selalu `PelangganBiasa` atau `PelangganVIP`. Abstract class ini berperan sebagai template yang memaksa setiap subclass untuk mengimplementasikan method-method tertentu.

```java
public abstract class Pelanggan {

    // atribut dan constructor tetap ada di sini
    // ...

    // Method konkrit — bisa langsung digunakan subclass
    protected void tampilkanDasar() { ... }
    public void tampilkanRingkas(int nomor) { ... }

    // Abstract method — wajib diimplementasikan subclass
    public abstract void tampilkan();
    public abstract double hitungTotal(double hargaPerKg, double berat);
}
```

Karena `Pelanggan` adalah abstract class, percobaan membuat objeknya langsung akan menghasilkan error:

```java
Pelanggan p = new Pelanggan(...); // ERROR: Cannot instantiate abstract class
```

Yang benar adalah membuat objek dari subclass-nya:

```java
Pelanggan p = new PelangganBiasa(...); // OK
Pelanggan p = new PelangganVIP(...);   // OK
```

#### Abstract Method #1 — `tampilkan()`

Setiap jenis pelanggan menampilkan informasi yang berbeda, sehingga implementasinya diserahkan ke masing-masing subclass.

```java
// PelangganBiasa.java
@Override
public void tampilkan() {
    tampilkanDasar();
    UI.baris("Status", "Biasa");
    UI.baris("Diskon", "Tidak ada");
}

// PelangganVIP.java
@Override
public void tampilkan() {
    tampilkanDasar();
    UI.baris("Status", "VIP");
    UI.baris("Diskon", (int) diskon + "%");
}
```

#### Abstract Method #2 — `hitungTotal(double, double)`

Logika perhitungan berbeda: `PelangganBiasa` tidak mendapat potongan, sedangkan `PelangganVIP` otomatis memotong harga sesuai diskonnya.

```java
// PelangganBiasa.java
@Override
public double hitungTotal(double hargaPerKg, double berat) {
    double total = hargaPerKg * berat;
    UI.baris("Total", UI.uang(total) + "  (tanpa diskon)");
    return total;
}

// PelangganVIP.java
@Override
public double hitungTotal(double hargaPerKg, double berat) {
    double subtotal = hargaPerKg * berat;
    double potongan = subtotal * (diskon / 100);
    double total    = subtotal - potongan;
    UI.baris("Subtotal", UI.uang(subtotal));
    UI.baris("Diskon " + (int) diskon + "%", "- " + UI.uang(potongan));
    UI.baris("Total", UI.uang(total) + "  (setelah diskon)");
    return total;
}
```

---

### 2. Interface — `Transaksi`

Interface `Transaksi` berperan sebagai kontrak yang mewajibkan setiap class yang mengimplementasikannya untuk menyediakan dua method: satu untuk mengembalikan kategori pelanggan, dan satu lagi untuk mencetak ringkasan tagihan.

```java
public interface Transaksi {
    String getKategori();
    void prosesTagihan(double hargaPerKg, double berat);
}
```

Interface ini diimplementasikan oleh `PelangganBiasa` dan `PelangganVIP` sekaligus bersamaan dengan mewarisi abstract class `Pelanggan`:

```java
public class PelangganBiasa extends Pelanggan implements Transaksi { ... }
public class PelangganVIP   extends Pelanggan implements Transaksi { ... }
```

#### Method Interface #1 — `getKategori()`

Mengembalikan label kategori pelanggan sebagai String.

```java
// PelangganBiasa.java
@Override
public String getKategori() {
    return "Biasa";
}

// PelangganVIP.java
@Override
public String getKategori() {
    return "VIP " + (int) diskon + "%";
}
```

#### Method Interface #2 — `prosesTagihan(double, double)`

Mencetak ringkasan tagihan singkat sesuai jenis pelanggan, termasuk informasi penghematan untuk VIP.

```java
// PelangganBiasa.java
@Override
public void prosesTagihan(double hargaPerKg, double berat) {
    double total = hargaPerKg * berat;
    UI.info("Kategori  : " + getKategori());
    UI.info("Tagihan   : " + UI.uang(total) + " (tanpa diskon)");
}

// PelangganVIP.java
@Override
public void prosesTagihan(double hargaPerKg, double berat) {
    double subtotal = hargaPerKg * berat;
    double potongan = subtotal * (diskon / 100);
    double total    = subtotal - potongan;
    UI.info("Kategori  : " + getKategori());
    UI.info("Tagihan   : " + UI.uang(total) + " (hemat " + UI.uang(potongan) + ")");
}
```

Interface `Transaksi` dipanggil di `Pesanan.java` saat menghitung tagihan dengan cara casting:

```java
if (p instanceof Transaksi) {
    Transaksi t = (Transaksi) p;
    t.prosesTagihan(hargaPerKg, target.getBerat());
}
```

---

## Perbedaan Abstract Class dan Interface pada Program Ini

| | `Pelanggan` (Abstract Class) | `Transaksi` (Interface) |
|---|---|---|
| Hubungan | Hierarki — subclass mewarisi atribut dan method | Kontrak — class wajib memenuhi method yang ditentukan |
| Constructor | Ada | Tidak ada |
| Method konkrit | Ada (`tampilkanDasar`, `tampilkanRingkas`) | Tidak ada |
| Digunakan sebagai | Template dasar pelanggan | Kemampuan tambahan untuk proses tagihan |

---

## Data Awal (Seed)

**Pelanggan:**

| ID | Nama | Jenis | Diskon |
|----|------|-------|--------|
| P001 | Andi Saputra | Biasa | — |
| P002 | Dewi Lestari | Biasa | — |
| P003 | Rudi Hartono | VIP | 15% |
| P004 | Siti Rahayu | VIP | 20% |

**Layanan:**

| ID | Nama | Harga/kg |
|----|------|----------|
| L001 | Cuci Kering | Rp 5.000 |
| L002 | Cuci Setrika | Rp 8.000 |
| L003 | Express 1 Hari | Rp 12.000 |

**Pesanan:**

| ID | Pelanggan | Layanan | Berat | Status |
|----|-----------|---------|-------|--------|
| PS001 | Andi Saputra | Cuci Kering | 3.5 kg | Proses |
| PS002 | Rudi Hartono | Cuci Setrika | 2.0 kg | Selesai |
| PS003 | Dewi Lestari | Express 1 Hari | 1.5 kg | Proses |

---

## Ringkasan Konsep

| Konsep | Mekanisme | Lokasi | Keterangan |
|--------|-----------|--------|------------|
| Abstract Class | `abstract class` | `Pelanggan.java` | Tidak bisa diinstansiasi langsung |
| Abstract Method | `public abstract` | `Pelanggan.java` | 2 method: `tampilkan()` dan `hitungTotal()` |
| Interface | `interface` | `Transaksi.java` | 2 method: `getKategori()` dan `prosesTagihan()` |
| Implements | `implements Transaksi` | `PelangganBiasa`, `PelangganVIP` | Keduanya extend abstract class sekaligus implement interface |