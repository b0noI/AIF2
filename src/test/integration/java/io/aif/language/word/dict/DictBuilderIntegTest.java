package io.aif.language.word.dict;

import com.google.gson.Gson;
import io.aif.common.FileHelper;
import io.aif.language.common.IGrouper;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.IGroupComparator;
import io.aif.language.word.IWord;
import opennlp.tools.formats.ad.ADSentenceStream;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by b0noI on 31/10/14.
 */
public class DictBuilderIntegTest {

    private static final Gson GSON = new Gson();

    @Test(groups = "experimental")
    public void test1() throws Exception {
        String text;
        long before = System.nanoTime();
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("aif_article.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<List<String>> sentences = sentenceSplitter.split(tokens);
        final List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
        IGroupComparator setComparator = IGroupComparator.createDefaultInstance(tokenComparator);
        WordMapper groupToWordMapper = new WordMapper(new RootTokenExtractor(tokenComparator));
        IGrouper grouper = new FormGrouper(setComparator);

        IDictBuilder dictBuilder = new DictBuilder(grouper, groupToWordMapper);
        IDict dict = dictBuilder.build(filteredTokens);

        long after = System.nanoTime();
        long delta = (after - before) / 1000_000_000;
        
        final IdealDict idealDict = loadIdealDict();
        
        final int rootTokenErrors = (int)dict.getWords().stream().filter(word -> 
            rootTokenError(word, idealDict)
        ).count();
        final int tokensErrors = dict.getWords().stream().mapToInt(word ->
            tokensErrors(word, idealDict)
        ).sum();
        
        System.out.println(dict);
        System.out.println("Completed in: " + delta);
        // 180 sec
        // 122 best
    }
    
    private static IdealDict loadIdealDict() throws IOException {
        try(final InputStream modelResource = DictBuilderTest.class.getResourceAsStream("ideal_dict.json")) {
            final String json = FileHelper.readAllText(modelResource);
            return GSON.fromJson(json, IdealDict.class);
        }
    }
    
    private static class IdealDict {
        
        private Map<String, List<String>> words;
        
        public Optional<Map.Entry<String, List<String>>> findTarget(final String targetToken) {
            return words.entrySet().stream().filter(entry ->
                            entry.getKey().equals(targetToken) || entry.getValue().contains(targetToken)
            ).findFirst();
        }
        
    }
    
    private static boolean rootTokenError(final IWord word, final IdealDict idealDict) {
        final Optional<Map.Entry<String, List<String>>> idealResult = idealDict.findTarget(word.getRootToken());
        return idealResult.get().getKey().equals(word.getRootToken());
    }
    
    private static int tokensErrors(final IWord word, final IdealDict idealDict) {
        final Map.Entry<String, List<String>> idealResult = idealDict.findTarget(word.getRootToken()).get();  
        return (int)word.getAllTokens().stream().filter(token -> idealResult.getValue().contains(token)).count();
    }

}
