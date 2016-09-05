package io.aif.language.sentence;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.aif.common.FileHelper;
import io.aif.language.common.ISplitter;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.sentence.separators.extractors.ISeparatorExtractor;
import io.aif.language.sentence.separators.groupers.ISeparatorsGrouper;
import io.aif.language.token.Tokenizer;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class SimpleSentenceSplitterCharactersExtractorQualityTest {

  @DataProvider(name = "path_provider")
  private static String[][] pathProvider() {
    return new String[][]{
        {"46800-0.txt"},
//               {"for_sentence_split_test_opencorpora_RU_5000.txt"},
//               {"A+Defence+of+Poesie+and+Poems.txt"},
//               {"A+General+History+and+Collection+of+Voyages+and+Travels.txt"},
//               {"Address+by+Honorable+William+C.+Redfield%2c+Secretary+of+Commerce+at+Conference+of+Regional+Chairmen.txt"},
//               {"Afloat+on+the+Flood.txt"},
    };
  }

  @DataProvider(name = "eng_books_provider")
  private static String[][] engBooksProvider() {
    return new String[][]{
        // ENG
        {"ENG/A+Defence+of+Poesie+and+Poems.txt"},
        {"ENG/A+General+History+and+Collection+of+Voyages+and+Travels.txt"},
        {"ENG/Address+by+Honorable+William+C.+Redfield%2c+Secretary+of+Commerce+at+Conference+of+Regional+Chairmen.txt"},
        {"ENG/Afloat+on+the+Flood.txt"},
        {"ENG/As+A+Chinaman+Saw+Us.txt"},
        {"ENG/Atlantic+Monthly%2c+Vol.+4%2c+no.+24+October+1859.txt"},
        {"ENG/Atlantic+Monthly%2c+Vol.+4%2c+no.+25%2c+November+1859.txt"},
        {"ENG/Atlantic+Monthly%2c+Volume+20%2c+No.+118%2c+August%2c+1887.txt"},
        {"ENG/Blackbeard.txt"},
        {"ENG/Book+of+Etiquette.txt"},
        {"ENG/Chamberss+Edinburgh+Journal%2c+No.+419.txt"},
        {"ENG/Charaktere+und+Schicksale.txt"},
        {"ENG/Compendio+di+Chimica+Fisiologica.txt"},
        {"ENG/Contos+dAldeia.txt"},
        {"ENG/Cumners+Son+and+Other+South+Sea+Folk.txt"},
        {"ENG/Dan+Merrithew.txt"},
        {"ENG/Darwiniana.txt"},
        {"ENG/Defence+of+Harriet+Shelley.txt"},
        {"ENG/Die+natuerliche+Tochter.txt"},
        {"ENG/Down+the+Mother+Lode.txt"},
        {"ENG/E-books+and+e-publishing.txt"},
        {"ENG/Early+Reviews+of+English+Poets.txt"},
        {"ENG/Emily+Fox-Seton%3a+Being+%22The+Making+of+a+Marchioness%22+and+%22The+Methods+of+Lady+Walderhurst%22.txt"},
        {"ENG/Encyclopaedia+Britannica%2c+11th+Edition.txt"},
        {"ENG/English+to+Bengali+Dectionary+Download.txt"},
        {"ENG/Evidence+as+to+Mans+Place+In+Nature.txt"},
        {"ENG/Farewell.txt"},
        {"ENG/Farm+Ballads.txt"},
        {"ENG/Four+Girls+at+Chautauqua.txt"},
        {"ENG/From+out+the+Vasty+Deep.txt"},
        {"ENG/G%c3%b6tzen-D%c3%a4mmerung.txt"},
        {"ENG/George+Borrow.txt"},
        {"ENG/Glaucoma.txt"},
        {"ENG/Gullivers+Travels.txt"},
        {"ENG/Hardy+Perennials+and+Old+Fashioned+Flowers.txt"},
        {"ENG/Harvard+Classics%2c+vol+38.txt"},
        {"ENG/Hellenica.txt"},
        {"ENG/Hero+Tales+From+American+History.txt"},
        {"ENG/Il+ritratto+del+diavolo.txt"},
        {"ENG/In+Bohemia+with+Du+Maurier.txt"},
        {"ENG/In+Flanders+Fields.txt"},
        {"ENG/In+Madeira+Place.txt"},
        {"ENG/In+Troubador-Land.txt"},
        {"ENG/In+the+Wrong+Paradise.txt"},
        {"ENG/Indrukken+van+Finland.txt"},
        {"ENG/Jack+Haydons+Quest.txt"},
        {"ENG/Judgments+of+the+Court+of+Appeal+of+New+Zealand+on+Proceedings+to+Review+Aspects+of+the+Report+of+t.txt"},
        {"ENG/Kalli%2c+the+Esquimaux+Christian.txt"},
        {"ENG/Kates+Ordeal.txt"},
        {"ENG/King+Olafs+Kinsman.txt"},
        {"ENG/Kreikkalaisia+satuja.txt"},
        {"ENG/LArrabbiata.txt"},
        {"ENG/La+Zaffetta.txt"},
        {"ENG/La+maison+de+la+courtisane.txt"},
        {"ENG/Laramie+Holds+the+Range.txt"},
        {"ENG/Le+capitaine+Pamphile.txt"},
        {"ENG/Le+dangereux+jeune+homme.txt"},
        {"ENG/Louis+Lambert.txt"},
        {"ENG/Love+Stories.txt"},
        {"ENG/Mga+Dakilang+Pilipino.txt"},
        {"ENG/Molly+McDonald.txt"},
        {"ENG/Monsieur+Bergeret+%c3%a0+Paris.txt"},
        {"ENG/Mor+i+Sutre.txt"},
        {"ENG/Mountain+idylls%2c+and+Other+Poems.txt"},
        {"ENG/Mr+Honeys+Small+Business+Dictionary+German-English.txt"},
        {"ENG/Mutter+und+Kind.txt"},
        {"ENG/Notes+and+Queries%2c+Number+57%2c+November+30%2c+1850.txt"},
        {"ENG/Novelle.txt"},
        {"ENG/Over+Here.txt"},
        {"ENG/Pages+from+a+Journal+with+Other+Papers.txt"},
        {"ENG/Red+Axe.txt"},
        {"ENG/Reprinted+Pieces.txt"},
        {"ENG/Robert+Louis+Stevenson.txt"},
        {"ENG/Ronald+Morton.txt"},
        {"ENG/Ruth+Fielding+at+the+War+Front.txt"},
        {"ENG/Sintram+and+His+Companions.txt"},
        {"ENG/Southern+Arabia.txt"},
        {"ENG/The+Casebook+of+Sherlock+Holmes.txt"},
        {"ENG/The+Centralia+Conspiracy.txt"},
        {"ENG/The+Chronicle+of+the+Canons+Regular+of+Mount+St.+Agnes.txt"},
        {"ENG/The+Cid.txt"},
        {"ENG/The+Coming+Technological+Singularity.txt"},
        {"ENG/The+Deaf.txt"},
        {"ENG/The+Friendly+Road.txt"},
        {"ENG/The+German+Classics+of+The+Nineteenth+and+Twentieth+Centuries%2c+Vol.+II.txt"},
        {"ENG/The+Mafulu.txt"},
        {"ENG/The+Outdoor+Chums.txt"},
        {"ENG/The+Prose+Works+of+Jonathan+Swift%2c+D.D.%2c+Vol.+VII.txt"},
        {"ENG/The+Rise+of+the+Dutch+Republic%2c+1576.txt"},
        {"ENG/The+Shuttle.txt"},
        {"ENG/The+Snow-Drop.txt"},
        {"ENG/The+Sport+of+the+Gods.txt"},
        {"ENG/The+Tattva-Muktavali.txt"},
        {"ENG/The+Three+Mistakes+Of+My+Life.txt"},
        {"ENG/The+Works+of+the+Right+Honourable+Edmund+Burke%2c+Vol.+IV.txt"},
        {"ENG/Three+Years+in+Tristan+da+Cunha.txt"},
        {"ENG/Trilby.txt"},
        {"ENG/Tuomo+sed%c3%a4n+tupa.txt"},
        {"ENG/Under+the+Ocean+to+the+South+Pole.txt"},
        {"ENG/Ved+vejen.txt"},
        {"ENG/Viage+al+Rio+de+La+Plata+y+Paraguay.txt"},
        {"ENG/Whiffet+Squirrel.txt"},
        {"ENG/Wilhelm+Meisters+Wanderjahre%2c+vol+1.txt"},
        {"ENG/Youth+and+Sex.txt"},
        {"ENG/english.txt"},
        {"ENG/sex+stories.txt"},

        // FRA
        {"FR/Albert Cohen - Belle Du Seigneur - 1968.txt"},
        {"FR/Boris Akounine - Les aventures d'Eraste Fandorine (integral).txt"},
        {"FR/Infernaliana.txt"},
        {"FR/Le temple de Gnide.txt"},
        {"FR/Un an - Jean Echenoz.txt"},

        //GER
        {"GER/Biss 1zum Morgengrauen.txt"},
        {"GER/Biss 2 zur Mittagsstunde.txt"},
        {"GER/Biss 3 zum Abendrot.txt"},
        {"GER/Biss 5 Midnightsun.txt"},
        {"GER/Leopold von Sacher-Masoch Venus im Pelz (Kompletter Text).txt"},

        // ITA
        {"ITA/Fallaci Oriana - Il sesso inutile (1961).txt"},
        {"ITA/Fallaci Oriana - Insciallah (1990).txt"},
        {"ITA/Fallaci Oriana - Intervista con la storia (1974).txt"},
        {"ITA/Fallaci Oriana - La forza della ragione (2004).txt"},
        {"ITA/Fallaci Oriana - La rabbia e l'orgoglio (2001).txt"},
        {"ITA/Fallaci Oriana - Lettera a un bambino mai nato (1975).txt"},
        {"ITA/Fallaci Oriana - Niente E Cos Sia (1969).txt"},
        {"ITA/Fallaci Oriana - Oriana Fallaci intervista Oriana Fallaci (2004).txt"},
        {"ITA/Fallaci Oriana - Penelope Alla Guerra (1962).txt"},
        {"ITA/Fallaci Oriana - Risponde (Social forum a Firenze) (2002).txt"},
        {"ITA/Fallaci Oriana - Un cappello pieno di ciliege (2005).txt"},
        {"ITA/Fallaci Oriana - Un uomo (1979).txt"},
        {"ITA/Fallaci Oriana - Wake Up Occidente Sveglia (2002).txt"},

        //POL
        {"POL/Chmielewska Joanna - Autobiografia t 3.txt"},
        {"POL/Chmielewska Joanna - Autobiografia t 4.txt"},
        {"POL/Chmielewska Joanna - Autobiografia t 5.txt"},
        {"POL/Chmielewska Joanna - Depozyt.txt"},
        {"POL/Chmielewska Joanna - Drugi watek.txt"},
        {"POL/Chmielewska Joanna - Pafnucy.txt"},
        {"POL/Chmielewska Joanna - Szajka bez konca.txt"},
        {"POL/Chmielewska Joanna - W.txt"},
        {"POL/Chmielewska Joanna D.txt"},
        {"POL/Chmielewska Joanna Dwie glowy i jedna noga.txt"},
        {"POL/Chmielewska Joanna Dwie trzecie sukcesu.txt"},
        {"POL/Chmielewska Joanna Klin.txt"},
        {"POL/Chmielewska Joanna Krokodyl z Kraju Karoliny.txt"},
        {"POL/Chmielewska Joanna L.txt"},
        {"POL/Chmielewska Joanna Zbieg okolicznosci.txt"},
        {"POL/Chmielewska_Joanna_-_(Nie)boszczyk_maz.txt"},
        {"POL/Chmielewska_Joanna_-_Babski_motyw.txt"},
        {"POL/Chmielewska_Joanna_-_Bulgarski_bloczek.txt"},
        {"POL/Chmielewska_Joanna_-_Pech.txt"},
        {"POL/Chmielewska_Joanna_-_Trudny_Trup.txt"},
        {"POL/J. Chmielewska Wielkie zaslugi.txt"},
        {"POL/J.Chmielewska 1 Wielki diament.txt"},
        {"POL/J.Chmielewska 2 Wielki diament.txt"},
        {"POL/J.Chmielewska Skarby.txt"},
        {"POL/J.Chmielewska Slepe szczescie.txt"},
        {"POL/JOANNA CHMIELEWSKA.txt"},
        {"POL/Joanna Chmielewska - Harpie.txt"},
        {"POL/Joanna Chmielewska Kocie worki.txt"},
        {"POL/Joanna Chmielewska Lesio.txt"},
        {"POL/Joanna Chmielewska Wszyscy jestesmy podejrzani.txt"},
        {"POL/Krowa niebianska.txt"},
        {"POL/Nawiedzony dom.txt"},
        {"POL/Przekleta Bariera.txt"},
        {"POL/Skradziona kolekcja.txt"},
        {"POL/Wegiel Marta Jak wytrzymac z Joanna Chmielewska.txt"},
        {"POL/Wszystko Czerwone.txt"},
        {"POL/Zbieg okolicznosci.txt"},
        {"POL/Złota mucha.txt"},
        {"POL/BOCZNE DROGI.txt"},
        {"POL/Cale zdanie nieboszczyka.txt"},
        {"POL/Chmielewska J. Jak wytrzymac ze wspolczesna kobieta.txt"},
        {"POL/Chmielewska Joanna - Autobiografia t 1.txt"},
        {"POL/Chmielewska Joanna - Autobiografia t 2.txt"},

        //RUS
        {"RU/17354.txt"},
        {"RU/18957.txt"},
        {"RU/19530.txt"},
        {"RU/27519.txt"},
        {"RU/46427.txt"},
        {"RU/46468.txt"},
        {"RU/46606.txt"},
        {"RU/46699.txt"},
        {"RU/46777.txt"},
        {"RU/47729.txt"},
        {"RU/49723.txt"},
        {"RU/70684.txt"},
        {"RU/79813.txt"},
        {"RU/9602.txt"},
    };
  }

  public static void main(String[] args) throws Exception {
    final Map<String, List<String>> errors = executeTest();
    System.out.println(
        errors.keySet().stream().mapToDouble(key -> (double) errors.get(key).size()).sum()
            / (double) engBooksProvider().length * 5.);
  }

  public static Map<String, List<String>> executeTest() throws Exception {
    // input arguments
    String inputText;

    final Map<String, List<String>> totalErrors = new HashMap<>();
    for (String[] path : engBooksProvider()) {
      try (InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class
          .getResourceAsStream(String.format("/texts/%s", path))) {
        inputText = FileHelper.readAllText(modelResource);
      }
      List<String> errors = qualityTest(inputText);
      if (errors.size() > 0) {
        totalErrors.put(path[0], errors);
      }
    }

    final Map<String, Integer> errorsCounts = new HashMap<>();

    totalErrors.entrySet().stream().forEach(element -> {
      element.getValue().forEach(error -> {
        final int errorCount = errorsCounts.getOrDefault(error, 0);
        errorsCounts.put(error, errorCount + 1);
      });
    });
    return totalErrors;
  }

  private static List<String> qualityTest(final String inputText) {
    final Tokenizer tokenizer = new Tokenizer();
    final List<String> inputTokens = tokenizer.split(inputText);

    // expected results
    final List<Character> expectedResult = Arrays.asList('.', '(', ')', ':', '\"', '#', ';', '‘',
        '“', ',', '\'', '?', '!');
    final List<Character> mandatoryCharacters = Arrays.asList('.', ',');
    final List<Character> mandatoryGroup1Characters = Collections.singletonList('.');
    final List<Character> mandatoryGroup2Characters = Collections.singletonList(',');

    // creating test instance
    final ISeparatorExtractor testInstance = ISeparatorExtractor.Type.PROBABILITY.getInstance();
    final ISeparatorsGrouper separatorsGrouper = ISeparatorsGrouper.Type.PROBABILITY.getInstance();
    final ISeparatorGroupsClassifier sentenceSeparatorGroupsClassificatory =
        ISeparatorGroupsClassifier.Type.PROBABILITY.getInstance();

    // execution test
    final List<Character> separators = testInstance.extract(inputTokens).get();
    final Map<ISeparatorGroupsClassifier.Group, Set<Character>> actualResult =
        sentenceSeparatorGroupsClassificatory
            .classify(inputTokens, separatorsGrouper.group(inputTokens, separators));

    // result assert

    final Set<Character> allCharacters = new HashSet<>();

    actualResult
        .entrySet()
        .forEach(element -> element.getValue().forEach(ch -> allCharacters.add(ch)));

    long correct = allCharacters
        .stream()
        .filter(ch -> expectedResult.contains(ch))
        .count();

    final List<String> errors = new ArrayList<>();

    if (allCharacters.size() != 0) {
      double result = (double) correct / (double) allCharacters.size();
      if (result < .6) {
        errors.add(String.format("result is: %f", result));
      }
    }


    mandatoryCharacters.forEach(ch -> {
      if (!actualResult.get(ISeparatorGroupsClassifier.Group.GROUP_1).contains(ch) &&
          !actualResult.get(ISeparatorGroupsClassifier.Group.GROUP_2).contains(ch))
        errors.add(String.format("mandatory character(%s) absent at all!\n", ch));
    });

    mandatoryGroup1Characters.forEach(ch -> {
      if (!actualResult.get(ISeparatorGroupsClassifier.Group.GROUP_1).contains(ch))
        errors.add(String.format("mandatory GROUP 1 character(%s) absent\n", ch));
    });

    mandatoryGroup2Characters.forEach(ch -> {
      if (!actualResult.get(ISeparatorGroupsClassifier.Group.GROUP_2).contains(ch))
        errors.add(String.format("mandatory GROUP 2 character(%s) absent\n", ch));
    });

    allCharacters.forEach(ch -> {
      if (Character.isAlphabetic(ch))
        errors.add(String.format("Character %s is alphabetic\n", ch));
    });

    return errors;
    // new 57
    // current state: 22(39)/186
    // . absent at all  12
    // , absent at all  6
    // . absent in Gr1  17
    // alhabetic 7
    // quality 1
  }

  @Test(groups = {"acceptance-tests", "quality-fast"})
  public void testSeparatorGroupingQualityBig() throws Exception {
    assertTrue(executeTest().size() <= 21);
  }

  @Test(groups = {"acceptance-tests", "quality-slow"}, dataProvider = "path_provider")
  public void testSeparatorGroupingQuality(final String path) throws Exception {
    // input arguments
    String inputText;
    try (InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class
        .getResourceAsStream(path)) {
      inputText = FileHelper.readAllText(modelResource);
    }

    qualityTest(inputText);
  }

  @Test(groups = {"acceptance-tests", "quality-fast"}, dataProvider = "path_provider")
  public void testSeparatorExtractionQuality(final String path) throws Exception {
    // input arguments
    String inputText;
    try (InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class
        .getResourceAsStream(path)) {
      inputText = FileHelper.readAllText(modelResource);
    }
    final Tokenizer tokenizer = new Tokenizer();
    final List<String> inputTokens = tokenizer.split(inputText);

    // expected results
    final List<Character> expectedResult = Arrays.asList('.', '(', ')', ':', '\"', '#', ';', '‘',
        '“', ',', '\'', '?', '!');
    final List<Character> mandatoryCharacters = Arrays.asList('.', ',', '(', ')');

    // creating test instance
    final ISeparatorExtractor testInstance = ISeparatorExtractor.Type.PROBABILITY.getInstance();

    // execution test
    final List<Character> actualResult = testInstance.extract(inputTokens).get();

    // result assert

    long correct = actualResult
        .stream()
        .filter(ch -> expectedResult.contains(ch))
        .count();
    double result = (double) correct / (double) expectedResult.size();
    assertTrue(String.format("result is: %f", result), result > 0.6);

    mandatoryCharacters.forEach(ch ->
        assertTrue(String.format("mandatory character(%s) absent", ch), actualResult.contains(ch)));

    actualResult.forEach(ch ->
        assertFalse(String.format("Character %s is alphabetic", ch), Character.isAlphabetic(ch)));

  }

  // will be removed, used for collection result for article
  @Test(groups = "experimental")
  public void test1() throws Exception {
    String text;

    try (InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class
        .getResourceAsStream("Afloat+on+the+Flood.txt")) {
      text = FileHelper.readAllText(modelResource);
    }

    StanfordNLPSentenceSplitter stanfordNLPSentenceSplitter = new StanfordNLPSentenceSplitter();

    List<String> sentences = stanfordNLPSentenceSplitter.split(text);
    assertTrue(sentences.size() > 0);

    try (InputStream modelResource = ClassLoader.class
        .getResourceAsStream("/models/opennlp/en-sent.bin")) {
      ISplitter<String, String> splitter =
          new OpenNLPSentenceSplitter(modelResource);

      List<String> actualResult = splitter.split(text);

      assertTrue(actualResult.size() > 0);
    }

    AIF2NLPSentenceSplitter aif2NLPSentenceSplitter = new AIF2NLPSentenceSplitter();
    List<List<String>> sentences2 = aif2NLPSentenceSplitter.split(text);
    assertTrue(sentences2.size() > 0);
  }

}
