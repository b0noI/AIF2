package io.aif.language.common.settings;

public interface ISettings {

    public String getVersion();

    public int recommendedMinimumTokensInputCount();

    public ISettings SETTINGS = PropertyBasedSettings.createInstance();

}
