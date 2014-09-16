package com.aif.language.common.settings;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertyBasedSettings implements ISettings {

    private static  final String        VERSION_PROPERTIES_KEY = "version";

    private static  final String        PROPERTIES_FILE_NAME = "/com/aif/common/settings/main.properties";

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

}
