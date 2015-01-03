package io.aif.language.word.dict;

import io.aif.language.word.IWord;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

public class WordTest {

    private final String ROOT_TOKEN = "hey";
    private final Set<String> TOKENS = new HashSet<>();

    @BeforeMethod
    public void setUp() {
        TOKENS.add("hey");
        TOKENS.add("heya");
        TOKENS.add("freya");
    }

    @Test
    public void testGetRootToken() throws Exception {
        IWord word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        assertEquals(ROOT_TOKEN, word.getRootToken());
    }

    @Test
    public void testGetAllTokens() throws Exception {
        IWord word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        assertEquals(TOKENS, word.getAllTokens());
    }

    @Test
    public void testGetCount() throws Exception {
        IWord word = new Word(ROOT_TOKEN, TOKENS, (long) TOKENS.size());
        final Long actual = new Long(TOKENS.size());
        assertEquals(actual, word.getCount());
    }
}