package io.aif.language.word.dict;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import io.aif.language.word.IWord;

class Word implements IWord {

  private final String rootToken;
  private final Long count;
  private ConcurrentMap<String, AtomicInteger> tokens = new ConcurrentHashMap<>();

  Word(final String rootToken, final Collection<String> tokens, final Long count) {
    this.rootToken = rootToken;
    this.count = count;

    tokens.forEach(token -> add(token));
  }

  public void add(String token) {
    // useful method that exists only in concurrent maps
    tokens.putIfAbsent(token, new AtomicInteger(0));
    tokens.get(token).incrementAndGet();
  }

  @Override
  public String getRootToken() {
    return rootToken;
  }

  @Override
  public Set<String> getAllTokens() {
    return tokens.keySet();
  }

  @Override
  public Long getCount() {
    return count;
  }

  /**
   * Returns how many times the token present in Word
   *
   * @return How many times the token present in Word
   */
  @Override
  public int getTokenCount(String token) {
    if (tokens.get(token) != null) {
      return tokens.get(token).get();
    } else return 0;
  }

  //TODO Need to override equals and hash

  @Override
  public String toString() {
    return String.format("RootToken: [%s] tokens: [%s]", rootToken, tokens);
  }

  public class WordPlaceholder implements IWordPlaceholder {

    private final String token;

    public WordPlaceholder(String token) {
      this.token = token;
    }

    @Override
    public IWord getWord() {
      return Word.this;
    }

    @Override
    public String getToken() {
      return token;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof WordPlaceholder)) return false;

      WordPlaceholder that = (WordPlaceholder) o;

      return (!token.equals(that.token) || !this.getWord().equals(that.getWord()));
    }

    @Override
    public int hashCode() {
      //TODO Is this good enough.
      int result = 17;
      result = 37 * result + token.hashCode();
      result = 37 * result + this.getWord().hashCode();
      return result;
    }

  }
}
