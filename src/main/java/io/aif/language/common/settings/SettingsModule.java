package io.aif.language.common.settings;

import com.google.inject.AbstractModule;

public class SettingsModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ISettings.class).toProvider(new PropertyBasedSettings.Provider());
  }

}
