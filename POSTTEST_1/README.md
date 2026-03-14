# SISTEM FOUNDRY

## Deskripsi Program
Program ini merupakan aplikasi sederhana untuk mengelola sistem laundry yang dibuat menggunakan bahasa pemrograman **Java** dengan konsep **Object Oriented Programming (OOP)**.

Program ini memungkinkan pengguna untuk mengelola data pelanggan, layanan laundry, dan pesanan laundry melalui menu berbasis console.

---

## Fitur Program

Program memiliki beberapa fitur utama:

### 1. Kelola Pelanggan
![menu pelanggan.png](FotoLaporanFoundry/menu%20pelanggan.png)

### 2. Kelola Layanan
![menu layanan.png](FotoLaporanFoundry/menu%20layanan.png)

### 3. Kelola Pesanan
![menu pesanan.png](FotoLaporanFoundry/menu%20pesanan.png)
---

## Struktur Class Program

Program ini terdiri dari beberapa class utama:

### 1. Main.java
Class utama yang berisi:
- Menu utama program
- Menu pengelolaan pelanggan
- Menu pengelolaan layanan
- Menu pengelolaan pesanan
- Proses CRUD (Create, Read, Update, Delete)

Data disimpan menggunakan **ArrayList**.

Contoh:
```
ArrayList<Pelanggan> daftarPelanggan
ArrayList<Layanan> daftarLayanan
ArrayList<Pesanan> daftarPesanan
```

---

### 2. Pelanggan.java
Class yang digunakan untuk menyimpan data pelanggan.

Atribut:
- `id`
- `nama`
- `alamat`
- `noHp`

Method yang tersedia:
- Constructor
- Getter (`getId`)
- Setter (`setNama`, `setAlamat`, `setNoHp`)
- Method `tampilkan()` untuk menampilkan data pelanggan.

---

### 3. Layanan.java
Class yang digunakan untuk menyimpan data layanan laundry.

Atribut:
- `id`
- `nama`
- `harga`

Method:
- Constructor
- Getter (`getId`)
- Setter (`setNama`, `setHarga`)
- Method `tampilkan()` untuk menampilkan data layanan.

---

### 4. Pesanan.java
Class yang digunakan untuk menyimpan data pesanan laundry.

Atribut:
- `id`
- `namaPelanggan`
- `namaLayanan`
- `berat`
- `status`

Method:
- Constructor
- Getter (`getId`)
- Setter (`setNamaPelanggan`, `setNamaLayanan`, `setBerat`, `setStatus`)
- Method `tampilkan()` untuk menampilkan data pesanan.

---

## Konsep OOP yang Digunakan

Program ini menerapkan beberapa konsep Object Oriented Programming:

### 1. Encapsulation
Semua atribut dalam class dibuat **private** sehingga tidak dapat diakses langsung dari luar class.

Contoh:
```
private String nama;
```

Atribut diakses menggunakan **getter dan setter**.

---

### 2. Object
Data pelanggan, layanan, dan pesanan disimpan dalam bentuk **object**.

Contoh:
```
Pelanggan p = new Pelanggan(id, nama, alamat, noHp);
```

---

### 3. ArrayList
Digunakan untuk menyimpan banyak object dalam satu list.

Contoh:
```
ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
```

---

## Cara Menjalankan Program

1. Compile semua file Java.
2. Jalankan class **Main.java**.
3. Program akan menampilkan menu utama.

Contoh tampilan menu:
![menu laundry.png](FotoLaporanFoundry/menu%20laundry.png)

Pengguna dapat memilih menu sesuai kebutuhan.

---

## Contoh Alur Program

1. Tambahkan pelanggan baru
2. Tambahkan layanan laundry
3. Buat pesanan laundry
4. Lihat daftar pesanan
5. Update atau hapus data jika diperlukan

---

## TERIMAKASSIH