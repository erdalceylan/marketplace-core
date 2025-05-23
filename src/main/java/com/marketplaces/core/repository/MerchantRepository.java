package com.marketplaces.core.repository;

import com.marketplaces.core.entity.Brand;
import com.marketplaces.core.entity.Merchant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    public Optional<Merchant> findFirstByName(final String name);

    @Query("SELECT m FROM Merchant m ORDER BY m.id ASC")
    List<Merchant> getTopList(Pageable pageable);
}
