package com.marketplaces.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(nullable = false, length = 256)
    private String name;

    @Column(length = 8)
    private String code;
}
