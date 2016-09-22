package io.aif.language.token.separator;

import com.google.inject.Guice;

import java.util.List;

import io.aif.language.common.IExtractor;
import io.aif.language.common.settings.ISettings;
import io.aif.language.common.settings.SettingsModule;

public interface ITokenSeparatorExtractor extends IExtractor<String, List<Character>> {
  final ISettings settings = Guice.createInjector(new SettingsModule()).getInstance(ISettings.class);
  public static enum Type {

    PREDEFINED(new PredefinedTokenSeparatorExtractor(settings)),
    PROBABILITY(new ProbabilityBasedTokenSeparatorExtractor());

    private final ITokenSeparatorExtractor instance;

    private Type(final ITokenSeparatorExtractor instance) {
      this.instance = instance;
    }

    public ITokenSeparatorExtractor getInstance() {
      return instance;
    }

  }

}
