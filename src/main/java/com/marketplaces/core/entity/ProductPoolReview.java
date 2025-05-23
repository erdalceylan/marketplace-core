package com.marketplaces.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Data
public class ProductPoolReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CustomerUser customerUser;

    @ManyToOne
    private ProductPool productPool;

    @ManyToOne
    private Merchant merchant;

    @Column(length = 2048)
    private String text;

    private Byte rate;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
