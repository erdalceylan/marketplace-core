package com.marketplaces.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductPoolState {

    public enum State {
        PENDING((short) 1), ACTIVE((short) 2), REJECT((short) 3);
        private Short value;
        State(Short value) {
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
