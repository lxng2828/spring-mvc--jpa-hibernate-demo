package com.example.app.model;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double price;

    private String color;

    /**
     * Mối quan hệ Nhiều-Một với Manufacture.
     * fetch=FetchType.EAGER: Mặc định cho @ManyToOne. Manufacture sẽ được tải cùng lúc với Phone.
     *
     * @JoinColumn: Chỉ định cột khóa ngoại trong bảng 'phones'.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacture_id", nullable = false)
    private Manufacture manufacture;

    // Constructors, Getters, and Setters...
    public Phone() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }
}
