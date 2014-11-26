package io.aif.language.common.settings;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertyBasedSettings implements ISettings {

    private static  final String VERSION_PROPERTIES_KEY = "version";

    private static  final String PROPERTIES_FILE_NAME = "/io/aif/common/settings/main.properties";

    private static  final String MINIMUM_TOKENS_INPUT_COUNT_KEY = "minimum_tokens_input_count";

    private static final String USE_IS_ALPHABETIC_METHOD_KEY = "use_is_alphabetic_method";

    private         final Properties    properties = new Properties();

    public static PropertyBasedSettings createInstance() {
        try (final InputStream is = ISettings.class.getClass().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            return new PropertyBasedSettings(is);
        } catch (IOException e) {
            throw new ValueException(e.getMessage());
        }
    }

    private PropertyBasedSettings(final InputStream is) throws IOException {
        properties.load(is);
    }

    @Override
    public String getVersion() {
        return properties.getProperty(VERSION_PROPERTIES_KEY);
    }

    @Override
    public int recommendedMinimumTokensInputCount() {
        return Integer.valueOf(properties.getProperty(MINIMUM_TOKENS_INPUT_COUNT_KEY));
    }

    @Override
    public boolean useIsAlphabeticMethod() {
        return Boolean.valueOf(properties.getProperty(USE_IS_ALPHABETIC_METHOD_KEY));
    }

}
