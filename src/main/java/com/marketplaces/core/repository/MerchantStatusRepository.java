package com.marketplaces.core.repository;

import com.marketplaces.core.entity.MerchantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantStatusRepository extends JpaRepository<MerchantStatus, Short> {
}
