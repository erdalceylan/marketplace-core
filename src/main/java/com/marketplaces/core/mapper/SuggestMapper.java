package com.marketplaces.core.mapper;

import com.marketplaces.core.dto.response.SuggestDTO;
import com.marketplaces.core.helper.SecurityHelper;
import org.springframework.stereotype.Service;

@Service
public class SuggestMapper {
    public SuggestDTO convert(Long refId, SuggestDTO.Type type, String text) {
        SuggestDTO suggestDTO = new SuggestDTO();
        suggestDTO.setRefId(refId);
        suggestDTO.setType(type);
        suggestDTO.setText(text);
        if (suggestDTO.getType().equals(SuggestDTO.Type.TEXT)) {
            suggestDTO.setId(SecurityHelper.md5(text.replaceAll("[^\\p{L}]", "")));
        } else {
            suggestDTO.setId(SecurityHelper.md5(type.getValue() + refId.toString()));
        }

        return suggestDTO;
    }
}
