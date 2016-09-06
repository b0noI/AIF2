package io.aif.language.token.separator;

import java.util.List;

import io.aif.language.common.IExtractor;
import io.aif.language.common.settings.ISettings;

public interface ITokenSeparatorExtractor extends IExtractor<String, List<Character>> {

  public static enum Type {

    PREDEFINED(new PredefinedTokenSeparatorExtractor(ISettings.SETTINGS)),
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
