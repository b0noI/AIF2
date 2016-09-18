package io.aif.language.common.settings;

import com.google.inject.Guice;

public interface ISettings {

  @Deprecated
  public ISettings SETTINGS = Guice.createInjector(new SettingsModule()).getInstance(ISettings.class);

  public String getVersion();

  public int recommendedMinimumTokensInputCount();

  public boolean useIsAlphabeticMethod();

  public double thresholdPForSeparatorCharacterInSecondFilter();

  public int minimalValuableTokenSizeForSentenceSplit();

  public int minimumCharacterObservationsCountForMakingCharacterValuableDuringSentenceSplitting();

  public double thresholdPFirstFilterForSeparatorCharacter();

  public double splitterCharactersGrouperSearchStep();

  public double splitterCharactersGrouperInitSearchPValue();

  public double wordSetDictComparatorThreshold();

  public double recursiveSubstringComparatorWeight();

  public double simpleTokenComparatorWeight();

  public double characterDensityComparatorWeight();

  public String predefinedSeparators();
}
