# 🐶 Woof App - Jetpack Compose Project
**Aulia Putri Salsabila - 5025221281**
Penerapan Tema Material Dalam Aplikasi Woof Menggunakan Jetpack Compose

## 📱 Deskripsi
Dalam project ini, saya membangun aplikasi Android sederhana bernama **Woof** menggunakan **Jetpack Compose**.  
Aplikasi ini menampilkan daftar anjing lengkap dengan gambar, nama, usia, dan deskripsi hobi.  
Saya juga menambahkan fitur pencarian (**search**) dan pengurutan (**sort**) berdasarkan nama anjing.

---

## ✨ Fitur Utama

- 🎨 **Desain Modern dengan Jetpack Compose**
  - Menggunakan Material3
  - Tema warna **oranye** untuk nuansa hangat
  - UI responsif dengan elemen Compose seperti Card, Column, dan LazyColumn

- 🔍 **Search Box**
  - Pengguna dapat mencari anjing berdasarkan nama

- 🅰️ **Filter Sort A-Z dan Z-A**
  - Data anjing dapat diurutkan berdasarkan nama secara ascending maupun descending

- 🔽 **Expandable Card**
  - Tekan kartu untuk melihat detail seperti usia, energi, dan hobi

- ❤️ **Favorit**
  - Ikon hati untuk menandai anjing favorit (menggunakan state lokal)

---

## 🐾 Struktur Data (Dog.kt)

```kotlin
data class Dog(
    val imageResourceId: Int,
    val name: Int,
    val age: Int,
    val hobbies: Int
)
```

## 🛠️ Komponen Penting:
- WoofApp() – Fungsi utama yang menampilkan daftar anjing.
- DogItem() – Card untuk setiap anjing dengan animasi expand.
- OutlinedTextField – Untuk pencarian.
- DropdownMenu – Untuk memilih urutan A-Z atau Z-A.
- LazyColumn – Menampilkan list anjing secara efisien.


## 📸 HASIL CAPTURE
<p align="center">
  <img src="https://github.com/user-attachments/assets/08cabae3-16c6-45c5-a95e-e4d6278fdec7" width="250"/>
  <img src="https://github.com/user-attachments/assets/3cfee208-2452-494b-927a-9c9d962ed401" width="250"/>
  <img src="https://github.com/user-attachments/assets/f6f3084c-4547-4747-9d0d-6f92decdc4bd" width="250"/>
</p>
