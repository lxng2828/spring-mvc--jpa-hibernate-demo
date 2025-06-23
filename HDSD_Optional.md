## 1. Optional là gì?

`Optional<T>` là một lớp bọc (wrapper) cho một giá trị **có thể có hoặc không**. Mục tiêu là **tránh lỗi `NullPointerException`** và buộc bạn xử lý rõ ràng khi dữ liệu có thể bị null.

---

## 2. Cách tạo `Optional`

| Cách tạo                     | Mô tả                                     | Ví dụ                            |
| ---------------------------- | ----------------------------------------- | -------------------------------- |
| `Optional.of(value)`         | Tạo Optional có giá trị (không được null) | `Optional.of("abc")`             |
| `Optional.ofNullable(value)` | Chấp nhận giá trị null                    | `Optional.ofNullable(maybeNull)` |
| `Optional.empty()`           | Tạo Optional rỗng                         | `Optional.empty()`               |

---

## 3. Sử dụng phổ biến

| Mục đích                           | Cách dùng              | Ví dụ                                                        |
| ---------------------------------- | ---------------------- | ------------------------------------------------------------ |
| Kiểm tra có giá trị                | `isPresent()`          | `if (opt.isPresent()) {...}`                                 |
| Nếu có thì dùng                    | `ifPresent(...)`       | `opt.ifPresent(v -> xử lý(v))`                               |
| Lấy giá trị                        | `get()`                | `T value = opt.get()` (**không nên dùng nếu chưa kiểm tra**) |
| Nếu rỗng thì dùng giá trị mặc định | `orElse(...)`          | `opt.orElse("giá trị mặc định")`                             |
| Nếu rỗng thì gọi hàm sinh giá trị  | `orElseGet(() -> ...)` | `opt.orElseGet(() -> sinh())`                                |
| Nếu rỗng thì ném lỗi               | `orElseThrow()`        | `opt.orElseThrow()`                                          |

---

## 4. Ví dụ thực tế trong Spring JPA

```java
Optional<Customer> opt = customerRepository.findById(1L);

if (opt.isPresent()) {
    Customer c = opt.get();
    System.out.println(c.getName());
} else {
    System.out.println("Không tìm thấy");
}
```

Viết gọn hơn:

```java
customerRepository.findById(1L)
    .ifPresent(customer -> System.out.println(customer.getName()));
```

Hoặc:

```java
String name = customerRepository.findById(1L)
    .map(Customer::getName)
    .orElse("Ẩn danh");
```

---

## 5. Bảng tổng hợp nhanh

| Mục đích                     | Method                     |
| ---------------------------- | -------------------------- |
| Tạo Optional có giá trị      | `Optional.of(...)`         |
| Có thể null                  | `Optional.ofNullable(...)` |
| Rỗng                         | `Optional.empty()`         |
| Kiểm tra có dữ liệu          | `isPresent()`              |
| Lấy dữ liệu                  | `get()`                    |
| Mặc định nếu rỗng            | `orElse(...)`              |
| Tạo nếu rỗng                 | `orElseGet(...)`           |
| Ném lỗi nếu rỗng             | `orElseThrow(...)`         |
| Gọi hàm nếu có               | `ifPresent(...)`           |
| Chuyển đổi giá trị bên trong | `map(...)`                 |

