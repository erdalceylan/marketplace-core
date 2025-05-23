package com.marketplaces.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductPoolGender {

    public enum Type {
        WOMAN((byte) 1), MAN((byte) 2), CHILD((byte) 3);
        private Byte value;
        Type(Byte value) {
            this.value = value;
        }

        public Byte getValue() {
            return value;
        }
    }

    @Id
    private Byte id;

    private String name;
}
