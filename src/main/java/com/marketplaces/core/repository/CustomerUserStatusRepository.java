package com.marketplaces.core.repository;

import com.marketplaces.core.entity.CustomerUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerUserStatusRepository extends JpaRepository<CustomerUserStatus, Short> {
}
