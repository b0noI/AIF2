package io.aif.language.word.dict;

import com.google.gson.Gson;
import io.aif.common.FileHelper;
import io.aif.language.common.IDict;
import io.aif.language.common.IDictBuilder;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertTrue;


/**
 * Created by b0noI on 31/10/14.
 */
public class DictBuilderIntegTest {

    private static final Gson GSON = new Gson();

    @Test(enabled = false, groups = {"integration-tests", "quality-fast"})
    public void testQuality() throws Exception {
        final ExperimentResult experimentResult = runExperiment();
        assertTrue(experimentResult.getRootTokenErrors()    <= 31);
        assertTrue(experimentResult.getTokensErrors()       <= 5);
    }


    public static void main(String[] args) throws Exception {
        new DictBuilderIntegTest().testQuality();
    }
    
    public static ExperimentResult runExperiment() throws IOException {
        String text;
        long before = System.nanoTime();
        try(InputStream modelResource
                    = SimpleSentenceSplitterCharactersExtractorQualityTest
                .class
                .getResourceAsStream("aif_article.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<List<String>> sentences = sentenceSplitter.split(tokens);
        final List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        IDictBuilder dictBuilder = new DictBuilder();
        IDict<IWord> dict = dictBuilder.build(filteredTokens);

        long after = System.nanoTime();
        long delta = (after - before) / 1_000_000_000;

        final IdealDict idealDict = loadIdealDict();

        final int rootTokenErrors = (int)dict.getWords().stream().filter(word ->
                        rootTokenError(word, idealDict)
        ).count();
        final int tokensErrors = dict.getWords().stream().mapToInt(word ->
                        tokensErrors(word, idealDict)
        ).sum();
        return new ExperimentResult(rootTokenErrors, tokensErrors);

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
        if (!idealResult.isPresent()) {
            return true;
        }
        return !idealResult.get().getKey().toLowerCase().equals(word.getRootToken().toLowerCase());
    }
    
    private static int tokensErrors(final IWord word, final IdealDict idealDict) {
        final Optional<Map.Entry<String, List<String>>> idealResult = idealDict.findTarget(word.getRootToken());  
        if (!idealResult.isPresent()) {
            return word.getAllTokens().size();
        }
        return (int)word.getAllTokens().stream().filter(token -> !idealResult.get().getValue().contains(token)).count();
    }
    
    public static class ExperimentResult {
        
        private final int rootTokenErrors;
        
        private final int tokensErrors;

        public ExperimentResult(int rootTokenErrors, int tokensErrors) {
            this.rootTokenErrors = rootTokenErrors;
            this.tokensErrors = tokensErrors;
        }

        public int getRootTokenErrors() {
            return rootTokenErrors;
        }

        public int getTokensErrors() {
            return tokensErrors;
        }
        
        public int totalErrorsCount() {
            return getRootTokenErrors() + getTokensErrors();
        }
        
    }

}
