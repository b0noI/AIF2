package io.aif.language.word.dict;

import io.aif.common.FileHelper;
import io.aif.language.common.IGrouper;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.IGroupComparator;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by b0noI on 31/10/14.
 */
public class DictBuilderIntegTest {

    @Test(groups = "experimental")
    public void test1() throws Exception {
        String text;
        long before = System.nanoTime();
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("Afloat+on+the+Flood_small.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        TokenSplitter tokenSplitter = new TokenSplitter();
        AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        List<String> tokens = tokenSplitter.split(text);
        List<List<String>> sentences = sentenceSplitter.split(tokens);
        List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
        IGroupComparator setComparator = IGroupComparator.createDefaultInstance(tokenComparator);
        WordMapper groupToWordMapper = new WordMapper(new RootTokenExtractor(tokenComparator));
        IGrouper grouper = new FormGrouper(setComparator);

        IDictBuilder dictBuilder = new DictBuilder(grouper, groupToWordMapper);
        IDict dict = dictBuilder.build(filteredTokens);

        long after = System.nanoTime();
        long delta = (after - before) / 1000_000_000;
        System.out.println(dict);
        System.out.println("Completed in: " + delta);
        // 180 sec
        // 122 best
    }

}
