package io.aif.language.word.dict;

import io.aif.language.common.IGrouper;
import io.aif.language.word.IDict;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class DictBuilderTest {

    @Test
    public void testConstructorWithoutArguments() throws Exception {
        try {
            new DictBuilder();
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testBuild() throws Exception {
        List<String> input = Arrays.asList("hey", "heya", "hiya");
        Set<String> tokens = new HashSet<>(input);
        List<Set<String>> grouperReturn = Arrays.asList(tokens);
        IWord mockIWord = new Word("hey", tokens, (long) tokens.size());
        List<IWord> wordMapperReturn = Arrays.asList(mockIWord);

        IDict expected = new Dict(new HashSet<>(Arrays.asList(mockIWord)));

        IGrouper grouperMock = mock(IGrouper.class);
        WordMapper wordMapperMock = mock(WordMapper.class);

        when(grouperMock.group(any())).thenReturn(grouperReturn);
        when(wordMapperMock.mapAll(any())).thenReturn(wordMapperReturn);

        IDictBuilder builder = new DictBuilder(grouperMock, wordMapperMock);
        IDict actual = builder.build(input);

        assertEquals(actual.getWords(), expected.getWords());
    }
}