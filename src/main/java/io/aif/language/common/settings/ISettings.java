package io.aif.language.common.settings;

public interface ISettings {

    public ISettings SETTINGS = PropertyBasedSettings.createInstance();

    public String getVersion();

    public int recommendedMinimumTokensInputCount();

    public boolean useIsAlphabeticMethod();

    public double separatorProbabilityThreshold();

}
