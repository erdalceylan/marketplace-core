package com.marketplaces.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(length = 512)
    private String fullName;
}
