# TỪ KHÓA ĐẶT TÊN METHOD TRONG SPRING DATA JPA**

> Dùng để tạo method **tuỳ biến truy vấn** theo thuộc tính mà không cần viết SQL hay JPQL.

| **Nhóm từ khóa**      | **Từ khóa**                                         | **Mô tả / SQL tương ứng**                       |
| --------------------- | --------------------------------------------------- | ----------------------------------------------- |
| **Cơ bản**            | `findBy`, `getBy`, `readBy`, `queryBy`              | Bắt đầu method truy vấn                         |
| **So sánh**           | `Is`, `Equals`                                      | `WHERE field = ?`                               |
|                       | `Not`, `IsNot`                                      | `WHERE field != ?`                              |
|                       | `GreaterThan`, `GreaterThanEqual`                   | `>`, `>=`                                       |
|                       | `LessThan`, `LessThanEqual`                         | `<`, `<=`                                       |
|                       | `Between`                                           | `BETWEEN ? AND ?`                               |
|                       | `In`, `NotIn`                                       | `IN (...)`, `NOT IN (...)`                      |
| **Chuỗi**             | `Like`, `NotLike`                                   | `LIKE`, `NOT LIKE`                              |
|                       | `Containing`, `NotContaining`                       | `%?%`                                           |
|                       | `StartingWith`, `EndingWith`                        | `LIKE ?%`, `LIKE %?`                            |
|                       | `IgnoreCase`                                        | `LOWER(field)`                                  |
| **Null & Boolean**    | `IsNull`, `IsNotNull`                               | `IS NULL`, `IS NOT NULL`                        |
|                       | `True`, `False`                                     | `field = true/false`                            |
| **Tổ hợp điều kiện**  | `And`, `Or`                                         | `AND`, `OR`                                     |
| **Sắp xếp**           | `OrderBy`, `Asc`, `Desc`                            | `ORDER BY`                                      |
| **Giới hạn kết quả**  | `Top`, `First` (kèm sắp xếp)                        | `LIMIT n`                                       |
| **Distinct**          | `Distinct`                                          | `SELECT DISTINCT`                               |
| **Phân trang & sort** | Kết hợp với `Pageable`, `Sort` tham số trong method | `Page<T>`, `List<T>` có sắp xếp hoặc phân trang |

**Ví dụ thực tế:**

```java
List<User> findTop3ByAgeGreaterThanOrderByScoreDesc(int age);
List<User> findByEmailContainingIgnoreCase(String email);
boolean existsByPhoneNumber(String phone);
```

---

# CÁC METHOD ĐƯỢC SINH SẴN (KHÔNG CẦN VIẾT LẠI)**

> Những method có sẵn từ `JpaRepository<T, ID>`. Không cần định nghĩa lại trong interface.

| **Method**                                 | **Mô tả**                           | **Từ đâu có**                |
| ------------------------------------------ | ----------------------------------- | ---------------------------- |
| `T save(T entity)`                         | Thêm mới hoặc cập nhật              | `CrudRepository`             |
| `List<T> findAll()`                        | Lấy tất cả entity                   | `CrudRepository`             |
| `Optional<T> findById(ID id)`              | Tìm theo ID                         | `CrudRepository`             |
| `void deleteById(ID id)`                   | Xóa theo ID                         | `CrudRepository`             |
| `void delete(T entity)`                    | Xóa theo entity                     | `CrudRepository`             |
| `void deleteAll()`                         | Xóa toàn bộ                         | `CrudRepository`             |
| `long count()`                             | Đếm số lượng bản ghi                | `CrudRepository`             |
| `boolean existsById(ID id)`                | Kiểm tra tồn tại bản ghi            | `CrudRepository`             |
| `void deleteAll(Iterable<? extends T>)`    | Xóa nhiều bản ghi                   | `CrudRepository`             |
| `List<T> findAll(Sort sort)`               | Lấy tất cả có sắp xếp               | `PagingAndSortingRepository` |
| `Page<T> findAll(Pageable pageable)`       | Trả về theo phân trang              | `PagingAndSortingRepository` |
| `<S extends T> S saveAndFlush(S entity)`   | Lưu và gọi flush()                  | `JpaRepository`              |
| `List<T> findAllById(Iterable<ID> ids)`    | Lấy nhiều bản ghi theo danh sách ID | `CrudRepository`             |
| `void flush()`                             | Đồng bộ với DB ngay lập tức         | `JpaRepository`              |
| `void deleteInBatch(Iterable<T> entities)` | Xoá hàng loạt theo batch            | `JpaRepository`              |

**Không nên override hoặc định nghĩa lại các method trên.** Spring sẽ **tự động sinh** thông qua proxy.

---

## Tổng kết:

| Bạn cần gì?                         | Cách làm                          |
| ----------------------------------- | --------------------------------- |
| CRUD cơ bản?                        | `extends JpaRepository` là đủ     |
| Truy vấn theo điều kiện?            | Dùng method đặt tên đúng quy tắc  |
| Truy vấn phức tạp?                  | Dùng `@Query` hoặc native SQL     |
| Tùy biến logic repository nâng cao? | Dùng `CustomRepository` và `Impl` |
