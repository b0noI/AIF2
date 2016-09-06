package io.aif.language.sentence.separators.classificators;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class PredefinedSeparatorGroupsClassifierTest {

  @Test
  public void testClassify() throws Exception {
    Set<Character> inputWithDotChar = new HashSet<>(Arrays.asList('!', '.', '?'));
    Set<Character> inputWithoutDotChar = new HashSet<>(Arrays.asList(',', ':', ';'));

    Map<ISeparatorGroupsClassifier.Group, Set<Character>> expected = new HashMap<>();
    expected.put(ISeparatorGroupsClassifier.Group.GROUP_1, inputWithDotChar);
    expected.put(ISeparatorGroupsClassifier.Group.GROUP_2, inputWithoutDotChar);

    List<String> inputTokens = new ArrayList<>();
    List<Set<Character>> seperatorGroupsInput = new ArrayList<>();
    seperatorGroupsInput.add(inputWithDotChar);
    seperatorGroupsInput.add(inputWithoutDotChar);

    ISeparatorGroupsClassifier classifier = new PredefinedSeparatorGroupsClassifier();
    Map<ISeparatorGroupsClassifier.Group, Set<Character>> actual = classifier.classify(
        inputTokens, seperatorGroupsInput);
    assertEquals(expected, actual);
  }

  @Test
  public void testClassifyOnlyDottedCharacters() throws Exception {
    Set<Character> inputWithDotChar1 = new HashSet<>(Arrays.asList('!', '.', '?'));
    Set<Character> inputWithDotChar2 = new HashSet<>(Arrays.asList('!', '.', ';'));

    Map<ISeparatorGroupsClassifier.Group, Set<Character>> expected = new HashMap<>();
    expected.put(ISeparatorGroupsClassifier.Group.GROUP_1, inputWithDotChar1);
    expected.get(ISeparatorGroupsClassifier.Group.GROUP_1)
        .addAll(inputWithDotChar2);
    expected.put(ISeparatorGroupsClassifier.Group.GROUP_2, new HashSet<>());

    List<String> inputTokens = new ArrayList<>();
    List<Set<Character>> seperatorGroupsInput = new ArrayList<>();
    seperatorGroupsInput.add(inputWithDotChar1);
    seperatorGroupsInput.add(inputWithDotChar2);

    ISeparatorGroupsClassifier classifier = new PredefinedSeparatorGroupsClassifier();
    Map<ISeparatorGroupsClassifier.Group, Set<Character>> actual = classifier.classify(
        inputTokens, seperatorGroupsInput);
    assertEquals(expected, actual);
  }

  @Test
  public void testClassifyNoDottedCharacters() throws Exception {
    Set<Character> inputWithoutDotChar1 = new HashSet<>(Arrays.asList(',', ':', ';'));
    Set<Character> inputWithoutDotChar2 = new HashSet<>(Arrays.asList('^', '/', '&'));

    Map<ISeparatorGroupsClassifier.Group, Set<Character>> expected = new HashMap<>();
    expected.put(ISeparatorGroupsClassifier.Group.GROUP_1, new HashSet<>());
    expected.put(ISeparatorGroupsClassifier.Group.GROUP_2, inputWithoutDotChar1);
    expected.get(ISeparatorGroupsClassifier.Group.GROUP_2)
        .addAll(inputWithoutDotChar2);

    List<String> inputTokens = new ArrayList<>();
    List<Set<Character>> seperatorGroupsInput = new ArrayList<>();
    seperatorGroupsInput.add(inputWithoutDotChar1);
    seperatorGroupsInput.add(inputWithoutDotChar2);

    ISeparatorGroupsClassifier classifier = new PredefinedSeparatorGroupsClassifier();
    Map<ISeparatorGroupsClassifier.Group, Set<Character>> actual = classifier.classify(
        inputTokens, seperatorGroupsInput);
    assertEquals(expected, actual);
  }
}