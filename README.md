# Modul 3

1) SOLID Principle yang telah saya terapkan, yaitu:
  - Single Responsibility Principle (SRP)<br>
    Saya telah menggunakan prinsip ini dengan memisahkan `CarController` dengan `ProductController`. `ProductController` berfokus hanya untuk menghandle `Product` dan `CarController` berfokus hanya unutk menghandle `Car`
  - Open/Closed Principle (OCP)<br>
    Pada `CarService`, saya telah menerapkan interface yang dapat digunakan untuk menambah method atau baru tanpa mengubah main code.
  - Liskov Substitution Principle (LSP)<br>
    Sebelumnya, `CarController` menginherit `ProductController`, tetapi diubah menjadi komposisi untuk menghindari ketergantungan yang tidak diperlukan.
  - Dependency Inversion Principle (DIP)<br>
    Dengan menggunakan interface `GenericRepository<T>`, komponen yang bergantung pada repository tidak bergantung pada implementasi konkret dari `CarRepository` atau `ProductRepository`, melainkan dari file `GenericRepository`.
  - Interface Segregation Principle (ISP)<br>
    Interface yang dibuat pada `GenericRepository<T>` dan `GenericService<T>` dirancang agar hanya mendefinisikan operasi CRUD dasar yang diperlukan sehingga klien tidak tergantung pada metode yang tidak digunakan.

2) Dengan menerapkan SOLID principles pada projek, maka: <br>
  - Projek kita jadi mudah untuk dimaintain. Misalnya, jika ada perubahan pada car controller, kita hanya fokus untuk memodifikasi kode di class CarController, jadi pada ProductController tidak akan terpengaruh oleh perubahan tersebut.<br>
  - kita bisa mengganti implementasi CarService dengan implementasi baru tanpa mempengaruhi komponen lain yang bergantung pada interfacenya.<br>
  - Memastikan bahwa interface yang digunakan oleh klien hanya berisi metode yang relevan saja. Hal ini dapat memudahkan klien dalam memahami dan mengimplementasikan.

3) Jika kita tidak menerapkan SOLID principles pada projek kita, maka ada beberapa kekurangan yang bisa kita temukan:
  - Kode menjadi sulit di maintain. Jika ada suatu perubahan terhadap suatu class/method yang memiliki banyak logika yang berbeda, maka perubahan tersebut dapat berdampak ke banyak aspek lainnya. Contoh, ketika CarController dan ProductController digabung menjadi satu class file saja, maka ketika ada perubahan pada
  - Tanpa menerapkan OCP, kita harus melakukan perbuahan pada suatu method/class yang sudah stabil. Hal ini dapat menyebabkan potensi munculnya bug baru pada method yg sudah statbil. Contoh, menerapkan log  `System.out.println(car.getCarId());` di dalam method `editCarPost` , yang mana berarti jika ingin menambahkan logging, harus mengubah methodnya secara langsung.
  - Tanpa menggunakan interface `CarRepository`, mengganti penyimpanan data ke database mengharuskan rewrite seluruh logika di CarServiceImpl,yang dapat beresiko memunculkan bug.

<hr>
<details>
<summary>Module 2</summary>

# Modul 2

[Link Deployment (Koyeb)](https://preferred-nadean-faridhsbi-4c486887.koyeb.app)

## Refleksi 1

> List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

Setelah melakukan identifikasi dengan menggunakan SonarQube, ada beberapa issue yang saya temukan pada kode saya, antara lain:
1. **Unit test method harus memiliki minimal 1 assertion** <br>
  Untuk memperbaiki issue diatas, saya menambahkan minimal satu assertion kedalam test method yang telah saya buat sebelumnya. Hal tersebut bertujuan untuk memastikan bahwa metode yang ditest dapat menghasilkan output yang semestinya.
2. **Removing unused declaration of thrown exception**<br>
  Issue tersebut terjadi karena saya menggunakan `throws exception` pada beberapa method unit test saya, tetapi method tersebut tidak mengimplementasikan `throws exception` sama sekali, sehingga saya harus menghapusnya.
3. **Removing autowired field injection and added constructor injection**<br>
  Penggunaan `@Autowired` secara langsung pada field menyebabkan masalah dalam hal dependency yang sulit ditest dan kurang fleksibel. Jadi saya menggantinya dengan menggunakan constructor testability.
4. **Adding a nested comment for explaining empty method**<br>
   Menambahkan penjelasan comment pada method saya yang kosong, hal tersebut bertujuan agar tidak menimbulkan kerancuan dan kebingungan mengapa kode tersebut dibiarkan kosong.
5. **Grouping Dependencies by Their Destination** <br>
   Deoendencies pada `build.gradle.kts` harus disusun berdasarkan fungsionalitasnya. Hal ini dapat menambah readability dan maintainabality pada masing-masing dependency.

> Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

Pada CI/CD workflows yang sudah saya implementasi, menurut saya implementasi tersebut sudah bisa dikatakan sebagai definisi CI/CD, karena beberapa alasan, antara lain:
- **Continuous Integration** <br>
  File `ci.yml` yang telah saya buat di `.github/workflows`, berfungsi untuk menjalankan unit test dan analisis lainnya setiap adanya push dan pull request. Hal tersebut dapat memastikan bahwa setiap ada perubahan pada repository telah diuji sebelum dilakukan merge pada master branch.
- **Continuous Deployment**<br>
  Saya telah mengintegrasikan repository saya dengan aplikasi Koyeb, dimana proses deployment akan dijalankan setelah proses CI pada setiap perubahan yang dilakukan pada branch master. 
  
Dengan demikian, berdasarkan implementasi yang telah saya lakukan, saya telah memenuhi prinsip CI/CD yang memastikan setiap ada perubahan kode maka akan dilakukan proses pengujian, analisis, dan deployment secara otomatis.
<hr>

</details>

<details>
<summary>Module 1</summary>
# Modul 1

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
- Melanggar prinsip Don't Repeat Yourself<br>
  Menulis ulang kode dapat mengurangi efisiensi program dan kualiatas kode secara keseluruhan. Kode yang berkualitas seharusnya mudah untuk dilakukan refactor dan reusable.<br>


  Solusi
- Dapat melakukan refactor dengan base class misalnya pada BaseFunctionalTest yang berisi setup prosedur yang umum. Kemudian, subclass test yang lainnya dapat mewarisi superclass pada base class tersebut.
- Dapat menggunakan helper method yang membantu dalam mereduksi fungsionalitas dari class yang dipanggil. Hal ini dapat membantu mengurangi duplikasi dan meningkatkan readability <br>

Dengan demikian, meskipun terdapat setup prosedur yang sama tidak langsung menurunkan kualitas kode. Dengan melakukana refactoring, kita dapat meningkatkan kualitas dan efisiensi kode sehingga dapat mempermudah kita dalam melakukan pemeliharaan dan melakukana update fitur kedepannya nanti.

</details>