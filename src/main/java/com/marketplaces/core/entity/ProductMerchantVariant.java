package com.marketplaces.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
public class ProductMerchantVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Merchant merchant;

    @ManyToOne
    private ProductPoolVariant productPoolVariant;

    @Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal originalPrice;

    @Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal discountedPrice;

    private Integer stockQuantity;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
