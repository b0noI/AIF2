package io.aif.language.word.dict;

import io.aif.language.word.IDict;
import io.aif.language.word.IWord;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class DictTest {

    @Test
    public void getWords() throws Exception {
        HashMap<String, List<String>> fixture = new HashMap<>();
        fixture.put("hey", Arrays.asList("hey", "heya", "freya"));
        fixture.put("see", Arrays.asList("see", "unseen", "foreseen", "seen", "seered"));
        fixture.put("back", Arrays.asList("back", "backward", "backer", "backpack"));

        Set<IWord> words = fixture
                .entrySet()
                .stream()
                .map(pair ->  new Word(
                        pair.getKey(),
                        new HashSet<String>(pair.getValue()), (long) pair.getValue().size()
                    )
                ).collect(Collectors.toSet());

        IDict dict = new Dict(words);
        assertEquals(dict.getWords(), words);
    }
}