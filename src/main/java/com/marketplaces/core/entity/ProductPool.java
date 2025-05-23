package com.marketplaces.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ProductPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String title;

    @Column(nullable = false, length = 1024*64)
    private String description;

    @Column(nullable = false, length = 1024)
    private String slug;

    @Column(length = 64)
    private String modelCode;

    private Float rate;

    @ManyToOne
    private ProductPoolState state;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    ProductPoolColor color;

    @ManyToOne
    ProductPoolGender gender;

    @OneToMany(mappedBy = "productPool")
    List<ProductPoolImage> images = new ArrayList<>();

    @ManyToMany()
    @JoinTable(name = "product_pool_attribute_value_map")
    private List<ProductPoolAttributeValue> attributeValues = new ArrayList<>();

    @OneToMany(mappedBy = "productPool")
    List<ProductPoolVariant> variants = new ArrayList<>();

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
