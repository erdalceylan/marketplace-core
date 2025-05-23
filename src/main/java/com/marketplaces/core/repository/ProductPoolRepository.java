package com.marketplaces.core.repository;

import com.marketplaces.core.entity.ProductPool;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.QueryHints;

@Repository
public interface ProductPoolRepository extends JpaRepository<ProductPool, Long> {

    @QueryHints(value = @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true"))
    @Query("SELECT p FROM ProductPool p")
    Stream<ProductPool> streamAllProducts();
}
