package io.aif.language.word.dict;

import io.aif.language.word.IWord;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.Collection;

import static org.testng.Assert.*;

public class WordTest {
    private final String ROOT_TOKEN = "hey";
    private Collection<String> TOKENS = Arrays.asList("hey", "hey", "hey", "heya", "freya.", "freya");

    @Test(groups = "unit-tests")
    public void testGetRootToken() throws Exception {
        IWord word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        assertEquals(word.getRootToken(), ROOT_TOKEN);
    }

    @Test(groups = "unit-tests")
    public void testGetAllTokens() throws Exception {
        Collection<String> TOKENS_ONCE = Arrays.asList("aa", "bb", "cc");
        IWord word = new Word(ROOT_TOKEN, TOKENS_ONCE, (long) TOKENS_ONCE.size());
        assertEquals(word.getAllTokens(), TOKENS_ONCE);
    }

    @Test(groups = "unit-tests")
    public void testGetCount() throws Exception {
        IWord word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        assertEquals(word.getCount(), new Long(TOKENS.size()));
    }

    @Test(groups = "unit-tests")
    public void testGetTokenCountTest1() throws Exception {
        Word word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        assertEquals(word.getTokenCount("hey"), 3);
    }

    @Test(groups = "unit-tests")
    public void testGetTokenCountTest2() throws Exception {
        Word word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        assertEquals(word.getTokenCount("freya"), 1);
    }

    @Test(groups = "unit-tests")
    public void testGetTokenCountTest3() throws Exception {
        Word word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        assertEquals(word.getTokenCount("newToken"), 0);
    }
}