package com.marketplaces.core.repository;

import com.marketplaces.core.entity.MerchantUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantUserStatusRepository extends JpaRepository<MerchantUserStatus, Short> {
}
