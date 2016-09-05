package io.aif.language.token.comparator;

import java.util.Map;

public interface ITokenComparator {

  public static ITokenComparator createComposite(final Map<ITokenComparator, Double> comparators) {
    return new CompositeTokenComparator(comparators);
  }

  public static ITokenComparator defaultComparator() {
    return Type.CHARACTER_DENSITY_COMPARATOR.getInstance();
  }

  public Double compare(String left, String right);

  public static enum Type {
    SIMPLE_TOKEN_COMPARATOR(new SimpleTokenComparator()),
    RECURSIVE_SUBSTRING_COMPARATOR(new RecursiveSubstringComparator()),
    CHARACTER_DENSITY_COMPARATOR(new CharacterDensityTokenComparator()),
    LEVENSHTEIN_COMPARATOR(new LevenshteinDistanceComparator());

    private final ITokenComparator instance;

    private Type(final ITokenComparator instance) {
      this.instance = instance;
    }

    public ITokenComparator getInstance() {
      return instance;
    }
  }
}