## Refleksi 1

> You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code

#### Clean Code Principles yang sudah diterapkan
- *Meaningfull names* <br>
  Penamaaan variable, class, method yang bermakna sehingga dapat meningkatkan code readability. Contohnya, `deleteProduct()`, existingProduct, updatedProduct, dll
- *Function / Method*<br>
  Setiap fungsi hanya memiliki 1 tujuan spesifik. Contohnya fungsi deleteProduct(), editProduct(), createProduct() yang masing-masing hanya memiliki 1 tujuan sesuai dengan nama fungsi tersebut.
- *Objects and Data Structures*<br>
  Menerapkan konsep encapsulation pada class Product yang memiliki beberapa private atribut seperti productId, productName, dan productQuantity,


#### Secure Code Principles yang sudah diterapkan
- Menggunakan UUID sebagai productId demi meningkatkan keamanan data
- Validasi input pada fields productName agar terhindar dari serangan sejenis SQL Injection.
- Menerapkan encapsulation pada class Product dengan menggunakan access modifier private sehingga akses langsung ke variabel tersebut dibatasi.

#### Improve Code
- Ketika ada suatu bug pada suatu method tertentu, saya memperhatikan kembali tujuan spesifik dari method tersebut. Kemudian memperhatikan kembali logic yang telah ditulis.
- Pada kode saya saat ini. validasi input dilakukan hanya di sisi klien saja. Kedepannya, agar keamanan dapat lebih baik, validasi juga perlu diterapkan di sisi server.
- Pada suatu logika kode yang kompleks, sebaiknya diberikan komentar tambahan untuk menjelaskan logika yang sebenarnya terjadi.



<hr>

# Refleksi 2

> After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?

Unit test dapat membantu kita dalam memastikan semua komponen pada kode kita berjalan dengan semestinya. Namun, ketika menulis kode untuk unit test, saya juga merasa banyak sekali repetitif pada kode yang ditulis. Jadi saya juga harus membiasakan hal tersebut karena akan sangat dibutuhkan kedepannya dibandingkan dengan melakukan debugging secara manual.
<br>
Untuk menghitung berapa banyak kode unit test pada suatu class, tidak ada angka yang pasti, melainkan unit test harus mencakup beberapa fungsionalitas, termasuk positive case, negative case dan juga edge-case.  
Di sisi lain, untuk memastikan unit test kita cukup yaitu dengan meninjau code coverage. Code coverage adalah ukuran seberapa banyak kode kita yang telah dilakukan testing. Semakin tinggi persentase code coverage, maka semakin banyak juga bagian dari kode kita yang telah teruji.
<br>
Ketika code coverage kita sudah mencapai 100%, hal tersebut belum tentu menjadi satu-satunya acuan kita untuk memastikan bahwa kode kita sudah dijamin tidak memiliki kesalahan. Ada kemungkinan kode kita memiliki masalah dalam integrasi, ataupun masalah yang terdapat pada komponen-komponen diluar method atau bahkan file kita yang tidak terdeteksi.

> What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

Masalah yang terjadi ketika dilakukan pengulangan kode dengan isi  <br>
- Duplikasi kode<br>
  Jika setup prosedur sebagian besar sama maka dapat menyebabkan duplikasi kode sehingga menyulitkan pemeliharaan. Misalnya, jika terdapat perubahan kode pada suatu fungsi, maka harus dilakukan perubahan terhadap beberapa tempat.
- Melanggar prinsip Don't Repeat Yourself
  Menulis ulang kode dapat mengurangi efisiensi program dan kualiatas kode secara keseluruhan. Kode yang berkualitas seharusnya mduah untuk dilakukan refactor dan reusable.<br>


Solusi
- Dapat melakukan refactor dengan base class misalnya pada BaseFunctionalTest yang berisi setup prosedur yang umum. Kemudian, subclass test yang lainnya dapat mewarisi superclass pada base class tersebut.
- Dapat menggunakan helper method yang membantu dalam mereduksi fungsionalitas dari class yang dipanggil. Hal ini dapat membantu mengurangi duplikasi dan meningkatkan readability <br>
-
Dengan demikian, meskipun terdapat setup prosedur yang sama tidak langsung menurunkan kualitas kode. Dengan melakukana refactoring, kita dapat meningkatkan kualitas dan efisiensi kode sehingga dapat mempermudah kita dalam melakukan pemeliharaan dan melakukana update fitur kedepannya nanti.