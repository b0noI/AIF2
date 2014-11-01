package io.aif.language.token;

public final class TokenMappers {

    public static String removeMultipleEndCharacters(final String token) {
        int index = token.length();
        while (index > 1 &&
                token.charAt(index - 1) == token.charAt(index - 2)) {
            index--;
        }
        if (index != token.length()) {
            return token.substring(0, index);
        }
        return token;
    }

}
