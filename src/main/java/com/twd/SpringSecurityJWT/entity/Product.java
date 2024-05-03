package com.twd.SpringSecurityJWT.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String type;
    private int quantity;
    private int price;
    private int updatedAt;
    private String place;
    private LocalDateTime createdAt;


}
