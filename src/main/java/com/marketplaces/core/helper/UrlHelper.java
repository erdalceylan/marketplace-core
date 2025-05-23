package com.marketplaces.core.helper;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class UrlHelper {

    static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String... input) {
        var joinedInput = org.apache.commons.lang3.StringUtils.join(input, " ");
        String noWhitespace = WHITESPACE.matcher(joinedInput).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
