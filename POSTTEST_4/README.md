# Sistem Laundry — Praktikum PBO Modul 5

Proyek Java berbasis CLI untuk manajemen sistem laundry yang menerapkan konsep **Polymorphism** (Method Overriding & Method Overloading) sebagai bagian dari mata kuliah Pemrograman Berorientasi Objek.

---

## Struktur File

```
├── Main.java
├── UI.java
├── Pelanggan.java
├── PelangganBiasa.java
├── PelangganVIP.java
├── Layanan.java
└── Pesanan.java
```

---

## Fitur Program

Program memiliki tiga menu utama, masing-masing dengan operasi CRUD lengkap:

| Menu | Fitur |
|------|-------|
| Kelola Pelanggan | Tambah, Lihat, Update, Hapus |
| Kelola Layanan | Tambah, Lihat, Update, Hapus |
| Kelola Pesanan | Tambah, Lihat Ringkas, Lihat + Estimasi Harga, Update, Hapus, Hitung Tagihan |

---

## Penerapan Polymorphism

### 1. Method Overriding (Dynamic Polymorphism)

Diterapkan melalui hierarki pewarisan `Pelanggan` → `PelangganBiasa` / `PelangganVIP`.

#### Override #1 — `tampilkan()` dan `tampilkanRingkas(int)`

Method ini ada di class `Pelanggan` sebagai parent, lalu di-override oleh kedua subclass untuk menampilkan informasi tambahan yang relevan dengan jenis pelanggannya.

```java
// Pelanggan.java (parent)
public void tampilkan() {
    UI.baris("ID", id);
    UI.baris("Nama", nama);
    UI.baris("Alamat", alamat);
    UI.baris("No HP", noHp);
}

// PelangganBiasa.java
@Override
public void tampilkan() {
    super.tampilkan();
    UI.baris("Status", "Biasa");
    UI.baris("Diskon", "Tidak ada");
}

// PelangganVIP.java
@Override
public void tampilkan() {
    super.tampilkan();
    UI.baris("Status", "VIP");
    UI.baris("Diskon", (int) diskon + "%");
}
```

#### Override #2 — `hitungTotal(double, double)`

Logika perhitungan total berbeda sesuai jenis pelanggan. `PelangganBiasa` menghitung langsung tanpa potongan, sedangkan `PelangganVIP` otomatis memotong harga sesuai persentase diskon yang dimilikinya.

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

### 2. Method Overloading (Static Polymorphism)

Diterapkan di class `Pesanan` dengan dua kelompok method yang berbeda versi parameternya.

#### Overload #1 — `tampilkan()`

Tiga versi `tampilkan()` untuk kebutuhan tampilan yang berbeda-beda.

```java
// Versi 1 — detail lengkap
public void tampilkan() { ... }

// Versi 2 — detail + estimasi harga
public void tampilkan(boolean tampilTotal, double hargaPerKg) { ... }

// Versi 3 — satu baris ringkas untuk tabel
public void tampilkan(String mode) { ... }
```

#### Overload #2 — `hitungTotal()`

Tiga versi `hitungTotal()` untuk skenario perhitungan yang berbeda.

```java
// Versi 1 — harga normal
public double hitungTotal(double hargaPerKg) { ... }

// Versi 2 — dengan biaya tambahan
public double hitungTotal(double hargaPerKg, double biayaTambahan) { ... }

// Versi 3 — dengan biaya tambahan dan diskon manual
public double hitungTotal(double hargaPerKg, double biayaTambahan, double diskonPersen) { ... }
```

---

## Data Awal (Seed)

Program sudah dilengkapi data awal saat dijalankan:

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

| Konsep | Teknik | Lokasi | Jumlah |
|--------|--------|--------|--------|
| Dynamic Polymorphism | Method Overriding | `PelangganBiasa`, `PelangganVIP` | 2 override × 2 subclass |
| Static Polymorphism | Method Overloading | `Pesanan` | 2 kelompok × 3 versi |