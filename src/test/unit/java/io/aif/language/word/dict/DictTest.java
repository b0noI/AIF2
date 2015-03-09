package io.aif.language.word.dict;

import io.aif.language.common.IDict;
import io.aif.language.common.ISearchable;
import io.aif.language.word.IWord;
import net.didion.jwnl.data.Exc;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class DictTest {

    private Map<String, List<String>> fixture;

    @BeforeTest
    public void setUp() {
        fixture = new HashMap<>();
        fixture.put("hey", Arrays.asList("hey", "heya", "freya"));
        fixture.put("see", Arrays.asList("see", "unseen", "foreseen", "seen", "seered"));
        fixture.put("back", Arrays.asList("back", "backward", "backer", "backpack"));
    }

    @Test(groups = "unit-tests", enabled = false)
    public void getWords() throws Exception {
        Set<IWord> words = fixture
                .entrySet()
                .stream()
                .map(pair ->  new Word(
                        pair.getKey(),
                        new HashSet<String>(pair.getValue()), (long) pair.getValue().size()
                    )
                ).collect(Collectors.toSet());

        IDict dict = Dict.create(words);
        assertEquals(dict.getWords(), words);
    }

    @Test(groups = "unit-tests", enabled = false)
    public void testSearch() throws Exception {
        IWord expectedIWord = new Word("see", fixture.remove("see"), 0l);
        Set<IWord> words = fixture
                .entrySet()
                .stream()
                .map(pair ->  new Word(
                                pair.getKey(),
                                new HashSet<String>(pair.getValue()), (long) pair.getValue().size()
                        )
                ).collect(Collectors.toSet());
        words.add(expectedIWord);

        ISearchable dict = Dict.create(words);
        Optional actual = dict.search("foreseen");
        assertEquals(actual.get(), expectedIWord);
    }

    @Test(groups = "unit-tests", enabled = false)
    public void testSearchNoResults() throws Exception {
        Set<IWord> words = fixture
                .entrySet()
                .stream()
                .map(pair ->  new Word(
                                pair.getKey(),
                                new HashSet<String>(pair.getValue()), (long) pair.getValue().size()
                        )
                ).collect(Collectors.toSet());

        ISearchable dict = Dict.create(words);
        Optional actual = dict.search("bingo");
        assertFalse(actual.isPresent());
    }
}