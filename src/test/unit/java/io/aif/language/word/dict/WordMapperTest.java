package io.aif.language.word.dict;

import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class WordMapperTest {

    @Test
    public void testMap() throws Exception {
        Set<String> inputTokenGroup = new HashSet<>(Arrays.asList("heya", "hey", "hiya"));
        IWord expected = new Word("heya", inputTokenGroup, (long) inputTokenGroup.size());

        RootTokenExtractor rootTokenExtractorMock = mock(RootTokenExtractor.class);
        when(rootTokenExtractorMock.extract(anySet())).thenReturn(Optional.<String>of("heya"));

        WordMapper wordMapper = new WordMapper(rootTokenExtractorMock);
        IWord actual = wordMapper.map(inputTokenGroup);
        assertEquals(actual.toString(), expected.toString());
        verify(rootTokenExtractorMock, times(1)).extract(inputTokenGroup);
    }

    @Test
    public void testMapEmptySet() throws Exception {
        Set<String> inputTokenGroup = new HashSet<>();

        RootTokenExtractor rootTokenExtractorMock = mock(RootTokenExtractor.class);
        WordMapper wordMapper = new WordMapper(rootTokenExtractorMock);
        try {
            wordMapper.map(inputTokenGroup);
            assert false;
        } catch (NullPointerException e) {
            assert true;
        }
    }
}