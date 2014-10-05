package com.aif.language.sentence;

import com.aif.common.FileHelper;
import com.aif.language.common.ISplitter;
import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISentenceSeparatorsGrouper;
import com.aif.language.token.TokenSplitter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.*;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class SimpleSentenceSplitterCharactersExtractorQualityTest {

    @DataProvider(name = "path_provider")
    private static String[][] pathProvider() {
       return new String[][]{
               {"46800-0.txt"},
               {"for_sentence_split_test_opencorpora_RU_5000.txt"},
               {"A+Defence+of+Poesie+and+Poems.txt"},
               {"A+General+History+and+Collection+of+Voyages+and+Travels.txt"},
               {"Address+by+Honorable+William+C.+Redfield%2c+Secretary+of+Commerce+at+Conference+of+Regional+Chairmen.txt"},
               {"Afloat+on+the+Flood.txt"},
        } ;
    }

    @DataProvider(name = "eng_books_provider")
    private static String[][] engBooksProvider() {
        return new String[][]{
                {"A+Defence+of+Poesie+and+Poems.txt"},
//                {"A+General+History+and+Collection+of+Voyages+and+Travels.txt"},
//                {"Address+by+Honorable+William+C.+Redfield%2c+Secretary+of+Commerce+at+Conference+of+Regional+Chairmen.txt"},
//                {"Afloat+on+the+Flood.txt"},
//                {"As+A+Chinaman+Saw+Us.txt"},
//                {"Atlantic+Monthly%2c+Vol.+4%2c+no.+24+October+1859.txt"},
//                {"Atlantic+Monthly%2c+Vol.+4%2c+no.+25%2c+November+1859.txt"},
//                {"Atlantic+Monthly%2c+Volume+20%2c+No.+118%2c+August%2c+1887.txt"},
//                {"Blackbeard.txt"},
//                {"Book+of+Etiquette.txt"},
//                {"Chamberss+Edinburgh+Journal%2c+No.+419.txt"},
//                {"Charaktere+und+Schicksale.txt"},
//                {"Compendio+di+Chimica+Fisiologica.txt"},
//                {"Contos+dAldeia.txt"},
//                {"Cumners+Son+and+Other+South+Sea+Folk.txt"},
//                {"Dan+Merrithew.txt"},
//                {"Darwiniana.txt"},
//                {"Defence+of+Harriet+Shelley.txt"},
//                {"Die+natuerliche+Tochter.txt"},
//                {"Down+the+Mother+Lode.txt"},
//                {"E-books+and+e-publishing.txt"},
//                {"Early+Reviews+of+English+Poets.txt"},
//                {"Emily+Fox-Seton%3a+Being+%22The+Making+of+a+Marchioness%22+and+%22The+Methods+of+Lady+Walderhurst%22.txt"},
//                {"Encyclopaedia+Britannica%2c+11th+Edition.txt"},
//                {"English+to+Bengali+Dectionary+Download.txt"},
//                {"Evidence+as+to+Mans+Place+In+Nature.txt"},
//                {"Farewell.txt"},
//                {"Farm+Ballads.txt"},
//                {"Fen+Zhuang+Lou%2c+chapters+71-80.txt"},
//                {"Four+Girls+at+Chautauqua.txt"},
//                {"From+out+the+Vasty+Deep.txt"},
//                {"G%c3%b6tzen-D%c3%a4mmerung.txt"},
//                {"George+Borrow.txt"},
//                {"Glaucoma.txt"},
//                {"Gullivers+Travels.txt"},
//                {"Hardy+Perennials+and+Old+Fashioned+Flowers.txt"},
//                {"Harvard+Classics%2c+vol+38.txt"},
//                {"Hellenica.txt"},
//                {"Hero+Tales+From+American+History.txt"},
//                {"Il+ritratto+del+diavolo.txt"},
//                {"In+Bohemia+with+Du+Maurier.txt"},
//                {"In+Flanders+Fields.txt"},
//                {"In+Madeira+Place.txt"},
//                {"In+Troubador-Land.txt"},
//                {"In+the+Wrong+Paradise.txt"},
//                {"Indrukken+van+Finland.txt"},
//                {"Jack+Haydons+Quest.txt"},
//                {"Judgments+of+the+Court+of+Appeal+of+New+Zealand+on+Proceedings+to+Review+Aspects+of+the+Report+of+t.txt"},
//                {"Kalli%2c+the+Esquimaux+Christian.txt"},
//                {"Kates+Ordeal.txt"},
//                {"King+Olafs+Kinsman.txt"},
//                {"Kreikkalaisia+satuja.txt"},
//                {"LArrabbiata.txt"},
//                {"La+Zaffetta.txt"},
//                {"La+maison+de+la+courtisane.txt"},
//                {"Laramie+Holds+the+Range.txt"},
//                {"Le+capitaine+Pamphile.txt"},
//                {"Le+dangereux+jeune+homme.txt"},
//                {"Louis+Lambert.txt"},
//                {"Love+Stories.txt"},
//                {"Mga+Dakilang+Pilipino.txt"},
//                {"Molly+McDonald.txt"},
//                {"Monsieur+Bergeret+%c3%a0+Paris.txt"},
//                {"Mor+i+Sutre.txt"},
//                {"Mountain+idylls%2c+and+Other+Poems.txt"},
//                {"Mr+Honeys+Small+Business+Dictionary+German-English.txt"},
//                {"Mutter+und+Kind.txt"},
//                {"Notes+and+Queries%2c+Number+57%2c+November+30%2c+1850.txt"},
//                {"Novelle.txt"},
//                {"Over+Here.txt"},
//                {"Pages+from+a+Journal+with+Other+Papers.txt"},
//                {"Red+Axe.txt"},
//                {"Reprinted+Pieces.txt"},
//                {"Robert+Louis+Stevenson.txt"},
//                {"Ronald+Morton.txt"},
//                {"Ruth+Fielding+at+the+War+Front.txt"},
//                {"Sintram+and+His+Companions.txt"},
//                {"Southern+Arabia.txt"},
//                {"The+Casebook+of+Sherlock+Holmes.txt"},
//                {"The+Centralia+Conspiracy.txt"},
//                {"The+Chronicle+of+the+Canons+Regular+of+Mount+St.+Agnes.txt"},
//                {"The+Cid.txt"},
//                {"The+Coming+Technological+Singularity.txt"},
//                {"The+Deaf.txt"},
//                {"The+Friendly+Road.txt"},
//                {"The+German+Classics+of+The+Nineteenth+and+Twentieth+Centuries%2c+Vol.+II.txt"},
//                {"The+Mafulu.txt"},
//                {"The+Outdoor+Chums.txt"},
//                {"The+Prose+Works+of+Jonathan+Swift%2c+D.D.%2c+Vol.+VII.txt"},
//                {"The+Rise+of+the+Dutch+Republic%2c+1576.txt"},
//                {"The+Shuttle.txt"},
//                {"The+Snow-Drop.txt"},
//                {"The+Sport+of+the+Gods.txt"},
//                {"The+Tattva-Muktavali.txt"},
//                {"The+Three+Mistakes+Of+My+Life.txt"},
//                {"The+Works+of+the+Right+Honourable+Edmund+Burke%2c+Vol.+IV.txt"},
//                {"Three+Years+in+Tristan+da+Cunha.txt"},
//                {"Trilby.txt"},
//                {"Tuomo+sed%c3%a4n+tupa.txt"},
//                {"Under+the+Ocean+to+the+South+Pole.txt"},
//                {"Ved+vejen.txt"},
//                {"Viage+al+Rio+de+La+Plata+y+Paraguay.txt"},
//                {"Whiffet+Squirrel.txt"},
//                {"Wilhelm+Meisters+Wanderjahre%2c+vol+1.txt"},
//                {"Youth+and+Sex.txt"},
//                {"english.txt"},
//                {"sex+stories.txt"},
        } ;
    }

    @Test(groups = { "acceptance-tests", "quality-fast" }, dataProvider = "eng_books_provider")
    public void testSeparatorGroupingQualityBig(final String path) throws Exception {
        // input arguments
        String inputText;
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream(String.format("/texts/ENG/%s", path))) {
            inputText = FileHelper.readAllText(modelResource);
        }

        qualityTest(inputText);
    }

    @Test(groups = { "acceptance-tests", "quality-fast" }, dataProvider = "path_provider")
    public void testSeparatorGroupingQuality(final String path) throws Exception {
        // input arguments
        String inputText;
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream(path)) {
            inputText = FileHelper.readAllText(modelResource);
        }

        qualityTest(inputText);
    }

    @Test(groups = { "acceptance-tests", "quality-fast" }, dataProvider = "path_provider")
    public void testSeparatorExtractionQuality(final String path) throws Exception {
        // input arguments
        String inputText;
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream(path)) {
            inputText = FileHelper.readAllText(modelResource);
        }
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final List<String> inputTokens = tokenSplitter.split(inputText);

        // expected results
        final List<Character> expectedResult = Arrays.asList(new Character[]{
                '.', '(', ')',
                ':', '\"', '#',
                ';', '‘', '“',
                ',', '\'', '?',
                '!'
        });
        final List<Character> mandatoryCharacters = Arrays.asList(new Character[]{
                '.', ',', '(', ')'
        });

        // creating test instance
        final ISentenceSeparatorExtractor testInstance = ISentenceSeparatorExtractor.Type.PROBABILITY.getInstance();

        // execution test
        final List<Character> actualResult = testInstance.extract(inputTokens).get();

        // result assert

        long correct  = actualResult
                .stream()
                .filter(ch -> expectedResult.contains(ch))
                .count();
        double result = (double)correct / (double)expectedResult.size();
        assertTrue(String.format("result is: %f", result), result > 0.6);

        mandatoryCharacters.forEach(ch ->
            assertTrue(String.format("mandatory character(%s) absent", ch), actualResult.contains(ch)));

        actualResult.forEach(ch ->
            assertFalse(String.format("Character %s is alphabetic", ch), Character.isAlphabetic(ch)));

    }

    // will be removed, used for collection result for article
    @Test (groups = "experimental")
    public void test1() throws Exception {
        String text;

        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("46800-0_mutated.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        StanfordNLPSentenceSplitter stanfordNLPSentenceSplitter = new StanfordNLPSentenceSplitter();

        List<String> sentences = stanfordNLPSentenceSplitter.split(text);
        assertTrue(sentences.size() > 0);

        try(InputStream modelResource = ClassLoader.class.getResourceAsStream("/models/opennlp/en-sent.bin")) {
            ISplitter<String, String> splitter =
                    new OpenNLPSentenceSplitter(modelResource);

            List<String> actualResult = splitter.split(text);

            assertTrue(actualResult.size() > 0);
        }

        AIF2NLPSentenceSplitter aif2NLPSentenceSplitter = new AIF2NLPSentenceSplitter();
        List<List<String>> sentences2 = aif2NLPSentenceSplitter.split(text);
        assertTrue(sentences2.size() > 0);
    }

    private void qualityTest(final String inputText) {
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final List<String> inputTokens = tokenSplitter.split(inputText);

        // expected results
        final List<Character> expectedResult = Arrays.asList(new Character[]{
                '.', '(', ')',
                ':', '\"', '#',
                ';', '‘', '“',
                ',', '\'', '?',
                '!'
        });
        final List<Character> mandatoryCharacters = Arrays.asList(new Character[]{
                '.', ','
        });
        final List<Character> mandatoryGroup1Characters = Arrays.asList(new Character[]{
                '.'
        });

        // creating test instance
        final ISentenceSeparatorExtractor testInstance = ISentenceSeparatorExtractor.Type.PROBABILITY.getInstance();
        ISentenceSeparatorsGrouper separatorsGrouper = ISentenceSeparatorsGrouper.Type.PROBABILITY.getInstance();
        ISentenceSeparatorGroupsClassificatory sentenceSeparatorGroupsClassificatory = ISentenceSeparatorGroupsClassificatory.Type.PROBABILITY.getInstance();

        // execution test
        final List<Character> separators = testInstance.extract(inputTokens).get();
        final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> actualResult = sentenceSeparatorGroupsClassificatory.classify(inputTokens, separatorsGrouper.group(inputTokens, separators));

        // result assert

        final Set<Character> allCharacters = new HashSet<>();

        actualResult
                .entrySet()
                .forEach(element -> element.getValue().forEach(ch -> allCharacters.add(ch)));

        long correct  = allCharacters
                .stream()
                .filter(ch -> expectedResult.contains(ch))
                .count();
        double result = (double)correct / (double)allCharacters.size();
        assertTrue(String.format("result is: %f", result), result >= 0.6);

        final List<String> errors = new ArrayList<>();

        mandatoryCharacters.forEach(ch -> {
                if (!actualResult.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1).contains(ch) &&
                    !actualResult.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_2).contains(ch))
                    errors.add(String.format("mandatory character(%s) absent at all!\n", ch));
        });

        mandatoryGroup1Characters.forEach(ch -> {
                    if (!actualResult.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1).contains(ch))
                        errors.add(String.format("mandatory GROUP 1 character(%s) absent\n", ch));
                });

        allCharacters.forEach(ch -> {
                    if (Character.isAlphabetic(ch))
                        errors.add(String.format("Character %s is alphabetic\n", ch));
                });

        assertTrue(errors.toString(), errors.size() == 0);
        // current state: 18/107
        // . absent at all  10
        // , absent at all  2
        // . absent in Gr1  14
        // other
        // alhabetic 3
    }

}
