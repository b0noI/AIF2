package io.aif.language.common;

import java.util.List;

public interface IRegexpCooker {

  String prepareRegexp(final List<Character> characters);
}
