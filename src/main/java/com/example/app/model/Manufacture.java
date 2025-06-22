package com.example.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufactures")
public class Manufacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    /**
     * Mối quan hệ Một-Nhiều với Phone.
     * mappedBy="manufacture": Chỉ ra rằng mối quan hệ này được quản lý bởi trường 'manufacture' trong class Phone.
     * cascade=CascadeType.ALL: Các thao tác (persist, remove, refresh, merge, detach) sẽ được lan truyền tới các Phone liên quan.
     * orphanRemoval=true: Nếu một Phone bị xóa khỏi danh sách 'phones' này, nó cũng sẽ bị xóa khỏi CSDL.
     * fetch=FetchType.LAZY: **Rất quan trọng!** Danh sách 'phones' sẽ chỉ được tải từ CSDL khi có lời gọi tới nó (ví dụ: manufacture.getPhones()).
     * Đây là cách mặc định và được khuyến khích để tránh vấn đề N+1.
     */
    @OneToMany(mappedBy = "manufacture", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Phone> phones = new ArrayList<>();

    // Constructors, Getters, and Setters...
    public Manufacture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}


