package com.marketplaces.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MerchantUserStatus {
    public enum Status {
        PENDING((short) 1), ACTIVE((short) 2);
        private Short value;
        Status(Short value) {
            this.value = value;
        }

        public Short getValue() {
            return value;
        }
    }

    @Id
    private Short id;

    private String name;
}
