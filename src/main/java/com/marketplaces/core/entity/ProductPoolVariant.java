package com.marketplaces.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ProductPoolVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductPool productPool;

    @Column(nullable = false, length = 64)
    private String barcode;

    @ManyToOne
    private ProductPoolVariantOptionValue optionValue;

    @OneToMany(mappedBy = "productPoolVariant")
    private List<ProductMerchantVariant> merchantVariants = new ArrayList<>();

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
