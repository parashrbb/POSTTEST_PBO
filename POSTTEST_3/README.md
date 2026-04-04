# Posttest 2 - Sistem Laundry (Java OOP)

## Deskripsi Program
Program ini merupakan aplikasi sederhana berbasis **Java** yang digunakan untuk mengelola sistem laundry. Program dibuat menggunakan konsep **Object Oriented Programming (OOP)** dan berjalan melalui **console/terminal**.

Program ini dapat digunakan untuk mengelola data pelanggan, layanan laundry, dan pesanan laundry.

---

## Fitur Program

Program memiliki beberapa fitur utama:

### 1. Kelola Pelanggan
- Menambah data pelanggan
- Melihat daftar pelanggan
- Mengupdate data pelanggan
- Menghapus data pelanggan

### 2. Kelola Layanan
- Menambah layanan laundry
- Melihat daftar layanan
- Mengupdate layanan
- Menghapus layanan

### 3. Kelola Pesanan
- Menambah pesanan laundry
- Melihat daftar pesanan
- Mengupdate pesanan
- Menghapus pesanan

---

## Struktur Class

Program terdiri dari beberapa class:

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
Class untuk menyimpan data pelanggan.

Atribut:
- id
- nama
- alamat
- noHp

Method:
- Constructor
- Getter
- Setter
- Method `tampilkan()` untuk menampilkan data pelanggan.

---

### 3. Layanan.java
Class untuk menyimpan data layanan laundry.

Atribut:
- id
- nama
- harga

Method:
- Constructor
- Getter
- Setter
- Method `tampilkan()` untuk menampilkan data layanan.

---

### 4. Pesanan.java
Class untuk menyimpan data pesanan laundry.

Atribut:
- id
- namaPelanggan
- namaLayanan
- berat
- status

Method:
- Constructor
- Getter
- Setter
- Method `tampilkan()` untuk menampilkan data pesanan.

---

## Konsep OOP yang Digunakan

### 1. Encapsulation
Atribut dalam class dibuat **private** atau **protected** sehingga tidak dapat diakses langsung dari luar class.

Contoh:

```
private String id;
protected String nama;
```

Atribut tersebut diakses menggunakan **getter dan setter**.

---

### 2. Access Modifier
Program ini menggunakan beberapa jenis access modifier pada Java:

- **private** → hanya dapat diakses dalam class itu sendiri
- **protected** → dapat diakses dalam package yang sama atau subclass
- **public** → dapat diakses dari class lain
- **default** → dapat diakses dalam package yang sama

---

### 3. Object
Data disimpan dalam bentuk **object** dari class yang telah dibuat.

Contoh:

```
Pelanggan p = new Pelanggan(id, nama, alamat, noHp);
```

---

### 4. ArrayList
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

Contoh menu:

```
=== SISTEM LAUNDRY ===
1. Kelola Pelanggan
2. Kelola Layanan
3. Kelola Pesanan
4. Exit
```

Pilih menu sesuai kebutuhan.

---

## TERIMAKASIHH.