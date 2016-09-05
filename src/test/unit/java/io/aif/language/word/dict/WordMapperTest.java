package io.aif.language.word.dict;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import io.aif.language.word.IWord;

import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class WordMapperTest {

  @Test(groups = "unit-tests")
  public void testMap() throws Exception {
    Set<String> inputTokenGroup = new HashSet<>(Arrays.asList("heya", "hey", "hiya"));
    IWord expected = new Word("heya", inputTokenGroup, (long) inputTokenGroup.size());

    RootTokenExtractor rootTokenExtractorMock = mock(RootTokenExtractor.class);
    when(rootTokenExtractorMock.extract(anySet())).thenReturn(Optional.of("heya"));

    WordMapper wordMapper = new WordMapper(rootTokenExtractorMock);
    IWord actual = wordMapper.map(new WordMapper.DataForMapping(inputTokenGroup, 0L));
    assertEquals(actual.toString(), expected.toString());
    verify(rootTokenExtractorMock, times(1)).extract(inputTokenGroup);
  }

  @Test(groups = "unit-tests")
  public void testMapEmptySet() throws Exception {
    Set<String> inputTokenGroup = new HashSet<>();

    RootTokenExtractor rootTokenExtractorMock = mock(RootTokenExtractor.class);
    WordMapper wordMapper = new WordMapper(rootTokenExtractorMock);
    try {
      wordMapper.map(new WordMapper.DataForMapping(inputTokenGroup, 0L));
      assert false;
    } catch (NullPointerException e) {
      assert true;
    }
  }
}