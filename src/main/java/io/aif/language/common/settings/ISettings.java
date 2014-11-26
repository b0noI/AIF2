package io.aif.language.common.settings;

public interface ISettings {

    public String getVersion();

    public int recommendedMinimumTokensInputCount();

    public boolean useIsAlphabeticMethod();

    public ISettings SETTINGS = PropertyBasedSettings.createInstance();

}
