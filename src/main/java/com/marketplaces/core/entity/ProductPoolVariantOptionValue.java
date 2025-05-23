package com.marketplaces.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductPoolVariantOptionValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private ProductPoolVariantOption option;
}
