package com.example.app.repository;

import com.example.app.model.Manufacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufactureRepository extends JpaRepository<Manufacture, Long> {

    /**
     * **GIẢI QUYẾT VẤN ĐỀ N+1 SELECT**
     * Sử dụng 'JOIN FETCH' để báo Hibernate lấy cả Manufacture và danh sách Phone liên quan trong CÙNG MỘT CÂU TRUY VẤN.
     * Điều này tránh việc phải thực hiện N câu truy vấn con để lấy danh sách phones cho mỗi manufacture.
     */
    @Query("SELECT DISTINCT m FROM Manufacture m JOIN FETCH m.phones")
    List<Manufacture> findAllWithPhones();
}