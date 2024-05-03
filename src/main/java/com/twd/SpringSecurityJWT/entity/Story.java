package com.twd.SpringSecurityJWT.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Story")
public class Story{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  userName;
    private String productName;
    private int quantity;
    private int price;
    private LocalDateTime dateTime;
    private String type;

}
