package com.example.app.service;


import com.example.app.model.Manufacture;
import com.example.app.model.Phone;
import com.example.app.repository.ManufactureRepository;
import com.example.app.repository.PhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final ManufactureRepository manufactureRepository;

    public PhoneService(PhoneRepository phoneRepository, ManufactureRepository manufactureRepository) {
        this.phoneRepository = phoneRepository;
        this.manufactureRepository = manufactureRepository;
    }

    // **BIỂU DIỄN @Transactional**
    // Đảm bảo tất cả các thao tác trong phương thức này đều nằm trong một giao dịch.
    @Transactional
    public Phone save(Phone phone, Long manufactureId) {
        Manufacture manufacture = manufactureRepository.findById(manufactureId)
                .orElseThrow(() -> new RuntimeException("Manufacture not found"));
        phone.setManufacture(manufacture);
        return phoneRepository.save(phone);
    }

    @Transactional
    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }

    // **BIỂU DIỄN Optional<T>**
    // Kiểu trả về Optional giúp xử lý trường hợp không tìm thấy một cách an toàn.
    @Transactional(readOnly = true)
    public Optional<Phone> findById(Long id) {
        return phoneRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Manufacture> findAllManufactures() {
        return manufactureRepository.findAll();
    }

    // **TÁI HIỆN VẤN ĐỀ N+1**
    // Lời gọi này sẽ gây ra N+1 query khi view cố gắng truy cập list phones của mỗi manufacture.
    @Transactional(readOnly = true)
    public List<Manufacture> getManufacturesNaively() {
        System.out.println("\n--- BAT DAU LAY DANH SACH MANUFACTURE (GAY RA N+1) ---\n");
        return manufactureRepository.findAll();
    }

    // **SỬ DỤNG PHƯƠNG THỨC ĐÃ TỐI ƯU**
    @Transactional(readOnly = true)
    public List<Manufacture> getManufacturesOptimized() {
        System.out.println("\n--- BAT DAU LAY DANH SACH MANUFACTURE (DA TOI UU JOIN FETCH) ---\n");
        return manufactureRepository.findAllWithPhones();
    }

    @Transactional(readOnly = true)
    public List<Phone> search(String keyword) {
        return phoneRepository.searchByKeyword(keyword);
    }
}