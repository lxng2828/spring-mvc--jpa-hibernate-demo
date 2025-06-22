package com.example.app.repository;


import com.example.app.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    // **BIỂU DIỄN QUERY METHOD**
    // Spring Data JPA sẽ tự động tạo truy vấn: SELECT * FROM phones WHERE price > ?
    List<Phone> findByPriceGreaterThan(double price);

    // **BIỂU DIỄN @Query VỚI JPQL**
    // Tìm kiếm điện thoại theo tên hoặc tên nhà sản xuất.
    @Query("SELECT p FROM Phone p WHERE lower(p.name) LIKE lower(concat('%', :keyword, '%'))" +
            " OR lower(p.manufacture.name) LIKE lower(concat('%', :keyword, '%'))")
    List<Phone> searchByKeyword(@Param("keyword") String keyword);

    // **BIỂU DIỄN @Query VỚI SQL THUẦN**
    @Query(value = "SELECT * FROM phones ORDER BY price DESC LIMIT 1", nativeQuery = true)
    Phone findMostExpensivePhone();
}
