package com.marketplaces.core.dto.response;

import lombok.Data;

@Data
public class SuggestDTO {
    public enum Type {
        TEXT("TEXT"), CATEGORY("CATEGORY"), BRAND("BRAND"), MERCHANT("MERCHANT");
        private String value;
        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    private String id;
    private String text;
    private Type type;
    private Long refId;
}
