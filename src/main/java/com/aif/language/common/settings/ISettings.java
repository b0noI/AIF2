package com.aif.language.common.settings;

public interface ISettings {

    public String getVersion();

    public ISettings SETTINGS = PropertyBasedSettings.createInstance();

}
