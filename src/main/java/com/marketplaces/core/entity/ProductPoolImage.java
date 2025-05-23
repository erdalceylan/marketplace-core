package com.marketplaces.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class ProductPoolImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String root;

    @Column(length = 128)
    private String folder;

    @Column(length = 128)
    private String fileName;

    @Column(length = 128)
    private String largeFileName;

    @ManyToOne
    private ProductPool productPool;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}