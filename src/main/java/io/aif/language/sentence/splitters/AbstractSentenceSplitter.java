package io.aif.language.sentence.splitters;

import com.google.common.annotations.VisibleForTesting;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import io.aif.language.common.ISplitter;
import io.aif.language.common.settings.ISettings;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.sentence.separators.extractors.ISeparatorExtractor;
import io.aif.language.sentence.separators.groupers.ISeparatorsGrouper;

public abstract class AbstractSentenceSplitter implements ISplitter<List<String>, List<String>> {

  private static final Logger logger = Logger.getLogger(AbstractSentenceSplitter.class);

  private final ISeparatorExtractor sentenceSeparatorExtractor;

  private final ISeparatorsGrouper sentenceSeparatorsGrouper;

  private final ISeparatorGroupsClassifier sentenceSeparatorGroupsClassificatory;

  protected AbstractSentenceSplitter(final ISeparatorExtractor sentenceSeparatorExtractor,
                                     final ISeparatorsGrouper sentenceSeparatorsGrouper,
                                     final ISeparatorGroupsClassifier
                                         sentenceSeparatorGroupsClassificatory) {
    this.sentenceSeparatorExtractor = sentenceSeparatorExtractor;
    this.sentenceSeparatorsGrouper = sentenceSeparatorsGrouper;
    this.sentenceSeparatorGroupsClassificatory = sentenceSeparatorGroupsClassificatory;
  }

  @VisibleForTesting
  static List<String> prepareSentences(final List<String> sentence,
                                       final List<Character> separators) {
    final List<String> preparedTokens = new ArrayList<>();

    for (String token : sentence) {
      preparedTokens.addAll(prepareToken(token, separators));
    }

    return preparedTokens;
  }

  @VisibleForTesting
  static List<String> prepareToken(final String token, final List<Character> separators) {
    final List<String> tokens = new ArrayList<>(3);
    final int lastPosition = lastNonSeparatorPosition(token, separators);
    final int firstPosition = firstNonSeparatorPosition(token, separators);

    if (firstPosition != 0) {
      tokens.add(token.substring(0, firstPosition));
    }

    tokens.add(token.substring(firstPosition, lastPosition));

    if (lastPosition != token.length()) {
      tokens.add(token.substring(lastPosition, token.length()));
    }

    return tokens;
  }

  @VisibleForTesting
  static int firstNonSeparatorPosition(final String token, final List<Character> separarors) {
    if (!separarors.contains(token.charAt(0))) {
      return 0;
    }
    int i = 0;
    while (i < token.length() && separarors.contains(token.charAt(i))) {
      i++;
    }
    if (i == token.length()) {
      return 0;
    }
    return i;
  }

  @VisibleForTesting
  static int lastNonSeparatorPosition(final String token, final List<Character> separators) {
    if (!separators.contains(token.charAt(token.length() - 1))) {
      return token.length();
    }
    int i = token.length() - 1;
    while (i > 0 && separators.contains(token.charAt(i))) {
      i--;
    }
    i++;
    if (i == 0) {
      return token.length();
    }
    return i;
  }

  public List<List<String>> split(final List<String> tokens) {

    if (tokens.size() <= ISettings.SETTINGS.recommendedMinimumTokensInputCount()) {
      logger.warn(
          String.format("Tokens input count is too low: %d, recommend count is: %d. Don't expect " +
                  "good quality of output",
              tokens.size(),
              ISettings.SETTINGS.recommendedMinimumTokensInputCount()));
    }

    logger.debug(String.format("Starting sentence extraction for tokens: %d", tokens.size()));
    final Optional<List<Character>> optionalSeparators = sentenceSeparatorExtractor.extract(tokens);

    if (!optionalSeparators.isPresent() || optionalSeparators.get().size() == 0) {
      logger.error("Fail to extract any sentence separators, returning tokens");
      return new ArrayList<List<String>>() {{
        add(tokens);
      }};
    }

    final List<Character> separators = optionalSeparators.get();
    logger.debug(
        String.format("Sentences separators in this text: %s",
            Arrays.toString(separators.toArray())));

    final List<Set<Character>> separatorsGroups =
        sentenceSeparatorsGrouper.group(tokens, separators);
    final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separatorsGroupsClassified =
        sentenceSeparatorGroupsClassificatory.classify(tokens, separatorsGroups);
    final List<Boolean> booleans = split(tokens, separatorsGroupsClassified);
    final SentenceIterator sentenceIterator = new SentenceIterator(tokens, booleans);
    final List<List<String>> sentences = new ArrayList<>();
    while (sentenceIterator.hasNext()) {
      sentences.add(sentenceIterator.next());
    }

    logger.debug(String.format("Founded %d sentences", sentences.size()));

    return sentences
        .parallelStream()
        .map(sentence -> prepareSentences(sentence, separators))
        .collect(Collectors.toList());
  }

  public abstract List<Boolean> split(final List<String> target,
                                      final Map<ISeparatorGroupsClassifier.Group, Set<Character>>
                                          splitters);

  public enum Type {

    SIMPLE(new SimpleSentenceSplitter()),
    HEURISTIC(new HeuristicSentenceSplitter());

    private final AbstractSentenceSplitter instance;

    Type(AbstractSentenceSplitter instance) {
      this.instance = instance;
    }

    public AbstractSentenceSplitter getInstance() {
      return instance;
    }

  }

  @VisibleForTesting
  static class SentenceIterator implements Iterator<List<String>> {

    private final List<String> tokens;

    private final List<Boolean> endTokens;

    private int currentPosition = 0;

    public SentenceIterator(List<String> tokens, List<Boolean> endTokens) {
      assert tokens != null;
      assert endTokens != null;
      assert tokens.size() == endTokens.size();
      this.tokens = tokens;
      this.endTokens = endTokens;
    }

    @Override
    public boolean hasNext() {
      return currentPosition != tokens.size();
    }

    @Override
    public List<String> next() {
      final List<String> sentence = getNextSentence();

      return sentence;
    }

    private List<String> getNextSentence() {
      final int oldIndex = currentPosition;
      currentPosition = getNextTrueIndex();
      return this.tokens.subList(oldIndex, currentPosition);
    }

    private int getNextTrueIndex() {
      int startIndex = currentPosition;

      if (endTokens.size() == startIndex) {
        return startIndex;
      }

      if (endTokens.size() == startIndex + 1) {
        return startIndex + 1;
      }

      do {
        if (endTokens.get(startIndex)) {
          startIndex++;
          return startIndex;
        }
        startIndex++;
      } while (startIndex < endTokens.size() - 1);
      return startIndex + 1;
    }

  }

}
