package io.aif.language.common;

import java.util.List;

import ru.lanwen.verbalregex.VerbalExpression;

import static java.util.stream.Collectors.joining;

public class RegexpCooker implements IRegexpCooker {

  @Override
  public String prepareRegexp(final List<Character> characters) {
    final String joinedCharacters = characters.stream().map(Object::toString).collect(joining());
    return VerbalExpression.regex()
        .anyOf(joinedCharacters).oneOrMore()
        .build()
        .toString();
  }
}
