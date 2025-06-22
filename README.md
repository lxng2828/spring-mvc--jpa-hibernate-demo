#### **1. Ánh xạ Entity (`@Entity`)**

Đây là những annotation cốt lõi bạn dùng để "dạy" cho JPA cách ánh xạ một lớp Java thành một bảng trong CSDL.

| Annotation | Mô tả | Ví dụ sử dụng (từ file tài liệu) |
| :--- | :--- | :--- |
| **`@Entity`** | Đánh dấu một lớp Java là một JPA entity, sẵn sàng để được quản lý và ánh xạ. | `@Entity public class Phone { ... }` |
| **`@Table`** | (Tùy chọn) Chỉ định tên của bảng trong CSDL. Nếu không có, tên bảng sẽ là tên lớp (phân biệt chữ hoa/thường tùy CSDL). | `@Table(name = "phones")` |
| **`@Id`** | Đánh dấu một trường là khóa chính (primary key) của bảng. | `@Id private Long id;` |
| **`@GeneratedValue`** | Cấu hình cách giá trị của khóa chính được sinh tự động. `IDENTITY` là lựa chọn phổ biến, dựa vào cột auto-increment của CSDL. | `@GeneratedValue(strategy = GenerationType.IDENTITY)` |
| **`@Column`** | (Tùy chọn) Ánh xạ một trường tới một cột và định nghĩa các thuộc tính của nó. | `@Column(nullable = false, length = 100)` |
| **`@ManyToOne`, `@OneToMany`, etc.** | Định nghĩa các mối quan hệ giữa các entity. | `@ManyToOne private Manufacture manufacture;` |

---

#### **2. Phương thức Repository**

`JpaRepository` đã cho bạn sức mạnh ngay từ đầu. Hãy làm chủ các phương thức có sẵn và quy ước đặt tên để tự tạo truy vấn.

| Loại phương thức | Tên phương thức / Quy ước | Mô tả |
| :--- | :--- | :--- |
| **Thao tác cơ bản (Có sẵn)** | `save(T entity)` | Lưu mới một entity hoặc cập nhật nếu đã tồn tại (upsert). |
| | `findById(ID id)` | Tìm một entity theo khóa chính, trả về `Optional<T>`. |
| | `findAll()` | Lấy tất cả các entity. |
| | `deleteById(ID id)` | Xóa một entity theo khóa chính. |
| | `count()` | Đếm tổng số entity. |
| **Query Methods (Tự định nghĩa)** | `findBy<TênThuộcTính>(...)` | Tìm kiếm dựa trên một thuộc tính. Ví dụ: `findByName(String name)`. |
| | `findBy<ThuộcTính1>And<ThuộcTính2>(...)` | Tìm kiếm kết hợp nhiều điều kiện bằng toán tử `AND`. |
| | `findBy<ThuộcTính>GreaterThan(...)` | Tìm kiếm với điều kiện "lớn hơn". Ví dụ: `findByPriceGreaterThan(double price)`. |
| | `findBy<ThuộcTính>Like(...)` | Tìm kiếm với điều kiện `LIKE` trong SQL. |
| | `findBy<ThuộcTínhLiênKết>_<TênThuộcTính>(...)` | Truy vấn dựa trên thuộc tính của một entity liên kết. Ví dụ: `findByManufactureNameIgnoreCase(...)`. |

---

#### **3. Truy vấn Tùy chỉnh (`@Query`)**

Khi Query Methods không đủ đáp ứng, `@Query` cho phép bạn toàn quyền kiểm soát.

| Loại Truy vấn | Cú pháp & Chú thích | Ví dụ (từ file tài liệu) |
| :--- | :--- | :--- |
| **JPQL (Java Persistence Query Language)** | Sử dụng tên Entity và thuộc tính của đối tượng Java. Tham số được đặt tên (`:paramName`). | ` @Query("SELECT p FROM Phone p WHERE p.name LIKE %:keyword%") List<Phone> searchByKeyword(@Param("keyword") String keyword); ` |
| **SQL Thuần (Native SQL)** | Sử dụng cú pháp SQL thật của CSDL. Bắt buộc phải có `nativeQuery = true`. Tham số được đánh chỉ mục (`?1`, `?2`). | ` @Query(value = "SELECT * FROM phones WHERE price BETWEEN ?1 AND ?2", nativeQuery = true) List<Phone> findByPriceRange(double minPrice, double maxPrice); ` |

---

#### **4. Nguyên tắc**

Đây không phải là syntax, mà là những quy tắc bạn *phải* tuân theo để viết code Spring Data JPA hiệu quả, an toàn và dễ bảo trì.

| Nguyên tắc | Lý do & Cách áp dụng |
| :--- | :--- |
| **1. Quản lý Giao dịch** | **Luôn đặt `@Transactional`** trên các phương thức ở tầng Service mà có thực hiện thao tác ghi (lưu, cập nhật, xóa). Điều này đảm bảo tất cả các thao tác trong phương thức hoặc thành công cùng nhau, hoặc thất bại cùng nhau, giữ cho dữ liệu toàn vẹn. |
| **2. Sử dụng `Optional<T>`** | Khi một phương thức truy vấn có thể không trả về kết quả (như `findById`), hãy để kiểu trả về là `Optional<T>`. Việc này bắt buộc bạn phải xử lý trường hợp `null` một cách tường minh, tránh lỗi `NullPointerException`. |
| **3. Ưu tiên Query Methods** | **Luôn thử dùng Query Methods trước** khi nghĩ đến `@Query`. Chúng an toàn hơn (được kiểm tra lúc biên dịch), trong sáng hơn và ít bị ràng buộc vào CSDL cụ thể. |
| **4. Cẩn trọng với FetchType** | **Ưu tiên `FetchType.LAZY`** cho các quan hệ tập hợp (`@OneToMany`, `@ManyToMany`). `EAGER` có thể gây ra vấn đề hiệu năng nghiêm trọng (N+1 Select) bằng cách tải một lượng lớn dữ liệu không cần thiết. |
| **5. Giải quyết N+1 Select** | Khi bạn cần tải một danh sách các entity cùng với các entity liên quan của chúng, hãy sử dụng `JOIN FETCH` trong một câu lệnh `@Query`. Việc này gộp nhiều câu lệnh SQL thành một, cải thiện đáng kể hiệu năng. |
| **6. Tận dụng Autoconfiguration** | **Luôn ưu tiên cấu hình trong `application.properties`** của Spring Boot. Tránh việc tạo các Bean cấu hình `DataSource` hay `EntityManagerFactory` thủ công trừ khi có lý do đặc biệt. |
